var boxs=document.querySelectorAll('input[type="checkbox"]');
var amounts=document.querySelectorAll('input[name="amount"]');
var language="";
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