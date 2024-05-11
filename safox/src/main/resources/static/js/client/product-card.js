


function addToCart(){
    var id = event.target.getAttribute("data-id");
    var cartCount = document.getElementById("cart-count");
    fetch("/cart/add",{
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body:id
    })
    .then(response => response.json())
    .then(data => {

        if(data.status === "success"){
            toastr.success("Add to cart successfully");
           if(!data.isExists){
              var count = parseInt(cartCount.getAttribute("data-count"));
              cartCount.setAttribute("data-count", count + 1);
           }
        }
        else{
            toastr.error("Material is not available in stock");
        }
    })
    .catch(error => {
        console.error(error);
        toastr.error("Add to cart failed");
    })

}

