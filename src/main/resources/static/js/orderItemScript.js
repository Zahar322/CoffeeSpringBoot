window.onload=calculatePrice;



function calculatePrice(){
    var amount=document.querySelectorAll(".amount");
    var price=document.querySelectorAll(".price");
    var totalPrice=document.querySelectorAll(".totalPrice");
    for(var i=0;i<amount.length;i++){
        totalPrice[i].innerHTML=String(amount[i].innerHTML)*String(price[i].innerHTML);
    }
}