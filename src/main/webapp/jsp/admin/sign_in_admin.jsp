<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE HTML>
<html lang="en">

<head>
    <jsp:include page="/jsp/parts/head_tag.jsp"/>
</head>

<body style="background: #218DA6; background-size: cover; ">
<div class="">
    <div class="sign-up col-lg-4" style="color: white; margin:100px auto auto auto">
        <h3 class="mb-3">ADMINISTRATOR</h3>
        <form class="sign_form" action="${pageContext.request.contextPath}/game-www/admin" method="post">
            <div class="form-group">
                <label for="login">Login:</label>
                <input type="text" class="form-control" id="login" placeholder="Enter login" name="login" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" class="form-control" id="password" placeholder="Enter password" name="password"
                       required>
            </div>
            <button type="submit" class="sign_button">
                Sign In
            </button>
        </form>
    </div>
</div>
</body>
</html>
