package com.bortni.controller.command.admin;

import com.bortni.controller.command.Command;
import com.bortni.controller.util.Routes;
import com.bortni.model.entity.question.Question;
import com.bortni.service.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminShowQuestionsCommand implements Command {

    private QuestionService questionService;

    public AdminShowQuestionsCommand(QuestionService questionService){
        this.questionService = questionService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Question> questionList = questionService.findAll();
        request.setAttribute("questions", questionList);

        request.getRequestDispatcher(Routes.ADMIN_SHOW_QUESTIONS).forward(request, response);
    }
}
