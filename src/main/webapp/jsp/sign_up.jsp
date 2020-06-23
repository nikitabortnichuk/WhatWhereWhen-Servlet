<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<!DOCTYPE html>
<html lang="${sessionScope.locale}">
<head>
    <jsp:include page="parts/head_tag.jsp"/>
</head>
<body style="background: #FFEFD5; background-size: cover; ">
<div class="">
    <div class="sign-up col-lg-4" style="color: #8C4637; margin:100px auto auto auto">
        <h3 class="mb-3"><fmt:message key="signUp.name"/> </h3>
        <p style="color: red; font-size: 14px; margin: 20px auto">
            <fmt:message key="signUp.error"/>
        </p>
        <form class="sign_form" action="${pageContext.request.contextPath}/game-www/sign_up" method="post">
            <div class="form-group">
                <label for="username"><fmt:message key="signIn.username"/> </label>
                <input type="text" class="form-control" id="username"
                       placeholder="<fmt:message key="signIn.username.placeholder"/>" name="username"
                       required>
            </div>
            <div class="form-group">
                <label for="email"><fmt:message key="signUp.email"/></label>
                <input type="email" class="form-control" id="email"
                       placeholder="<fmt:message key="signUp.email.placeholder"/>" name="email" required>
            </div>
            <div class="form-group">
                <label for="password"><fmt:message key="signIn.password"/></label>
                <input type="password" class="form-control" id="password"
                       placeholder="<fmt:message key="signIn.password.placeholder"/>" name="password"
                       required>
            </div>
            <button type="submit" class="btn btn-info"><fmt:message key="signUp.button"/></button>
        </form>

    </div>
</div>
</body>
</html>