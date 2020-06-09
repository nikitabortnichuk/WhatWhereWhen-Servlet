<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="${locale}">
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
                        <div class="variantList d-flex justify-content-between" id="variantList"></div>
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
                <span>Connected Experts:</span>
            </div>
            <div class="messages">
                <textarea id="messageList" class="messageList no-gray" readonly="readonly" rows="15"
                          cols="15"></textarea>
                <div class="message-input">
                    <input id="messageInput" type="text" name="message" placeholder="Type message...">
                    <button type="button" class="btn btn-simple btn-info" onclick="sendMessage()">Send</button>
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
    sessionStorage.setItem("username", "${sessionScope.username}");
    sessionStorage.setItem("roundTime", "${sessionScope.game.configuration.roundTime}");
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/websocket.js"></script>

</html>