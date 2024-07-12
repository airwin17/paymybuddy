var submitButton = document.getElementById("submit");
var cancelButton = document.getElementById("cancel");
var usernameInput= document.getElementById("username");
var usernametf = document.getElementById("usernametf");
var emailInput = document.getElementById("email");
var emailtf = document.getElementById("emailtf");
var passwordInput = document.getElementById("password");
var passwordtf = document.getElementById("passwordtf");
var formstate=false;
switchFormState(false)
function switchFormState(state){
    if(!state){
        //false
        usernameInput.disabled = true;
        emailInput.disabled = true;
        passwordInput.disabled = true;
        cancelButton.disabled = true;
        cancelButton.style.display = "none";
        formstate=false;
        submitButton.value = "Modifier";
        passwordInput.value = "55555";
    }else{
        //true
        usernameInput.disabled = false;
        emailInput.disabled = false;
        passwordInput.disabled = false;
        cancelButton.disabled = false;
        submitButton.value = "Confirmer";
        cancelButton.style.display = "block";
        passwordInput.value = "";
        formstate=true;
    }
    
}
cancelButton.onclick=function(){
    switchFormState(false);
}
submitButton.onclick=function(e){
    e.preventDefault();
    switchFormState(true);

}