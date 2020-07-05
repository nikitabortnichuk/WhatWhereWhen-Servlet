package com.bortni.model.dao;

import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;

import java.util.List;

public interface QuestionsDao extends Dao<Question> {
    List<Question> findAll(long from, long to);

    long getQuestionsCount();
}
