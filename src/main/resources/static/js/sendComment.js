var comment;
var id=document.getElementById("coffee").value;
var userId=document.getElementById("userId").value;

var commentList=document.getElementById("commentList");
var textComment=document.getElementById("comment");
var username=document.getElementById("user").innerHTML;

window.onload=function () {

    document.getElementById("but").onclick=fullfil;
}



function fullfil(){
    var uri=`http://localhost:8080/rest/saveComment`;
    var request=new XMLHttpRequest();
    setComment();

    request.open("POST",uri);
    request.onload=function(){
        if(request.status==200){

        }
    };
    request.setRequestHeader("Content-Type","application/json");

    request.send(JSON.stringify(comment));
}


function setComment() {

    comment=new Comment(id,userId,textComment.value,username);
    commentList.innerHTML=`${commentList.innerHTML}
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
                    </li>`

}

function Comment(coffeeId,userId,content,username){

    this.coffeeId=coffeeId;
    this.userId=userId;
    this.content=content;
    this.username=username;
}