package com.bortni.model.entity_mapper;

import com.bortni.model.dao.QuestionsDao;
import com.bortni.model.dao.VariantDao;
import com.bortni.model.database_mapper.VariantMapper;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionType;

import java.util.List;

public class QuestionEntityMapper implements EntityMapper {

    public void setVariantsAndAnswerToOneQuestionWithVariants(VariantDao variantDao, Question question){
        if (isWithVariants(question)) {
            question.setVariantList(variantDao.findVariantsByQuestionId(question));
            question.setAnswer(findAnswer(question));
        }
    }

    private boolean isWithVariants(Question question) {
        return question.getQuestionType().equals(QuestionType.WITH_VARIANTS);
    }

    private String findAnswer(Question question) {
        return question.getVariantList().stream()
                .filter(Variant::isCorrect)
                .findAny()
                .orElseThrow(RuntimeException::new)
                .getText();
    }

}
