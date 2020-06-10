<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<!DOCTYPE html>
<html lang="${locale}">
<head>
    <jsp:include page="parts/head_tag.jsp"/>
</head>
<body>
<div class="my_header_main d-flex">
    <jsp:include page="parts/header.jsp"/>
</div>

<div class="content m-5">
    <div class="container">
        <h2>PROFILE</h2>

        <p>Username: ${sessionScope.userSession.username}</p>
        <p>Email: ${sessionScope.userSession.email}</p>
        <p>Password: ${sessionScope.userSession.password}</p>

        <h3>My games: </h3>
        <table class="game_table table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Game Identification</th>
                <th scope="col">Number of rounds</th>
                <th scope="col">Number of players</th>
                <th scope="col">Correct answers</th>
                <th scope="col">Incorrect answers</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.gameList}" var="game" varStatus="i">
                <tr>
                    <td>${i.count}</td>
                    <td>${game.gameIdentification}</td>
                    <td>${game.configuration.roundsNumber}</td>
                    <td>${game.configuration.playersNumber}</td>
                    <td>${game.statistics.expertScore}</td>
                    <td>${game.statistics.opponentScore}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="my_footer">
    <jsp:include page="parts/footer.jsp"/>
</div>
</body>
</html>
