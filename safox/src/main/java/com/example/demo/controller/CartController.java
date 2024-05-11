package com.example.demo.controller;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.DTO.CartDto;

import com.example.demo.DTO.ContactDto;

import com.example.demo.DTO.GeneralSettingsDto;

import com.example.demo.DTO.PaymentRequestDto;

import com.example.demo.model.Category;

import com.example.demo.model.Material;

import com.example.demo.model.User;

import com.example.demo.reservation_decorator.BasicPrice;

import com.example.demo.reservation_decorator.DiscountDecorator;

import com.example.demo.reservation_decorator.InsuranceDecorator;

import com.example.demo.reservation_decorator.ReservationDecorator;

import com.example.demo.reservation_decorator.ShippingDecorator;

import com.example.demo.service.CategoryService;

import com.example.demo.service.GeneralSettingsService;

import com.example.demo.service.MaterialService;

import com.example.demo.service.ReservationService;

import com.example.demo.service.StripeSettingsService;

import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;

@Controller
public class CartController {

    private final GeneralSettingsService generalSettingsService;

    private final CategoryService categoryService;

    private final MaterialService materialService;

    private final ReservationService reservationService;

    @Autowired

    private StripeSettingsService stripeSettingsService;

    @Autowired

    private BasicPrice basicPrice;

    @Autowired

    private ShippingDecorator shippingDecorator;

    @Autowired

    private InsuranceDecorator insuranceDecorator;

    @Autowired

    private DiscountDecorator discountDecorator;

    @Autowired

    private UserService userService;

    public CartController(GeneralSettingsService generalSettingsService, CategoryService categoryService,

            MaterialService materialService, ReservationService reservationService) {

        this.generalSettingsService = generalSettingsService;

        this.categoryService = categoryService;

        this.materialService = materialService;

        this.reservationService = reservationService;

    }

    @GetMapping("/cart")
    public String index(Model model, HttpServletRequest request,

            @ModelAttribute("contactDto") ContactDto contactDto,

            @ModelAttribute("paymentRequestDto") PaymentRequestDto paymentRequestDto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User client = userService.findByEmailInstance(email);
        if(client == null) {
            return "redirect:/login";
        }

        GeneralSettingsDto generalSettingsDto = generalSettingsService.getGeneralSettings();

        List<Category> categories = categoryService.getAllActiveCategories();

        HttpSession session = request.getSession();

        Map<Integer, Integer> cart = new HashMap<>();
        session.setAttribute("durree", 1);
        session.setAttribute("shipping", 1);
        session.setAttribute("insurance", 0);
        session.setAttribute("shipping_address", "");

        int count = 0;

        if (session.getAttribute("cart") != null) {

            cart = (Map<Integer, Integer>) session.getAttribute("cart");

            count = cart.size();

        }

        if (count == 0) {

            return "redirect:/";

        }

        List<CartDto> cartDto = new ArrayList();

        for (Integer key : cart.keySet()) {

            Material material = materialService.getMaterialInstance(key);

            if (material.getStock() > 0) {

                int qty = cart.get(key) <= material.getStock() ? cart.get(key) : material.getStock();

                cartDto.add(new CartDto(material, qty));

            }

        }

        Map<String, Object> invoice = getInvoice(1, session);

        double total = (double) invoice.get("total");

        double subtotal = (double) invoice.get("subtotal");

        double shipping = (double) invoice.get("shipping");

        double discount = (double) invoice.get("discount");

        String client_id = stripeSettingsService.getStripeSettings().getClientId();

        model.addAttribute("generalSettings", generalSettingsDto);

        model.addAttribute("categories", categories);

        model.addAttribute("count", count);

        model.addAttribute("cartDto", cartDto);

        model.addAttribute("client_id", client_id);

        model.addAttribute("total", total);

        model.addAttribute("subtotal", subtotal);

        model.addAttribute("shipping", shipping);

        model.addAttribute("discount", discount);

        return "client/cart";

    }

    @GetMapping("/cart/update")
    public ResponseEntity<Map<String, Object>> updateCart(@RequestParam("id") int id, @RequestParam("qty") int qty,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        Map<String, Object> response = new HashMap<>();
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        int stock = materialService.getMaterial(id).getStock();
        if (stock < qty) {

            cart.put(id, stock);
            session.setAttribute("cart", cart);

            response.put("status", "error");
            response.put("available", stock);
            return ResponseEntity.ok(response);
        } else {
            cart.put(id, qty);
            session.setAttribute("cart", cart);
            response.put("status", "success");
            int durree = (int) session.getAttribute("durree");
            Map<String, Object> invoice = getInvoice(durree, session);
            response.put("invoice", invoice);
            return ResponseEntity.ok(response);
        }

    }

    @PostMapping("/cart/add")
    public ResponseEntity<Map<String, Object>> addToCart(@RequestBody int id, HttpServletRequest request) {

        HttpSession session = request.getSession();

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        Map<String, Object> response = new HashMap<>();

        boolean isExist = false;

        if (cart == null) {

            cart = new HashMap<>();

        }

        if (cart.containsKey(id)) {

            isExist = true;

        } else {

            int stock = materialService.getMaterial(id).getStock();

            if (stock <= 0) {

                response.put("status", "error");

                return ResponseEntity.ok(response);

            }

            cart.put(id, 1);

        }

        session.setAttribute("cart", cart);

        // Create the response object

        response.put("status", "success");

        response.put("isExists", isExist);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/cart/delete")
    public ResponseEntity<Map<String, Object>> deleteFromCart(@RequestBody int id, HttpServletRequest request) {

        HttpSession session = request.getSession();

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        cart.remove(id);

        session.setAttribute("cart", cart);

        int durree = (int) session.getAttribute("durree");
        Map<String, Object> invoice = getInvoice(durree, session);
        Map<String, Object> response = new HashMap<>();
        response.put("invoice", invoice);
        response.put("status", "success");
        return ResponseEntity.ok(response);

    }

    @GetMapping("/cart/update-durree")
    public ResponseEntity<?> updateDurree(@RequestParam("start") String start, @RequestParam("end") String end,

            @RequestParam("durree") int durree, HttpServletRequest request) {

        HttpSession session = request.getSession();

        Map<String, Object> response = new HashMap<>();

        session.setAttribute("start", start);

        session.setAttribute("end", end);

        session.setAttribute("durree", durree);

        response.put("status", "success");

        Map<String, Object> invoice = getInvoice(durree, session);

        response.put("invoice", invoice);

        return ResponseEntity.ok(response);

    }

    public Map<String, Object> getInvoice(int durree, HttpSession session) {

        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");

        List<CartDto> cartDto = new ArrayList();

        for (Integer key : cart.keySet()) {

            Material material = materialService.getMaterialInstance(key);

            if (material.getStock() > 0) {

                int qty = cart.get(key) <= material.getStock() ? cart.get(key) : material.getStock();

                cartDto.add(new CartDto(material, qty));

            }

        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User client = userService.findByEmailInstance(email);
        double total = 0;
        double shipping = 0;

        double subtotal = new BasicPrice(cartDto, durree).getPrice();
        double discount = subtotal - new DiscountDecorator(new BasicPrice(cartDto, durree), client,
                generalSettingsService, reservationService).getPrice();

        ReservationDecorator reservation_decorator = new DiscountDecorator(new BasicPrice(cartDto, durree), client,
                generalSettingsService, reservationService);
        if ((int) session.getAttribute("insurance") == 1)
            reservation_decorator = new InsuranceDecorator(reservation_decorator, generalSettingsService);
        if ((int) session.getAttribute("shipping") == 1)
            reservation_decorator = new ShippingDecorator(reservation_decorator, cartDto);

        total = reservation_decorator.getPrice();
        if ((int) session.getAttribute("shipping") == 1)
            shipping = new ShippingDecorator(new BasicPrice(cartDto, durree), cartDto).getPrice() - subtotal;

        total = Double.parseDouble(String.format("%.2f", total));

        subtotal = Double.parseDouble(String.format("%.2f", subtotal));

        discount = Double.parseDouble(String.format("%.2f", discount));

        shipping = Double.parseDouble(String.format("%.2f", shipping));

        Map<String, Object> response = new HashMap<>();

        response.put("total", total);

        response.put("subtotal", subtotal);

        response.put("discount", discount);

        response.put("shipping", shipping);

        session.setAttribute("invoice", response);

        return response;

    }

    @GetMapping("/cart/update-shipping")
    public ResponseEntity<?> updateShipping(@RequestParam("shipping") int shipping, HttpServletRequest request) {

        HttpSession session = request.getSession();

        Map<String, Object> response = new HashMap<>();

        session.setAttribute("shipping", shipping);

        response.put("status", "success");

        int durree = (int) session.getAttribute("durree");

        Map<String, Object> invoice = getInvoice(durree, session);

        response.put("invoice", invoice);

        return ResponseEntity.ok(response);

    }

    // update insureance
    @GetMapping("/cart/update-insurance")
    public ResponseEntity<?> updateInsurance(@RequestParam("insurance") int insurance, HttpServletRequest request) {

        HttpSession session = request.getSession();

        Map<String, Object> response = new HashMap<>();

        session.setAttribute("insurance", insurance);

        response.put("status", "success");

        int durree = (int) session.getAttribute("durree");

        Map<String, Object> invoice = getInvoice(durree, session);

        response.put("invoice", invoice);

        return ResponseEntity.ok(response);

    }

    // set shipping address
    @PostMapping("/cart/update-shipping-address")
    public ResponseEntity<?> updateShippingAddress(@RequestBody String shipping_address, HttpServletRequest request) {

        HttpSession session = request.getSession();

        Map<String, Object> response = new HashMap<>();

        session.setAttribute("shipping_address", shipping_address);

        response.put("status", "success");

        return ResponseEntity.ok(response);

    }

}
