var ws;
var username = sessionStorage.getItem("username");
var host = document.location.host;
var gameId = document.documentURI.split("/")[5];
ws = new WebSocket("ws://" + host + "/game/" + gameId);

ws.onopen = function(){
    var userAction = {
        action: "connect",
        gameId: window.gameId,
        username: window.username
    };
    ws.send(JSON.stringify(userAction));
};

ws.onmessage = function (event) {
    var json = JSON.parse(event.data);
    console.log(json);
    if(json.action === "connect"){
        printConnectedUser(json);
        printMessage(json);
    }
    if(json.action === "disconnect"){
        document.getElementById(json.username).remove();
        printMessage(json);
    }
    if(json.action === "send"){
        printMessage(json);
    }
    if(json.action === "start"){
        printStartMessage();
    }
    if(json.action === "ask"){
        printQuestion(json);
    }
};


window.onbeforeunload = function () {
    var userAction = {
        action: "disconnect",
        gameId: window.gameId,
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

function printStartMessage() {
    var startMessageH1 = document.getElementById("start-message");
    startMessageH1.setAttribute("class", "startMessage");
    startMessageH1.innerHTML = "The game started!";
    setTimeout(removeStartQuestion, 3000);
}

function removeStartQuestion() {
    document.getElementById("start-message").remove();
}

function send() {
    var content = document.getElementById("messageInput").value;
    var userAction = {
        action: "send",
        gameId: window.gameId,
        username: window.username,
        content: content
    };
    ws.send(JSON.stringify(userAction));
}

function printQuestion(json) {
    var questionText = document.getElementById("question-text");
    questionText.innerHTML = "Question #"
        + json["numberOfQuestion"] + 1
        + ": "
        + json["questionText"];
}
