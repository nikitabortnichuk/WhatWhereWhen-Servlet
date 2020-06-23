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
        final long recordsPerPage = 5L;
        int currentPage;
        if (request.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        } else {
            currentPage = 1;
        }

        long from = (currentPage - 1) * recordsPerPage;

        List<Question> questionList = questionService.findAll(from, recordsPerPage);
        request.setAttribute("questions", questionList);

        long rows = questionService.getQuestionsCount();
        long nOfPages = rows / recordsPerPage;
        long rowsOnPage = rows % recordsPerPage;
        if (rowsOnPage > 0) {
            nOfPages++;
        }

        request.setAttribute("nOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);

        request.getRequestDispatcher(Routes.ADMIN_SHOW_QUESTIONS).forward(request, response);
    }
}
