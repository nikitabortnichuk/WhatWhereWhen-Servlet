<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/jsp/parts/head_tag.jsp"/>
</head>
<body>
<div class="admin_page d-flex">

    <jsp:include page="left_part.jsp"/>

    <div class="admin_right">
        <div class="margin_block">
            <div class="admin_welcome_text d-flex">
                <p>Welcome, ${sessionScope.adminSession.login}</p>
            </div>
            <div class="admin_line"></div>
        </div>
        <div class>
            <p class="admin_title_content">Add the question</p>
        </div>
        <div class="add_book_form">
            <form method="post"
                  action="${pageContext.request.contextPath}/game-www/admin/add_question">

                <input type="hidden" name="question_id" value="${requestScope.question.id}">

                <div class="my_form_item">
                    <p>Question text:</p>
                    <input type="text" name="question_text" placeholder="Enter text"
                           value="${requestScope.question.questionText}" required>
                </div>


                <div class="my_form_item">
                    <p>Question type:</p>
                    <select id="question_type" name="question_type">
                        <option value="${requestScope.question.questionType}" selected>Choose</option>
                        <option value="WITH_VARIANTS">With variants</option>
                        <option value="NO_VARIANTS">Without variants</option>
                    </select>
                </div>
                <div class="my_form_item variants" id="variants">
                    <div class="d-flex">
                        <div class="variantTexts d-flex flex-column mr-5">
                            <p>Enter variants:</p>
                            <label for="variant1">1.&nbsp;
                                <input type="text" id="variant1" name="variant1" placeholder="Enter variant 1"
                                       value="${requestScope.question.variantList.get(0).text}">
                            </label>
                            <label for="variant2">2.
                                <input type="text" id="variant2" name="variant2" placeholder="Enter variant 2"
                                       value="${requestScope.question.variantList.get(1).text}">
                            </label>
                            <label for="variant3">3.
                                <input type="text" id="variant3" name="variant3" placeholder="Enter variant 3"
                                       value="${requestScope.question.variantList.get(2).text}">
                            </label>
                            <label for="variant4">4.
                                <input type="text" id="variant4" name="variant4" placeholder="Enter variant 4"
                                       value="${requestScope.question.variantList.get(3).text}">
                            </label>
                        </div>
                        <div class="variant_is_right d-flex flex-column">
                            <p>Choose right variant:</p>
                            <label for="right_variant_1">
                                <input type="checkbox" class="right_variant" id="right_variant_1" name="right_variant_1"
                                       value="true" >
                            </label>
                            <label for="right_variant_2">
                                <input type="checkbox" class="right_variant" id="right_variant_2" name="right_variant_2"
                                       value="true">
                            </label>
                            <label for="right_variant_3">
                                <input type="checkbox" class="right_variant" id="right_variant_3" name="right_variant_3"
                                       value="true">
                            </label>
                            <label for="right_variant_4">
                                <input type="checkbox" class="right_variant" id="right_variant_4" name="right_variant_4"
                                       value="true">
                            </label>
                        </div>
                    </div>
                </div>

                <div class="my_form_item answer" id="answer">
                    <p>Answer:</p>
                    <input type="text" name="answer" placeholder="Enter answer">
                </div>

                <script type="text/javascript" src="${pageContext.request.contextPath}/js/change-question-type.js"></script>
                <script>

                    let rightVariants = {
                        <c:forEach items="${requestScope.question.variantList}" var="variant">
                        "${variant.id}":{
                            text:"${variant.text}",
                            isRight:"${variant.correct}",
                        },
                        </c:forEach>
                    };

                    for (let key in rightVariants){
                        let variant = rightVariants[key];

                        if(variant.isRight){
                            let elements = document.getElementsByClassName("right_variant");
                            elements[0].checked = true;
                        }
                    }

                    $(".right_variant").each(function () {

                        $(this).change(function () {
                            $(".right_variant").prop('checked', false);
                            $(this).prop('checked', true);
                        });
                    });
                </script>

                <div class="my_form_item mb-3">
                    <p>Content Language(en, ua):</p>
                    <input type="text" name="content_language" placeholder="Enter content language(en, ua)">
                </div>
                <input type="submit" value="ADD QUESTION">
            </form>
        </div>
    </div>
</div>
</body>

</html>
