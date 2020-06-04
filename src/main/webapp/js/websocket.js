var ws;
var username = sessionStorage.getItem("username");
var host = document.location.host;
ws = new WebSocket("ws://" + host + "/game/" + username);

ws.onopen = function(event){
    var usersList = document.getElementById("usersList");
    usersList.innerHTML = localStorage.getItem("connectedUsers");
    var message = JSON.parse(event.data);
    console.log(message);
    usersList.innerHTML += message.from + message.content + "\n";
    localStorage.setItem("connectedUsers", usersList.innerHTML);
};

ws.onmessage = function (event) {
    var log = document.getElementById("messagesList");
    log.innerHTML = localStorage.getItem("messages");
    console.log(event.data);
    var message = JSON.parse(event.data);
    log.innerHTML += message.from + " : " + message.content + "\n";
    localStorage.setItem("messages", log.innerHTML);
};

function clearContent() {
    document.getElementById("log").innerHTML = "";
    localStorage.removeItem("myContent");
}

function send() {
    var content = document.getElementById("messageInput").value;
    var json = JSON.stringify({
        "content": content
    });
    ws.send(json);
}
