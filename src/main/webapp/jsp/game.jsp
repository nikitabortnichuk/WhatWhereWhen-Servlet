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
        <div class="main_window">

        </div>
        <div class="users_field">
            <div class="connected_users">

            </div>
            <div class="messages">
                <textarea readonly="readonly" rows="15" cols="15"></textarea>
                <input type="text" name="message" placeholder="Type message...">
                <button type="button" onclick="send()">Send</button>
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
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/websocket.js"></script>

</html>