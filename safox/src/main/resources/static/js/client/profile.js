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
