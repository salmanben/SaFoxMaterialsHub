var sendMsgContact = document.querySelector(".sendMsgContact")

console.log(sendMsgContact)
function contacter(){
    event.preventDefault()
    sendMsgContact.innerHTML = "Sending..."
    sendMsgContact.disabled = true
    var form = event.target
    var email = form.querySelector('input[name="email"]').value
    var message = form.querySelector('input[name="message_"]').value
    fetch('/contact', {
        method: 'POST',
        body: JSON.stringify({
            "email": email,
            "message_": message

        }),
        headers:{
            'Content-Type': 'application/json'
        }})
        .then(response => response.text())
        .then(data => {
            console.log(data)
            if(data == "success"){
                toastr.success("Message sent successfully")
                form.reset()
            }else{
                toastr.error("Message not sent")
            }
            sendMsgContact.innerHTML = "Send"
            sendMsgContact.disabled = false
        })
}

var contact = document.querySelector(".contact-link")

contact.onclick = ()=>{

    toastr.info("Contact us by submitting the form below")
}





function searchMaterial(){
    var searchValue = document.querySelector("#search-value").value
    console.log()
    if(searchValue != ""){
        window.location.href = "/material/search?search="+searchValue
    }
}