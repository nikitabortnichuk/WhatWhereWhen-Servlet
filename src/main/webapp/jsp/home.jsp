<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>


<!DOCTYPE html>
<html lang="${locale}">
<head>
    <jsp:include page="parts/head_tag.jsp"/>

    <script type="text/javascript">
        var url = new URL(window.location.href);
        var message = url.searchParams.get("errorMessage");
        if (message != null) {
            alert(message);
        }
    </script>

</head>
<body>

<div class="my_header_main d-flex">
    <jsp:include page="parts/header.jsp"/>
</div>

<div id="create_game">
    <div class="game_create">
        <jsp:include page="parts/create_game.jsp"/>
    </div>
</div>

<div id="find_game">
    <div class="game_create">
        <jsp:include page="parts/find_game.jsp"/>
    </div>
</div>

<div class="content d-flex">

    <div class="m-auto">
        <div class="slogan">
            <h1>What? Where? When?</h1>
        </div>
        <div class="buttons">
            <a class="button_home d-block" href="#find_game">
                <h2>Find a Game</h2>
            </a>
            <a class="button_home d-block" href="#create_game">
                <h2>Create a Game</h2>
            </a>
        </div>
    </div>
</div>

<div class="my_footer">
    <jsp:include page="parts/footer.jsp"/>
</div>
</body>
