package com.bortni.model.sql_query;

public class VariantSqlQuery {

    public static final String FIND_ALL = "SELECT * FROM variants ";

    public static final String FIND_BY_ID = FIND_ALL + "WHERE variants.variant_id = ? ";

    public static final String SAVE = "INSERT INTO variants(text, question_id, is_correct) VALUES (?, ?, ?) ";

    public static final String UPDATE = "UPDATE variants SET text = ?, question_id = ?, is_correct = ? " +
            "WHERE variants.variant_id = ? ";

    public static final String DELETE = "DELETE FROM variants WHERE variants.variant_id = ? ";

    public static final String FIND_VARIANTS_BY_QUESTION_ID = "SELECT * FROM variants " +
            "JOIN questions ON variants.question_id = questions.question_id " +
            "AND variants.question_id = ? ";
}
