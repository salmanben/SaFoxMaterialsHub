

const swiper = new Swiper('.swiper', {

    // Optional parameters
    direction: 'horizontal',
    loop: true,
   

     GrabCursor: true,
     keyboard: {
        enabled: true,
    },
    // pagination:
    pagination: {
        el: '.swiper-pagination',
        clickable: true,
    },
    breakpoints: {
        480: {
            slidesPerView: 1,
            spaceBetween: 50
        },
        640: {
            slidesPerView: 2,
            spaceBetween: 10
        },
        1000:{
            slidesPerView:3,
            spaceBetween: 10
        }
    }

});


var catDescription = document.querySelectorAll(".cat-description")
catDescription.forEach((cat)=>{

    if(cat.clientHeight > 70){
        cat.innerText = cat.innerText.substring(0, 150) + "..."
    }
})