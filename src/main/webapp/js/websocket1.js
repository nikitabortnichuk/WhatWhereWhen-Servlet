var ws;
var username = sessionStorage.getItem("username");
var host = document.location.host;
var game = document.documentURI.split("/")[5];
ws = new WebSocket("ws://" + host + "/game/" + game);

ws.onopen = function(){
    var userAction = {
        action: "connect",
        gameId: window.game,
        username: window.username
    };
    ws.send(JSON.stringify(userAction));
};

ws.onmessage = function (event) {
    var user = JSON.parse(event.data);
    console.log(user);
    if(user.action === "connect"){
        printConnectedUser(user);
        printMessage(user);
    }
    if(user.action === "disconnect"){
        document.getElementById(user.username).remove();
        printMessage(user);
    }
    if(user.action === "send"){
        printMessage(user);
    }
};

window.onbeforeunload = function () {
    var userAction = {
        action: "disconnect",
        gameId: window.game,
        username: window.username
    };
    ws.send(JSON.stringify(userAction));
};

function printConnectedUser(user) {
    var connectedUsers = document.getElementById("connected_users");

    var connectedUserDiv = document.createElement("div");
    connectedUserDiv.setAttribute("id", user.username);
    connectedUserDiv.setAttribute("class", "connected_user");
    connectedUsers.appendChild(connectedUserDiv);

    var username = document.createElement("span");
    username.innerHTML = "• Expert: " + user.username;
    connectedUserDiv.appendChild(username);
}

function printMessage(user) {
    var messageList = document.getElementById("messageList");

    if(user.action !== "send"){
        user.content = user.action;
    }

    messageList.innerHTML += user.username + ": " + user.content + "\n";
}

function send() {
    var content = document.getElementById("messageInput").value;
    var userAction = {
        action: "send",
        gameId: window.game,
        username: window.username,
        content: content
    };
    ws.send(JSON.stringify(userAction));
}
