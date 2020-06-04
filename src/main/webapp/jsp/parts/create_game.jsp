<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<a href="#" class="close"><img src="../../images/close.svg"></a>

<form class="create_game_form form-group" method="post"
      action="${pageContext.request.contextPath}/game-www/create-game">

    <h3 class="create_game_name">Create a game</h3>

    <div class="d-flex justify-content-between">

        <div class="game_labels">
            <label for="players_number">Players number:</label>
            <label for="rounds_number">Rounds number:</label>
            <label for="round_time">Round time:</label>
            <label for="username">Username:</label>
        </div>
        <div class="game_inputs">
            <input type="number" class="form-control players_number" name="players_number" id="players_number"
                   min="1" max="10" placeholder="Enter number of players">
            <input type="number" class="form-control rounds_number" name="rounds_number" id="rounds_number" min="1"
                   max="20" placeholder="Enter number of rounds">
            <input type="number" class="form-control round_time" name="round_time" id="round_time" min="20"
                   max="120" placeholder="Enter round time">
            <input type="text" class="form-control username" name="username" id="username"
                   placeholder="Enter username">
        </div>
        <%--  todo add required --%>
    </div>

    <div class="create_button">
        <input type="submit" class="btn btn-light" value="CREATE">
    </div>
</form>