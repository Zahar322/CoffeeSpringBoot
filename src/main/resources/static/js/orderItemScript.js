var language;
var title=document.getElementsByName("title");
var titleRu=document.getElementsByName("titleRu");
window.onload=()=>{
    var main=document.getElementById("mainRef");
    var mainText=main.innerHTML;
    if(mainText==="главная"){
        language="ru";
    }else{
        language="eg";
    }
    localizate();
    calculatePrice();
}



function calculatePrice(){
    var amount=document.querySelectorAll(".amount");
    var price=document.querySelectorAll(".price");
    var totalPrice=document.querySelectorAll(".totalPrice");
    for(var i=0;i<amount.length;i++){
        totalPrice[i].innerHTML=String(amount[i].innerHTML)*String(price[i].innerHTML);
    }
}


function localizate() {

    if(language==="ru"){
        title.forEach(title=>{
            title.setAttribute("class","hide");
        })
    }else{
        titleRu.forEach(titleRu=>{
            titleRu.setAttribute("class","hide");
        })
    }
}