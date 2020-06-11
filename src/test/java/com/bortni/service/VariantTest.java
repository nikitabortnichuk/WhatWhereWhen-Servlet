package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.QuestionsDao;
import com.bortni.model.dao.VariantDao;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class VariantTest {

    @Mock
    DaoFactory daoFactory;

    @Mock
    VariantDao variantDao;

    @InjectMocks
    VariantService variantService = new VariantService();

    @Test
    public void shouldCallSave_WhenSave() {
        when(daoFactory.createVariantDao()).thenReturn(variantDao);

        variantService.save(getVariantList().get(1));
        verify(variantDao).save(any());
    }

    @Test
    public void shouldCallUpdate_WhenSave() {
        when(daoFactory.createVariantDao()).thenReturn(variantDao);

        variantService.update(getVariantList().get(0));
        verify(variantDao).update(any());
    }

    @Test
    public void shouldReturnVariantList_WhenGetVariantListByQuestionId() {
        when(daoFactory.createVariantDao()).thenReturn(variantDao);
        when(variantDao.findVariantsByQuestionId(any())).thenReturn(getVariantList());

        List<Variant> expected = getVariantList();
        List<Variant> actual = variantService.getVariantListByQuestionId(getQuestion());

        assertEquals(expected, actual);
    }

    private List<Variant> getVariantList() {
        List<Variant> variantList = new ArrayList<>();
        variantList.add(
                Variant.builder()
                        .id(1)
                        .text("text1")
                        .isCorrect(true)
                        .build()
        );
        variantList.add(
                Variant.builder()
                        .id(2)
                        .text("text2")
                        .isCorrect(false)
                        .build()
        );
        variantList.add(
                Variant.builder()
                        .id(3)
                        .text("text3")
                        .isCorrect(false)
                        .build()
        );
        return variantList;
    }

    private Question getQuestion() {
        return Question.builder()
                .id(1)
                .questionText("text")
                .answer("answer")
                .questionType(QuestionType.WITH_VARIANTS)
                .build();
    }
}