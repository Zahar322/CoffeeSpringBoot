var boxs=document.querySelectorAll('input[type="checkbox"]');
var amounts=document.querySelectorAll('input[name="amount"]');


window.onload=()=>{


    boxs.forEach(item=>{
        item.onclick=()=>{
            if(item.checked){
                let amountEl= document.getElementById(item.getAttribute("coffeeId"));
                let coffeeIdEl=document.getElementById(`coffeeId${item.getAttribute("coffeeId")}`);
                coffeeIdEl.setAttribute("name","coffeeId");
                amountEl.removeAttribute("disabled");
                amountEl.setAttribute("name","count");


            }else{
                let amountEl= document.getElementById(item.getAttribute("coffeeId"));
                let coffeeIdEl=document.getElementById(`coffeeId${item.getAttribute("coffeeId")}`);
                coffeeIdEl.setAttribute("name","");
                amountEl.setAttribute("disabled","");
                amountEl.setAttribute("name","");

            }
        };
    });
    amounts.forEach(item=>{
        item.onblur=()=>{
            var price=String(document.getElementById(`price${item.id}`).innerHTML);
            var count=item.value;
            document.getElementById(`totalPrice${item.id}`).innerHTML=price*count;
        }
    })


}





function done(){
    var coffeeIds=document.getElementById("coffeeId");
    var amount=document.getElementById("amount");
    var totalPrice=document.getElementById("totalPrice");
    boxs.forEach(item=>{
        var id=item.getAttribute("coffeeId");
        var count=document.getElementById(id).value;
        var priceString=document.getElementById(`totalPrice${id}`).innerHTML;
        var price=String(priceString);
        alert(price);
        if(item.checked){
            coffeeIds.value=`${coffeeIds.value}/${id}`;
            amount.value=`${amount.value}/${count}`;
            totalPrice.value=`${totalPrice.value}/${price}`;
        }

    });
    document.getElementById("but").click();
}