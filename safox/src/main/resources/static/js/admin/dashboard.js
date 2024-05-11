var tbody = document.querySelector(".table-lignes")
var tr = tbody.querySelectorAll("tr")
var list_names = document.querySelectorAll(".name")


function search() {
    var input = event.target;
    if (input.value) {
        tbody.innerHTML = ''

        var text = input.value.toLowerCase()
        if(!isNaN(text)){
           tr.forEach(e=>{
            if(text == e.id){
                tbody.append(e)
                return
            }
           })
        }
        else{
            list_names.forEach(e => {
                var name = e.innerText.toLowerCase()
                if (name.includes(text)) {
                    tbody.append(e.parentNode)
                }
            })
        }
        


    } else {
        resetSearch()
    }
}

function resetSearch() {
    tbody.innerHTML = ''
    tr.forEach(e => tbody.append(e))
}

function printDoc() {

    var documentContent = document.body.innerHTML
    var contentPrinted = document.querySelector(".content-printed")
    document.body.innerHTML = contentPrinted.innerHTML
    window.print()
    document.body.innerHTML = documentContent
}

function switchStatus(){
    var element = event.target
    var status =   element.checked
    fetch("/admin/switchStatus",{
        method:"POST",
        headers:{
            'Content-Type': 'application/json'
        },
        body:element.id
    })
    .then(response => response.text())
    .then(data=>{
        if(data=="success"){
            toastr.success("Status changed successfully")
            
        }
        else{
            toastr.error("Error changing status")
            element.checked = !status
        }
    })
    
}


function changeReservationStatus(){
    var id = event.target.id
    fetch(`/admin/reservations/treat/${id}`,{
        method:"POST",
        headers:{
            'Content-Type': 'application/json'
        },
        body:event.target.value

    })
    .then(response => response.text())
    .then(data=>{
        if(data=="success"){
            toastr.success("Status changed successfully")
        }
        else{
            toastr.error("Error changing status")
        }
    })
}

