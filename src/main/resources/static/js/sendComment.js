var comment;
var id=document.getElementById("coffee").value;
var userId=document.getElementById("userId").value;

var commentList=document.getElementById("commentList");
var textComment=document.getElementById("comment");
var username=document.getElementById("user").innerHTML;
var count=document.getElementById("count").value;
var el=[];

window.onload=function () {

    textComment.value=localStorage.getItem("comment");

    document.getElementById("but").onclick=fullfil;

   // setInterval(compare,5000);
}



function fullfil(){
    // http://192.168.0.102/
    // var uri=`http://localhost:8080/rest/saveComment`;
    // var uri=`http://192.168.0.102:8080/rest/saveComment`;
    var uri=`${window.location.protocol}//${window.location.host}/rest/saveComment`;

    var request=new XMLHttpRequest();
    setComment();
    textComment.value="";
    request.open("POST",uri);
    request.onload=function(){
        if(request.status==200){

        }
    };
    request.setRequestHeader("Content-Type","application/json");

    request.send(JSON.stringify(comment));

}

function compare() {
    getJson();
    if(el.length>count){
        count=el.length;
        localStorage.setItem("comment",textComment.value);
        location.reload();


    }
}

function getJson() {
    var uri=`${window.location.protocol}//${window.location.host}/rest/comment/${id}`;
    var request=new XMLHttpRequest();
    request.open("GET",uri);
    request.onload=function(){
        if(request.status==200){
          el= JSON.parse(request.responseText);


        }
    };
    request.send(null);

}


function setComment() {

    comment=new Comment(id,userId,textComment.value,username);
    commentList.innerHTML=`
                    <li class="media">
                        <div class="media-left">
                            <a href="#">
                                <img class="media-object img-rounded" src="https://itchief.ru/examples/images/avatar1.jpg" alt="...">
                            </a>
                        </div>
                        <div class="media-body">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <div class="author">${username}</div>
                                    <div class="metadata">
                                        <span class="date">${new Date().toUTCString()}</span>
                                    </div>
                                </div>
                                <div class="panel-body">
                                    <div class="media-text text-justify">
                                        ${comment.content}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>${commentList.innerHTML}`

}

function Comment(coffeeId,userId,content,username){

    this.coffeeId=coffeeId;
    this.userId=userId;
    this.content=content;
    this.username=username;
}