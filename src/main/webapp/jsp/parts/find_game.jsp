<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<a href="#" class="close"><img src="../../images/close.svg"></a>

<form class="create_game_form form-group" method="post"
      action="${pageContext.request.contextPath}/game-www/find-game">

    <h3 class="create_game_name">Find a game</h3>

    <div class="d-flex justify-content-between">

        <div class="game_labels">
            <label for="game_identification">Game id:</label>
            <label for="username">Username:</label>
        </div>
        <div class="game_inputs">
            <input type="text" class="form-control game_identification" name="game_identification"
                   id="game_identification" placeholder="Enter game identification">
            <input type="text" class="form-control username" name="username" id="username"
                   placeholder="Enter username">
        </div>
    </div>

    <div class="create_button">
        <input type="submit" class="btn btn-light" value="FIND">
    </div>
</form>


