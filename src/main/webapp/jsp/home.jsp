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

    <script type="text/javascript">
        var message = "${requestScope.errorMessage}";
        if (message !== "") {
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
            <h1><fmt:message key="home.slogan"/></h1>
        </div>
        <div class="buttons">
            <a class="button_home d-block" href="#find_game">
                <h2><fmt:message key="home.find"/></h2>
            </a>
            <a class="button_home d-block" href="#create_game">
                <h2><fmt:message key="home.create"/></h2>
            </a>
        </div>
    </div>
</div>

<div class="my_footer">
    <jsp:include page="parts/footer.jsp"/>
</div>
</body>
