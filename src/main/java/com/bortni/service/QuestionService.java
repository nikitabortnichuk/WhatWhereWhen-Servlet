package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.QuestionsDao;
import com.bortni.model.dao.VariantDao;
import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionType;
import com.bortni.model.entity_mapper.QuestionEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuestionService {

    final Logger logger = LoggerFactory.getLogger(QuestionService.class); //todo configure logger

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private QuestionEntityMapper entityMapper = new QuestionEntityMapper();

    public List<Question> findAll() {
        try (QuestionsDao questionsDao = daoFactory.createQuestionsDao();
             VariantDao variantDao = daoFactory.createVariantDao()) {

            List<Question> questionList = questionsDao.findAll();
            entityMapper.setVariantsAndAnswerToQuestionListWithVariants(variantDao, questionList);

            return questionList;
        }
    }

    public Question findById(int id) {
        try (QuestionsDao questionsDao = daoFactory.createQuestionsDao();
             VariantDao variantDao = daoFactory.createVariantDao()) {

            Question question = questionsDao.findById(id);
            entityMapper.setVariantsAndAnswerToOneQuestionWithVariants(variantDao, question);

            return question;
        }
    }

    public Question save(Question question) {

        QuestionType questionType = question.getQuestionType();

        try(QuestionsDao questionsDao = getQuestionsDao(questionType)){
            return questionsDao.save(question);
        }
    }

    public void update(Question question){
        QuestionType questionType = question.getQuestionType();

        try(QuestionsDao questionsDao = getQuestionsDao(questionType)){
            questionsDao.update(question);
        }
    }

    public void delete(int id){
        Question question = new Question();
        question.setId(id);
        try(QuestionsDao questionsDao = daoFactory.createQuestionsDao()){
            questionsDao.delete(question);
        }
    }

    public void checkNotNull(){

    }


    private QuestionsDao getQuestionsDao(QuestionType type){
        QuestionsDao questionsDao;
        switch (type){
            case WITH_VARIANTS:
                questionsDao = daoFactory.createQuestionsWithVariantsDao();
                break;
            case NO_VARIANTS:
                questionsDao = daoFactory.createQuestionsNoVariantsDao();
                break;
            default:
                questionsDao = daoFactory.createQuestionsDao();
        }

        return questionsDao;
    }

}
