package com.bortni.controller.command.admin;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.Routes;
import com.bortni.model.entity.question.Question;
import com.bortni.service.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminUpdatePageQuestionCommand implements Command {

    private QuestionService questionService;

    public AdminUpdatePageQuestionCommand(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int questionId = Integer.parseInt(request.getParameter("question_id"));

        Question question = questionService.findById(questionId);

        request.setAttribute("question", question);

        request.getRequestDispatcher(Routes.ADMIN_ADD_QUESTION).forward(request, response);
    }
}
