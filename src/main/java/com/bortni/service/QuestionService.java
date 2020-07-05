package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.QuestionsDao;
import com.bortni.model.dao.VariantDao;
import com.bortni.model.database_mapper.QuestionDatabaseMapper;
import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;
import com.bortni.model.entity.question.QuestionType;
import com.bortni.model.entity_mapper.QuestionEntityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class QuestionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionService.class);

    private DaoFactory daoFactory = DaoFactory.getInstance();

    private QuestionEntityMapper entityMapper = new QuestionEntityMapper();

    public List<Question> findAll() {
        try (QuestionsDao questionsDao = daoFactory.createQuestionsDao();
             VariantDao variantDao = daoFactory.createVariantDao()) {

            List<Question> questionList = questionsDao.findAll();
            questionList.forEach(question ->
                    entityMapper.setVariantsAndAnswerToOneQuestionWithVariants(variantDao, question));
            LOGGER.info("Getting all questions");
            return questionList;
        }
    }

    public List<Question> findAll(long from, long to) {
        try (QuestionsDao questionsDao = daoFactory.createQuestionsDao();
             VariantDao variantDao = daoFactory.createVariantDao()) {

            List<Question> questionList = questionsDao.findAll(from, to);
            questionList.forEach(question ->
                    entityMapper.setVariantsAndAnswerToOneQuestionWithVariants(variantDao, question));
            LOGGER.info("Getting all questions paginated");
            return questionList;
        }
    }

    public Question findById(int id) {
        try (QuestionsDao questionsDao = daoFactory.createQuestionsDao();
             VariantDao variantDao = daoFactory.createVariantDao()) {

            Question question = questionsDao.findById(id);
            entityMapper.setVariantsAndAnswerToOneQuestionWithVariants(variantDao, question);
            LOGGER.info("Getting question by id");
            return question;
        }
    }

    public Question save(Question question) {

        QuestionType questionType = question.getQuestionType();

        try (QuestionsDao questionsDao = getQuestionsDao(questionType)) {
            question = questionsDao.save(question);
        }
        LOGGER.info("Saving question");
        return question;
    }

    public void update(Question question) {
        QuestionType questionType = question.getQuestionType();

        try (QuestionsDao questionsDao = getQuestionsDao(questionType)) {
            questionsDao.update(question);
        }
        LOGGER.info("Updating question");
    }

    public void delete(int id) {
        Question question = new Question();
        question.setId(id);
        try (QuestionsDao questionsDao = daoFactory.createQuestionsDao()) {
            questionsDao.delete(question);
        }
    }

    public List<Question> getNRandomQuestions(int questionsNumber) {
        List<Question> allQuestions = findAll();
        List<Question> randomQuestions = new ArrayList<>();

        int size = allQuestions.size();
        Random random = new Random();
        random.ints(0, size)
                .distinct()
                .limit(questionsNumber)
                .forEach(n -> randomQuestions.add(allQuestions.get(n)));
        LOGGER.debug("Getting {} random questions", questionsNumber);
        return randomQuestions;
    }

    private QuestionsDao getQuestionsDao(QuestionType type) {
        QuestionsDao questionsDao;
        switch (type) {
            case WITH_VARIANTS:
                questionsDao = daoFactory.createQuestionsWithVariantsDao();
                break;
            case NO_VARIANTS:
                questionsDao = daoFactory.createQuestionsNoVariantsDao();
                break;
            default:
                questionsDao = daoFactory.createQuestionsDao();
        }
        LOGGER.debug("Getting questionDao type: {}", questionsDao.getClass());
        return questionsDao;
    }

    public long getQuestionsCount() {
        try(QuestionsDao questionsDao = daoFactory.createQuestionsDao()) {
            return questionsDao.getQuestionsCount();
        }
    }
}
