package com.bortni.controller.command.admin;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.UrlPath;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionType;
import com.bortni.service.QuestionService;
import com.bortni.service.VariantService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminSaveQuestionCommand implements Command {

    private QuestionService questionService;
    private VariantService variantService;

    public AdminSaveQuestionCommand(QuestionService questionService, VariantService variantService) {
        this.questionService = questionService;
        this.variantService = variantService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String questionText = request.getParameter("question_text");
        QuestionType questionType = QuestionType.valueOf(request.getParameter("question_type"));
        String answer = request.getParameter("answer");

        Question question = new Question();
        question.setQuestionText(questionText);
        question.setQuestionType(questionType);
        question.setAnswer(answer);

        doSaveOrUpdateByUri(request, question);

        response.sendRedirect("/game-www" + UrlPath.ADMIN_SHOW_QUESTIONS);
    }

    private void doSaveOrUpdateByUri(HttpServletRequest request, Question question) {

        String questionIdString = request.getParameter("question_id");

        System.out.println(questionIdString + " -string");

        if (questionIdString.equals("")) {
            saveQuestion(request, question);
        } else {
            question.setId(Integer.parseInt(questionIdString));
            updateQuestion(request, question);
        }

    }

    private void saveQuestion(HttpServletRequest request, Question question) {
        Question savedQuestion = questionService.save(question);

        if (question.getQuestionType().equals(QuestionType.WITH_VARIANTS)) {
            List<Variant> variantList = getVariantList(request, savedQuestion);
            variantList.forEach(variantService::save);
        }
    }

    private void updateQuestion(HttpServletRequest request, Question question) {
        questionService.update(question);
        Question savedQuestion = questionService.findById(question.getId());

        if (question.getQuestionType().equals(QuestionType.WITH_VARIANTS)) {
            List<Variant> variantList = getVariantList(request, savedQuestion);
            List<Variant> variantListByQuestionId = variantService.getVariantListByQuestionId(question);
            variantService.mapListsForUpdate(variantList, variantListByQuestionId);
            variantList.forEach(variantService::update);
        }
    }

    private List<Variant> getVariantList(HttpServletRequest request, Question question) {

        String variant1 = request.getParameter("variant1");
        String variant2 = request.getParameter("variant2");
        String variant3 = request.getParameter("variant3");
        String variant4 = request.getParameter("variant4");

        boolean isRight1 = Boolean.parseBoolean(request.getParameter("right_variant_1"));
        boolean isRight2 = Boolean.parseBoolean(request.getParameter("right_variant_2"));
        boolean isRight3 = Boolean.parseBoolean(request.getParameter("right_variant_3"));
        boolean isRight4 = Boolean.parseBoolean(request.getParameter("right_variant_4"));

        List<Variant> variantList = new ArrayList<>();

        int questionId = question.getId();

        variantList.add(
                Variant.builder()
                        .text(variant1)
                        .isCorrect(isRight1)
                        .questionId(questionId)
                        .build()
        );
        variantList.add(
                Variant.builder()
                        .text(variant2)
                        .isCorrect(isRight2)
                        .questionId(questionId)
                        .build()
        );
        variantList.add(
                Variant.builder()
                        .text(variant3)
                        .isCorrect(isRight3)
                        .questionId(questionId)
                        .build()
        );
        variantList.add(
                Variant.builder()
                        .text(variant4)
                        .isCorrect(isRight4)
                        .questionId(questionId)
                        .build()
        );

        return variantList;
    }

}
