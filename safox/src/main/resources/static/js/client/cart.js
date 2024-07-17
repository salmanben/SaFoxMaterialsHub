

var total = document.querySelector(".total-value");
var shipping = document.querySelector(".shipping-value");
var subtotal = document.querySelector(".subtotal-value");
var discount = document.querySelector(".discount-value");
var insurance = document.querySelector(".insurance-value");




var startDate = document.getElementById("start-date");
var endDate = document.getElementById("end-date");
startDate.addEventListener("change", updateDurree)

endDate.addEventListener("change", updateDurree)


 function updateDurree(){
    if(startDate.value && endDate.value){
 
        if(startDate.value > endDate.value){
            toastr.error("End date must be greater than start date");
            return
        }
        if(startDate.value <= new Date().toISOString().split("T")[0]){
           toastr.error("Start date must be greater than today");
           return
          
       }
        if(startDate.value === endDate.value)
            toastr.error("Start date and end date must be different");

        durree = Math.floor((new Date(endDate.value) - new Date(startDate.value)) / (1000 * 60 * 60 * 24));
        fetch(`/cart/update-durree?start=${startDate.value}&end=${endDate.value}&durree=${durree}`)
        .then(response => response.json())
        .then(data => {
            if(data.status === "success"){
                total.textContent = "$" + data.invoice.total;
                shipping.textContent = "$" + data.invoice.shipping;
                subtotal.textContent = "$" + data.invoice.subtotal;
                discount.textContent = "$" + data.invoice.discount;
            }
            else{
                
                   toastr.error("Update failed, Please try again");
            }
        })
       }
 }




var cartCount = document.getElementById("cart-count")
function deleteItem(){
    var element = event.currentTarget;
    var id = element.getAttribute("data-id");
    fetch("/cart/delete",{
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: id
    })
    .then(response => response.json())
    .then(data => {
        if(data.status === "success"){
            element.parentNode.parentNode.parentNode.remove();
            var count = parseInt(cartCount.getAttribute("data-count"));
            cartCount.setAttribute("data-count", count - 1);
            total.textContent = "$" + data.invoice.total;
            shipping.textContent = "$" + data.invoice.shipping;
            subtotal.textContent = "$" + data.invoice.subtotal;
            discount.textContent = "$" + data.invoice.discount;
            if(count === 1){
                window.location.href = "/";
            }
    
        }
    })
       
    .catch((error) => {
        console.error("Error:", error);
    });
    
   



}

function updateQty(){
    var element = event.currentTarget;
    var id = element.getAttribute("data-id");
    if(element.classList.contains("plus")){
        var quantity = element.nextElementSibling;
        if(quantity.value == element.getAttribute("data-stock")){
            toastr.info("Out of stock")
            return;
        }
        var qty = parseInt(quantity.value) + 1;
        quantity.value = qty;
    }
    else{
        var quantity = element.previousElementSibling;
        if(quantity.value == 1){
            return;
        }
        var qty = parseInt(quantity.value) - 1;
        quantity.value = qty;
    }
    fetch(`/cart/update?id=${id}&qty=${qty}`)
    .then(response => response.json())
    .then(data => {
        if(data.status === "error"){
            toastr.error(`Update failed, It stay just ${data.available} item available in stock`);
            quantity.value = data.available;
        }
        else{
            total.textContent = "$" + data.invoice.total;
            shipping.textContent = "$" + data.invoice.shipping;
            subtotal.textContent = "$" + data.invoice.subtotal;
            discount.textContent = "$" + data.invoice.discount;
        }
       
    })
}

var shippingAddress = document.getElementById("shipping-address");
var shippingMethod = document.querySelectorAll("input[name='shipping-method']");
shippingMethod.forEach(function (element) {
    element.addEventListener("change", function () {

        var shipping_status
        if(element.value == "alixa-shipping"){
            shippingAddress.classList.add("active");
            shipping_status = 1
        }
        else{
            shippingAddress.classList.remove("active");
            shipping_status = 0
        }
        fetch(`/cart/update-shipping?shipping=${shipping_status}`)
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                total.textContent = "$" + data.invoice.total;
                shipping.textContent = "$" + data.invoice.shipping;
                subtotal.textContent = "$" + data.invoice.subtotal;
                discount.textContent = "$" + data.invoice.discount;
            }
        })
       
        
    })
})

var insuranceCheck = document.querySelectorAll("input[name='insurance']");
var insurancePrice = insuranceCheck[0].value
insuranceCheck.forEach(function (element) {
    element.addEventListener("change", function () {
        var insurance_status
        if(element.value == 0){
            insurance_status = 0
            insurance.textContent = "$" + 0

        }
        else{
            insurance_status = 1
            insurance.textContent = "$" + insurancePrice

        }
        fetch(`/cart/update-insurance?insurance=${insurance_status}`)
        .then(response => response.json())
        .then(data => {
            if (data.status === "success") {
                total.textContent = "$" + data.invoice.total;
                shipping.textContent = "$" + data.invoice.shipping;
                subtotal.textContent = "$" + data.invoice.subtotal;
                discount.textContent = "$" + data.invoice.discount;
            }
        })

    })
})


var paymentForm = document.querySelector(".payment-form");
var overlay = document.querySelector(".overlay");
var close = document.querySelector(".close");
var checkout = document.querySelector(".checkout");




checkout.addEventListener("click", function () {
  if(shippingAddress.value === "" && shippingAddress.classList.contains("active")){
        toastr.error("Please fill the shipping address");
        return;
    
  }
  if(startDate.value === "" || endDate.value === ""){
        toastr.error("Please fill the dates");
        return;
  }
  if(shippingAddress.classList.contains("active")){
        fetch("/cart/update-shipping-address",{
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: shippingAddress.value
        })
        .then(response => response.json())
        .then(data => {
            if(data.status === "error"){
                toastr.error("Update failed, Please try again");
            }
            else{
                paymentForm.classList.add("active");
                overlay.classList.add("active");
                close.classList.add("active");
            }
        })
  }
  else{
      paymentForm.classList.add("active");
      overlay.classList.add("active");
      close.classList.add("active");
  }


  

}
);

close.addEventListener("click", function () {
    paymentForm.classList.remove("active");
    overlay.classList.remove("active");
    close.classList.remove("active");

});
