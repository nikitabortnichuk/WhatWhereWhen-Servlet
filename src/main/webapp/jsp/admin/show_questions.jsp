<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/jsp/parts/head_tag.jsp"/>
</head>
<body style="width: 100%">
<div class="admin_page d-flex">
    <jsp:include page="/jsp/admin/left_part.jsp"/>
    <div class="admin_right">
        <div class="margin_block">
            <div class="admin_welcome_text d-flex">
                <p>Welcome, ${sessionScope.adminSession.login}</p>
            </div>
            <div class="admin_line"></div>
        </div>
        <div>
            <p class="admin_title_content">All questions</p>
        </div>

        <a class="add_button" href="${pageContext.request.contextPath}/jsp/admin/add_question.jsp">
            + Add new question
        </a>

        <div class="question_list mt-3">
            <div class="table_question_list">
                <table class="table table-primary table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Text</th>
                        <th>Type</th>
                        <th>Variants</th>
                        <th>Answer</th>
                        <th>Edit/Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.questions}" var="question" varStatus="i">
                        <tr>
                            <th>${question.id}</th>
                            <td>${question.questionText}</td>
                            <td>${question.questionType}</td>
                            <td>
                                <c:forEach items="${question.variantList}" var="variant" varStatus="i">
                                    <p><c:out value="${i.count}"/>. ${variant.text}</p>
                                </c:forEach>
                            </td>

                            <td>${question.answer}</td>

                            <td>
                                <div class="d-flex">
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/game-www/admin/edit_question_page">
                                        <input type="hidden" name="question_id" value="${question.id}">
                                        <button type="submit" name="submit_edit" class="link_button">edit</button>
                                    </form>
                                    /
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/game-www/admin/delete_question">
                                        <input type="hidden" name="question_id" value="${question.id}">
                                        <button type="submit" name="submit_delete" class="link_button">delete</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <nav class="d-flex" aria-label="Navigation for questions">
                    <ul class="pagination m-auto">
                        <c:if test="${requestScope.currentPage != 1}">
                            <li class="page-item">
                                <a class="page-link"
                                   href="${pageContext.request.contextPath}/game-www/admin/show_questions?currentPage=${requestScope.currentPage-1}">
                                    Previous</a>
                            </li>
                        </c:if>

                        <c:forEach begin="1" end="${requestScope.nOfPages}" var="i">
                            <c:choose>
                                <c:when test="${requestScope.currentPage eq i}">
                                    <li class="page-item active"><a class="page-link">
                                            ${i} <span class="sr-only">(current)</span></a>
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li class="page-item"><a class="page-link"
                                                             href="${pageContext.request.contextPath}/game-www/admin/show_questions?currentPage=${i}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>

                        <c:if test="${requestScope.currentPage lt requestScope.nOfPages}">
                            <li class="page-item"><a class="page-link"
                                                     href="${pageContext.request.contextPath}/game-www/admin/show_questions?currentPage=${requestScope.currentPage+1}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</div>
</body>
</html>