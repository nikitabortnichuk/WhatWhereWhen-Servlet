<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="${bundle}"/>

<!DOCTYPE html>
<html lang="${locale}">
<head>
    <jsp:include page="parts/head_tag.jsp"/>
</head>
<body style="background: #FFEFD5; background-size: cover; ">
<div class="">
    <div class="sign-up col-lg-4" style="color: #8C4637; margin:100px auto auto auto">
        <h3 class="mb-3">REGISTRATION</h3>
        <p style="color: red; font-size: 14px; margin: 20px auto">
            ${requestScope.SignUpFailedMessage}
        </p>
        <form class="sign_form" action="${pageContext.request.contextPath}/game-www/sign_up" method="post">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" class="form-control" id="username"
                       placeholder="Enter username" name="username"
                       required>
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email"
                       placeholder="Enter email" name="email" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password"
                       placeholder="Enter password" name="password"
                       required>
            </div>
            <button type="submit" class="btn btn-info">Sign Up</button>
        </form>

    </div>
</div>
</body>
</html>