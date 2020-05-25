var ws;
var username = sessionStorage.getItem("username");
var host = document.location.host;
ws = new WebSocket("ws://" + host + "/game/" + username);


ws.onmessage = function (event) {
    var log = document.getElementById("log");
    log.innerHTML = localStorage.getItem("myContent");
    console.log(event.data);
    var message = JSON.parse(event.data);
    log.innerHTML += message.from + " : " + message.content + "\n";
    localStorage.setItem("myContent", log.innerHTML);
};

function clearContent() {
    document.getElementById("log").innerHTML = "";
    localStorage.removeItem("myContent");
}

function send() {
    var content = document.getElementById("msg").value;
    var json = JSON.stringify({
        "content": content
    });
    ws.send(json);
}
