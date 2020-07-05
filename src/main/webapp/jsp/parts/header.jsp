<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="messages"/>

<div class="language_menu d-flex m-auto">
    <div class="language">
        <div class="dropdown_language">
            <a href="?locale=en">EN</a>
            <a class="dropdown_link"><img class="arrow" src="../../images/down-arrow.svg"></a>
            <div class="dropdown-content">
                <a href="?locale=ua">UA</a>
            </div>
        </div>
    </div>
    <div class="menu d-flex">
    </div>
</div>

<div class="container d-flex m-auto">
    <div class="logo_div col-6 justify-content-between m-auto">
        <a href="${pageContext.request.contextPath}/game-www/home">
            <img class="logo" src="../../images/logo.png">
        </a>
    </div>
    <div class="sign_in col-6 text-right m-auto">
        <a href="${pageContext.request.contextPath}/game-www/home">
            <fmt:message key="home.home"/>
        </a>
        <a style="color: #6f3d2d">&#8195|&#8195</a>
        <c:if test="${sessionScope.userSession == null}">
            <a href="${pageContext.request.contextPath}/jsp/sign_in.jsp">
                <fmt:message key="home.signIn"/>
            </a>
            <a style="color: #6f3d2d">&#8195|&#8195</a>
            <a href="${pageContext.request.contextPath}/jsp/sign_up.jsp">
                <fmt:message key="home.signUp"/>
            </a>
        </c:if>
        <c:if test="${sessionScope.userSession != null}">
            <a href="${pageContext.request.contextPath}/game-www/profile">
                <fmt:message key="home.profile"/>
            </a>
            <a style="color: #6f3d2d">&#8195|&#8195</a>
            <a href="${pageContext.request.contextPath}/game-www/logout">
                <fmt:message key="home.logout"/>
            </a>
        </c:if>
    </div>
</div>