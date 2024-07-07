var submit = document.getElementById("submit");
var currentUrl = window.location.origin;
console.log(currentUrl);
submit.addEventListener("click", e=>{
    e.preventDefault();
    signin();});
function signin() {
    
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var email = document.getElementById("email").value;

    var user = {
        username: username,
        password: password,
        email: email
    };
    jsonUser = JSON.stringify(user);
    fetch(currentUrl+'/api/user/saveUser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: jsonUser
    }).then(response => {
        if (response.ok) {
            alert("User created");
            window.location.href = "/login";
        }else{
            alert(response.statusText);
        }
    })
}