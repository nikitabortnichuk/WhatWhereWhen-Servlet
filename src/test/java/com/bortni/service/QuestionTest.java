package com.bortni.service;

import com.bortni.controller.websocket.GameSessionHandler;
import com.bortni.model.entity.question.Question;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuestionTest {

    QuestionService questionService = new QuestionService();

    @Test
    public void checkNumber_WhenGetNRandomQuestions(){
        int number = 10;
        int actual = questionService.getNRandomQuestions(10).size();

        assertEquals(number, actual);
    }

    @Test
    public void check_WhenGetNRandomQuestions(){
        questionService.getNRandomQuestions(5)
                .forEach(System.out::println);
    }

    @Test
    public void checkJson_WhenGetJsonOfQuestionList(){

        List<Question> questionList = questionService.getNRandomQuestions(5);

        System.out.println(questionService.getJsonOfQuestionList(questionList));
    }
}
