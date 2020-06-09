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
        <h3 class="mb-5">SIGN IN</h3>
        <p style="color: red; font-size: 14px; margin:20px auto">
            ${requestScope.SignInFailedMessage}
        </p>
        <form class="sign_form" action="${pageContext.request.contextPath}/game-www/sign_in" method="post">
            <div class="form-group">
                <label for="email">Username</label>
                <input type="text" class="form-control" id="email" placeholder="Enter username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" placeholder="Enter password" name="password"
                       required>
            </div>
            <button type="submit" class="sign_button">
                Sign In
            </button>
        </form>
        <p class="mt-5">Don't have an account?
            <a href="${pageContext.request.contextPath}/jsp/sign_up.jsp">sign up here</a>
        </p>
    </div>
</div>
</body>
</html>