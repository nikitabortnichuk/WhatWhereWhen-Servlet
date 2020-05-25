<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<div class="admin_left_menu">
    <div class="library_admin_text d-block text-center">
        <p class="m-1">WhatWhereWhen</p>
        <p class="m-1">Admin</p>
    </div>
    <a class="admin_menu_button d-block" href="${pageContext.request.contextPath}/game-www/admin/show_questions">
        <p>Show all questions</p>
    </a>
    <a class="admin_menu_button d-block" href="${pageContext.request.contextPath}/jsp/admin/add_question.jsp">
        <p>Add the question</p>
    </a>
    <a class="admin_menu_button d-block" href="${pageContext.request.contextPath}/game-www/home">
        <p>Back to the site</p>
    </a>
</div>