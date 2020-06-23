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

<div class="content m-5">
    <div class="container">
        <h2><fmt:message key="profile"/> </h2>

        <p><fmt:message key="profile.username"/>: ${sessionScope.userSession.username}</p>
        <p>Email: ${sessionScope.userSession.email}</p>

        <h3><fmt:message key="profile.games"/>: </h3>
        <table class="game_table table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col"><fmt:message key="profile.game.gameId"/></th>
                <th scope="col"><fmt:message key="profile.game.rounds"/></th>
                <th scope="col"><fmt:message key="profile.game.players"/></th>
                <th scope="col"><fmt:message key="profile.game.correct"/></th>
                <th scope="col"><fmt:message key="profile.game.incorrect"/></th>
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
