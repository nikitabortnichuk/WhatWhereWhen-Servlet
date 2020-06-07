package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.QuestionsDao;
import com.bortni.model.dao.VariantDao;
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

        try (QuestionsDao questionsDao = getQuestionsDao(questionType)) {
            return questionsDao.save(question);
        }
    }

    public void update(Question question) {
        QuestionType questionType = question.getQuestionType();

        try (QuestionsDao questionsDao = getQuestionsDao(questionType)) {
            questionsDao.update(question);
        }
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

        return randomQuestions;
    }

    public JsonArrayBuilder getJsonOfQuestionList(List<Question> questions) {

        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        questions.forEach(question -> jsonArrayBuilder
                .add(Json.createObjectBuilder()
                        .add("id", question.getId())
                        .add("text", question.getQuestionText())
                        .add("answer", question.getAnswer())
                        .add("variantList", getJsonOfVariantListOrNull(question.getVariantList()))
                        .add("questionType", question.getQuestionType().toString())
                )
        );

        return jsonArrayBuilder;
    }

    private JsonArrayBuilder getJsonOfVariantListOrNull(List<Variant> variants) {
        if(variants == null){
            return Json.createArrayBuilder();
        }
        else {
            return getJsonOfVariantList(variants);
        }
    }

    private JsonArrayBuilder getJsonOfVariantList(List<Variant> variants) {
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        variants.forEach(variant -> jsonArrayBuilder
                .add(Json.createObjectBuilder()
                        .add("id", variant.getId())
                        .add("text", variant.getText())
                        .add("isCorrect", variant.isCorrect())
                )
        );
        return jsonArrayBuilder;
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

        return questionsDao;
    }

}
