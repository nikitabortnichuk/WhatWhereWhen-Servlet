<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <jsp:include page="parts/head_tag.jsp"/>
</head>
<body>

<div class="my_header_main d-flex">
    <jsp:include page="parts/header.jsp"/>
</div>

<div class="content d-flex mb-5">
    <div class="game_window m-auto d-flex">
        <div class="main_window" id="main-window">
            <div id="main-message-question" class="main-message">
                <h1 id="start-message"></h1>
                <div id="question-wrap" class="question-wrap d-flex">
                    <div id="question" class="question">
                        <h2 class="question-number" id="question-number"></h2>
                        <p class="question-text" id="question-text"></p>
                        <div class="variantList m-auto d-flex justify-content-between" id="variantList"></div>
                    </div>
                </div>
            </div>
            <div id="round-timer" class="round-timer">
                <p class="timer-text" id="timer-text"></p>
                <p class="timer-seconds" id="timer-seconds"></p>
            </div>
            <div class="answer-is-correct text-center" id="answer-is-correct">
                <p class="is-correct-message" id="is-correct-message"></p>
            </div>
        </div>
        <div class="users_field">
            <div class="connected_users" id="connected_users">
                <span><fmt:message key="game.experts"/></span>
            </div>
            <div class="gameId">
                <p><fmt:message key="game.gameId"/>: ${sessionScope.game.gameIdentification}</p>
            </div>
            <div class="messages">
                <textarea id="messageList" class="messageList no-gray" readonly="readonly" rows="15"
                          cols="15"></textarea>
                <div class="message-input">
                    <input id="messageInput" type="text" name="message"
                           placeholder="<fmt:message key="game.chat.placeholder"/>">
                    <button type="button" class="btn btn-simple btn-info" onclick="sendMessage()">
                        <fmt:message key="game.chat.button"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="my_footer">
    <jsp:include page="parts/footer.jsp"/>
</div>

</body>

<script type="text/javascript">
    sessionStorage.setItem("username", "${sessionScope.userSession.username}");
    sessionStorage.setItem("roundTime", "${sessionScope.game.configuration.roundTime}");
</script>
<script type="text/javascript">

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
        username.innerHTML = "• <fmt:message key="game.experts.expert"/>: " + user.username;
        connectedUserDiv.appendChild(username);
    }


    function printMessage(user) {
        var messageList = document.getElementById("messageList");
        if (user.action === "connect") {
            user.content = "<fmt:message key="game.connected"/>"
        } else if (user.action === "disconnect") {
            user.content = "<fmt:message key="game.disconnected"/>"
        }

        messageList.innerHTML += user.username + ": " + user.content + "\n";
    }

    function printStartMessage() {
        var startMessageH1 = document.getElementById("start-message");
        startMessageH1.style.setProperty("color", "#6F3D2D");
        startMessageH1.innerHTML = "<fmt:message key="game.start"/>";
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
        questionNumber.innerHTML = "<fmt:message key="game.question"/>" + number + ":";
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
        timerText.innerHTML = "<fmt:message key="game.timer.read"/> ";
        var seconds = 10;
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
        timerText.innerHTML = "<fmt:message key="game.timer.round"/> ";
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

    function printIncorrectMessage(json) {
        sendQuestionAnswer(json);
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
        answerInput.placeholder = "<fmt:message key="game.answer.placeholder"/>...";
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

        if (value == null) {
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
        var messageType = json["message"];
        var isCorrect = json["isCorrect"];
        var roundNumber = json["numberOfQuestion"];

        document.getElementById("question-answer-div").remove();

        var answerDiv = document.getElementById("answer-is-correct");
        var isCorrectMessage = answerDiv.children["is-correct-message"];
        isCorrectMessage.style.fontSize = "24px";

        var messageIsCorrectValue;
        if (isCorrect === true) {
            messageIsCorrectValue = "<fmt:message key="game.answer.correct"/>";
            isCorrectMessage.style.color = "green";
        } else if (isCorrect === false) {
            if(messageType === "incorrect") {
                messageIsCorrectValue = "<fmt:message key="game.answer.incorrect"/>";
            }
            else if(messageType === "timeIsOver"){
                messageIsCorrectValue = "<fmt:message key="game.answer.timeIsOver"/>";
            }
            isCorrectMessage.style.color = "red";
        }
        isCorrectMessage.innerHTML = messageIsCorrectValue;

        setTimeout(function () {
            removeElement(isCorrectMessage);
            sendRound(json, roundNumber + 1);
        }, 3000);
    }

    function removeElement(element) {
        element.innerHTML = "";
    }

    function processGameEnd(json) {
        document.getElementById("question-wrap").remove();
        document.getElementById("round-timer").remove();

        var endMessageH1 = document.getElementById("start-message");
        endMessageH1.innerHTML = "<fmt:message key="game.end"/>";

        var mainWindow = document.getElementById("main-window");

        var scoreP = document.createElement("p");
        scoreP.style.fontSize = "24px";
        scoreP.innerHTML = "<fmt:message key="game.score"/>";
        mainWindow.appendChild(scoreP);

        var correctP = document.createElement("p");
        correctP.style.fontSize = "18px";
        correctP.innerHTML = "<fmt:message key="game.result.correct"/> " + json["corrects"];
        mainWindow.appendChild(correctP);

        var incorrectP = document.createElement("p");
        incorrectP.style.fontSize = "18px";
        incorrectP.innerHTML = "<fmt:message key="game.result.incorrect"/> " + json["incorrects"];
        mainWindow.appendChild(incorrectP);

        var homeRef = document.createElement("a");
        homeRef.href = "/";
        homeRef.innerHTML = "<fmt:message key="game.result.home"/> ";
        mainWindow.appendChild(homeRef);
    }

    function processRedirect(page) {
        window.location.replace(page);
    }


</script>

</html>