var container=document.getElementById("formContainer");
var form=document.getElementById("form");
var button=document.getElementById("but");
var pass=document.querySelector(".pass");
var springMessages=document.querySelectorAll(".springMessage");


var login=document.getElementById("login");

var loc="/main#";

var name=document.getElementById("name");


window.onload=()=>{
    var register=document.getElementById("reg");

    var main=document.getElementById("main");


    if(window.location.href===loc){
        showForm();
    }

    main.onclick=hideForm;
    register.onclick=reg;
    login.onclick=enter;



}
var message=document.querySelector(".message").innerHTML;

if(message!=""){
    showForm();
}
var user=document.getElementById("user").innerHTML;
// if(user!=""){
//
//     login.setAttribute("href","/orderList");
// }





function enter(){
    showForm();

    form.setAttribute("action","/main");
    pass.setAttribute("type","password");

    reset();
}

function reg(){
    showForm();
    pass.setAttribute("type","text");
    form.setAttribute("action","/reg");

    reset();
}

function reset(){
    var inputs=document.querySelectorAll('input[type="text"');
    inputs.forEach(item=>item.value="");
}

function hideForm(){
    container.style.cssText="display:none";
    message="";

}

function showForm(){
    container.style.cssText="display:block";

}




