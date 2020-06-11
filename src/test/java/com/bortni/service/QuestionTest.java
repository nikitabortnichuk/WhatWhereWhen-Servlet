package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.GameDao;
import com.bortni.model.dao.QuestionsDao;
import com.bortni.model.dao.VariantDao;
import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionType;
import com.bortni.model.entity_mapper.QuestionEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionTest {

    @Mock
    DaoFactory daoFactory;

    @Mock
    QuestionsDao questionsDao;

    @Mock
    VariantDao variantDao;

    @Mock
    QuestionEntityMapper questionEntityMapper;

    @InjectMocks
    QuestionService questionService = new QuestionService();

    @Test
    public void shouldReturnQuestionList_WhenFindAll(){
        when(daoFactory.createQuestionsDao()).thenReturn(questionsDao);
        when(daoFactory.createVariantDao()).thenReturn(variantDao);
        doNothing().when(questionEntityMapper).setVariantsAndAnswerToOneQuestionWithVariants(any(), any());
        when(questionsDao.findAll()).thenReturn(getQuestionList());

        List<Question> expected = getQuestionList();
        List<Question> actual = questionService.findAll();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnQuestion_WhenFindById(){
        when(daoFactory.createQuestionsDao()).thenReturn(questionsDao);
        when(daoFactory.createVariantDao()).thenReturn(variantDao);
        when(questionsDao.findById(anyInt())).thenReturn(getQuestionList().get(0));

        Question expected = getQuestionList().get(0);
        Question actual = questionService.findById(1);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldSaveQuestionWithVariants_WhenSave(){
        when(daoFactory.createQuestionsWithVariantsDao()).thenReturn(questionsDao);

        questionService.save(getQuestionList().get(0));
        verify(questionsDao).save(any());
    }

    @Test
    public void shouldSaveQuestionNoVariants_WhenSave(){
        when(daoFactory.createQuestionsNoVariantsDao()).thenReturn(questionsDao);

        questionService.save(getQuestionList().get(1));
        verify(questionsDao).save(any());
    }

    @Test
    public void shouldUpdateQuestionWithNoVariants_WhenUpdate(){
        when(daoFactory.createQuestionsNoVariantsDao()).thenReturn(questionsDao);

        questionService.update(getQuestionList().get(1));
        verify(questionsDao).update(any());
    }

    @Test
    public void shouldUpdateQuestionWithVariants_WhenSave(){
        when(daoFactory.createQuestionsWithVariantsDao()).thenReturn(questionsDao);

        questionService.update(getQuestionList().get(0));
        verify(questionsDao).update(any());
    }

    @Test
    public void shouldCallDelete_WhenDelete(){
        when(daoFactory.createQuestionsDao()).thenReturn(questionsDao);

        questionService.delete(1);
        verify(questionsDao).delete(any());
    }

    @Test
    public void shouldReturnNRandomQuestion_WhenGetNRandomQuestion(){
        when(daoFactory.createQuestionsDao()).thenReturn(questionsDao);
        when(questionsDao.findAll()).thenReturn(getQuestionList().subList(0,2));

        List<Question> questionList = questionService.getNRandomQuestions(2);

        int expected = getQuestionList().subList(0, 2).size();
        int actual = questionList.size();

        assertEquals(expected, actual);
        assertTrue(isElementsUnique(questionList));
    }

    private boolean isElementsUnique(List<Question> questions){
        return new HashSet<>(questions).size() == questions.size();
    }

    private List<Question> getQuestionList(){
        List<Question> questionList = new ArrayList<>();
        questionList.add(
                Question.builder()
                        .id(1)
                        .questionText("text1")
                        .answer("a1")
                        .questionType(QuestionType.WITH_VARIANTS)
                        .build()
        );
        questionList.add(
                Question.builder()
                        .id(2)
                        .questionText("text2")
                        .answer("a2")
                        .questionType(QuestionType.NO_VARIANTS)
                        .build()
        );
        questionList.add(
                Question.builder()
                        .id(3)
                        .questionText("text3")
                        .answer("a3")
                        .questionType(QuestionType.WITH_VARIANTS)
                        .build()
        );
        return questionList;
    }

    

}
