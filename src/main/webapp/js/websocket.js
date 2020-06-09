var ws;
var username = sessionStorage.getItem("username");
var host = document.location.host;
var gameId = document.documentURI.split("/")[5];
ws = new WebSocket("ws://" + host + "/game/" + gameId);

ws.onopen = function () {
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
    if (json.action === "connect") {
        printConnectedUser(json);
        printMessage(json);
    }
    if (json.action === "disconnect") {
        document.getElementById(json.username).remove();
        printMessage(json);
    }
    if (json.action === "send") {
        printMessage(json);
    }
    if (json.action === "start") {
        printStartMessage();
        setTimeout(function () {
            sendStartMessage(json);
            sendRound(json, 0);
        }, 3000);
    }
    if (json.action === "ask") {
        processGame(json);
    }
    if (json.action === "answer") {
        printAnswerIsCorrect(json);
    }
    if (json.action === "end") {
        processGameEnd(json);
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
    if (user.action !== "send") {
        user.content = user.action;
    }
    messageList.innerHTML += user.username + ": " + user.content + "\n";
}

function printStartMessage() {
    var startMessageH1 = document.getElementById("start-message");
    startMessageH1.style.setProperty("color", "#6F3D2D");
    startMessageH1.innerHTML = "The game started!";
    setTimeout(removeStartQuestion, 3000);
}

function removeStartQuestion() {
    var startMessageH1 = document.getElementById("start-message");
    startMessageH1.innerHTML = "";
}

function sendStartMessage(json) {
    ws.send(JSON.stringify(json));
}

function sendMessage() {
    var content = document.getElementById("messageInput").value;
    var userAction = {
        action: "send",
        gameId: window.gameId,
        username: window.username,
        content: content
    };
    ws.send(JSON.stringify(userAction));
}

function sendRound(json, roundNumber) {
    var action = {
        action: "ask",
        gameId: window.gameId,
        roundNumber: roundNumber
    };
    ws.send(JSON.stringify(action))
}


function processGame(json) {
    removeAnswerInputIfNotNull();

    printQuestion(json);

    var number = json["numberOfQuestion"] + 1;
    processTimerForQuestionRead(json, number);

}

function removeAnswerInputIfNotNull() {
    var questionAnswerDiv = document.getElementById("question-answer-div");
    if (questionAnswerDiv !== null) {
        questionAnswerDiv.remove();
    }
}

function printQuestion(json) {
    var questionWrap = document.getElementById("question-wrap");
    questionWrap.style.setProperty("box-shadow", "0 0 5px 2px rgba(122, 122, 122, 0.5)");

    var questionDiv = questionWrap.children["question"];
    questionDiv.style.backgroundColor = "#fee0c1";
    questionDiv.children["variantList"].innerHTML = "";

    var questionNumber = questionDiv.children["question-number"];
    var questionText = questionDiv.children["question-text"];
    var number = json["numberOfQuestion"] + 1;
    questionNumber.innerHTML = "Question #" + number + ":";
    questionText.innerHTML = json["questionText"];

    printVariants(json, questionDiv);

}


function printVariants(json, questionDiv) {
    var variantListDiv = questionDiv.children["variantList"];
    var variantList = json["variantList"];
    for (let i = 0; i < variantList.length; i++) {
        var variantP = document.createElement("p");
        variantP.innerHTML = (i + 1) + ". " + variantList[i].text;
        variantListDiv.appendChild(variantP);
    }
}

function processTimerForQuestionRead(json, number) {
    isAnswered = false;
    var timerElement = document.getElementById("round-timer");
    timerElement.style.textAlign = "center";

    var timerText = timerElement.children["timer-text"];
    timerText.innerHTML = "Read Question. Remaining time: ";
    var seconds = 5;
    timer(seconds,
        function (s) {
            var element = timerElement.children["timer-seconds"];
            element.innerHTML = s;
        },
        json,
        number,
        function (json, number) {
            processTimerForQuestionAnswer(json, number);
            printAnswerInputIfNull(json);
        }
    )

}

function processTimerForQuestionAnswer(json, number) {
    var timerElement = document.getElementById("round-timer");
    timerElement.style.textAlign = "center";

    var timerText = timerElement.children["timer-text"];
    timerText.innerHTML = "Round Started. Remaining time: ";
    var seconds = json["roundTime"];

    timer(seconds,
        function (s) {
            var element = timerElement.children["timer-seconds"];
            element.innerHTML = s;
        },
        json,
        number,
        function (json, number) {
            printIncorrectMessage(json, number);
        });
}

function printIncorrectMessage(json, number) {
    sendQuestionAnswer(json);
    var answerDiv = document.getElementById("answer-is-correct");
    var isCorrectMessage = answerDiv.children["is-correct-message"];
    isCorrectMessage.style.fontSize = "24px";
    isCorrectMessage.style.color = "red";
    isCorrectMessage.innerHTML = "Time is over";

    removeAnswerInput(isCorrectMessage);

    timer(3, function (s) {
        },
        json,
        number,
        function (json, number) {
            isCorrectMessage.innerHTML = "";
            sendRound(json, number);
        }
    )
}

var isAnswered = false;

function timer(seconds, tick, json, number, afterFunction) {
    if (seconds > 0) {
        seconds -= 1;
        tick(seconds);
        if (isAnswered === true) {
            return;
        }
        setTimeout(function () {
            timer(seconds, tick, json, number, afterFunction);
        }, 1000);
    } else {
        afterFunction(json, number);
    }
}

function printAnswerInputIfNull(json) {
    var inputDiv = document.getElementById("question-answer");

    if (inputDiv === null) {
        printAnswerInput(json);
    }
}

function printAnswerInput(json) {
    var mainWindow = document.getElementById("main-window");

    var answerInputDivWrap = document.createElement("div");
    answerInputDivWrap.id = "question-answer-div";
    answerInputDivWrap.style.display = "flex";

    var answerInputDiv = document.createElement("div");
    answerInputDiv.id = "question-answer";
    answerInputDiv.style.margin = "auto";
    answerInputDiv.style.width = "250px";
    answerInputDiv.style.height = "40px";
    answerInputDiv.style.border = "1px solid #6f3d2d";
    answerInputDiv.style.borderRadius = "5px";

    var answerInput = document.createElement("input");
    answerInput.type = "text";
    answerInput.name = "question-answer-input" + json["numberOfQuestion"];
    answerInput.id = "question-answer-input" + json["numberOfQuestion"];
    answerInput.placeholder = "Type answer ...";
    answerInput.style.border = "0px solid #6f3d2d";
    answerInput.style.marginLeft = "10px";
    answerInput.style.width = "150px";
    answerInputDiv.appendChild(answerInput);

    var button = document.createElement("button");
    button.type = "button";
    button.setAttribute("class", "btn btn-simple btn-info");
    button.innerHTML = "Send";
    button.onclick = function () {
        sendQuestionAnswer(json);
    };

    answerInputDiv.appendChild(button);
    answerInputDivWrap.appendChild(answerInputDiv);
    mainWindow.appendChild(answerInputDivWrap);
}

function sendQuestionAnswer(json) {
    var number = json["numberOfQuestion"];
    var valueInput = document.getElementById("question-answer-input" + number);
    var value = valueInput.value;

    if (value == null){
        value = "";
    }

    var action = {
        action: "answer",
        gameId: json.gameId,
        answer: value,
        numberOfQuestion: number
    };
    isAnswered = true;
    ws.send(JSON.stringify(action));
}

function printAnswerIsCorrect(json) {
    var message = json["message"];
    var isCorrect = json["isCorrect"];
    var roundNumber = json["numberOfQuestion"];

    document.getElementById("question-answer-div").remove();

    var answerDiv = document.getElementById("answer-is-correct");
    var isCorrectMessage = answerDiv.children["is-correct-message"];
    isCorrectMessage.style.fontSize = "24px";

    if (isCorrect === true) {
        console.log(isCorrect);
        isCorrectMessage.style.color = "green";
    } else if (isCorrect === false) {
        isCorrectMessage.style.color = "red";
    }
    isCorrectMessage.innerHTML = message;

    setTimeout(function () {
        removeAnswerInput(isCorrectMessage);
        sendRound(json, roundNumber + 1);
    }, 3000);
}

function removeAnswerInput(isCorrectMessage) {
    isCorrectMessage.innerHTML = "";
}

function processGameEnd(json) {
    document.getElementById("question-wrap").remove();
    document.getElementById("round-timer").remove();

    var endMessageH1 = document.getElementById("start-message");
    endMessageH1.innerHTML = "The game ended!";

    var mainWindow = document.getElementById("main-window");

    var scoreP = document.createElement("p");
    scoreP.style.fontSize = "24px";
    scoreP.innerHTML = "Score:";
    mainWindow.appendChild(scoreP);

    var correctP = document.createElement("p");
    correctP.style.fontSize = "18px";
    correctP.innerHTML = "Correct answers: " + json["corrects"];
    mainWindow.appendChild(correctP);

    var incorrectP = document.createElement("p");
    incorrectP.style.fontSize = "18px";
    incorrectP.innerHTML = "Incorrect answers: " + json["incorrects"];
    mainWindow.appendChild(incorrectP);

    var homeRef = document.createElement("a");
    homeRef.href = "/";
    homeRef.innerHTML = "Go to home page";
    mainWindow.appendChild(homeRef);
}

function processRedirect(page) {
    window.location.replace(page);
}
