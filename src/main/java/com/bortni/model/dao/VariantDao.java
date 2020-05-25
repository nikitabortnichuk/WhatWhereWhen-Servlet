package com.bortni.model.dao;

import com.bortni.model.entity.Variant;
import com.bortni.model.entity.question.Question;

import java.util.List;

public interface VariantDao extends Dao<Variant> {
    List<Variant> findVariantsByQuestionId(Question question);
}
