var submitBut=document.getElementById("submit");
var email=document.getElementById("email");
submitBut.addEventListener("click", e=>{
    e.preventDefault();
    fetch("/api/user/addConnection?email="+email.value+"", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(response => {
        if (response.ok) {
            alert("Connection added");
            window.location.reload();
        } else {
            alert(response.statusText);
        }    
    })
})