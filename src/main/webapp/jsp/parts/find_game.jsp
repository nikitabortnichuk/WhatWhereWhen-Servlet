<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<a href="#" class="close"><img src="../../images/close.svg"></a>

<form class="create_game_form form-group" method="post"
      action="${pageContext.request.contextPath}/game-www/find-game">

    <h3 class="create_game_name"><fmt:message key="home.find"/></h3>

    <div class="d-flex justify-content-between">

        <div class="game_labels">
            <label for="game_identification"><fmt:message key="game.gameId"/>:</label>
        </div>
        <div class="game_inputs">
            <input type="text" class="form-control game_identification" name="game_identification"
                   id="game_identification" placeholder="<fmt:message key="home.find.gameId"/>">
        </div>
    </div>

    <div class="create_button">
        <input type="submit" class="btn btn-light" value="<fmt:message key="home.find.button"/>">
    </div>
</form>


