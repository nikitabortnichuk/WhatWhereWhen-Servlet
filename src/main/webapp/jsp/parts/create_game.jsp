<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<a href="#" class="close"><img src="../../images/close.svg"></a>

<form class="create_game_form form-group" method="post"
      action="${pageContext.request.contextPath}/game-www/create-game">

    <h3 class="create_game_name"><fmt:message key="home.create"/></h3>

    <div class="d-flex justify-content-between">

        <div class="game_labels">
            <label for="players_number"><fmt:message key="home.create.players"/></label>
            <label for="rounds_number"><fmt:message key="home.create.rounds"/></label>
            <label for="round_time"><fmt:message key="home.create.time"/></label>
        </div>
        <div class="game_inputs">
            <input type="number" class="form-control players_number" name="players_number" id="players_number"
                   min="1" max="10" placeholder="<fmt:message key="home.create.players.placeholder"/>" required>
            <input type="number" class="form-control rounds_number" name="rounds_number" id="rounds_number" min="1"
                   max="20" placeholder="<fmt:message key="home.create.rounds.placeholder"/>" required>
            <input type="number" class="form-control round_time" name="round_time" id="round_time" min="20"
                   max="120" placeholder="<fmt:message key="home.create.time.placeholder"/>" required>
        </div>
    </div>

    <div class="create_button">
        <input type="submit" class="btn btn-light" value="<fmt:message key="home.create.button"/>">
    </div>
</form>
