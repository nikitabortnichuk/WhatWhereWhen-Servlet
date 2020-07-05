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
        <nav class="d-flex" aria-label="Navigation for games in user profile">
            <ul class="pagination m-auto">
                <c:if test="${requestScope.page != 1}">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/game-www/profile?page=${requestScope.page-1}">
                            Previous</a>
                    </li>
                </c:if>

                <c:forEach begin="1" end="${requestScope.nOfPages}" var="i">
                    <c:choose>
                        <c:when test="${requestScope.page eq i}">
                            <li class="page-item active"><a class="page-link">
                                    ${i} <span class="sr-only">(current)</span></a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link"
                                                     href="${pageContext.request.contextPath}/game-www/profile?currentPage=${i}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:if test="${requestScope.page lt requestScope.nOfPages}">
                    <li class="page-item"><a class="page-link"
                                             href="${pageContext.request.contextPath}/game-www/profile?currentPage=${requestScope.page+1}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>

<div class="my_footer">
    <jsp:include page="parts/footer.jsp"/>
</div>
</body>
</html>
