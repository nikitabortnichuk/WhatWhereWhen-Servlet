package com.bortni.model.sql_query;

public class QuestionsSqlQuery {

    public static final String FIND_ALL = "SELECT * FROM questions ";

    public static final String FIND_BY_ID = FIND_ALL + "WHERE questions.question_id = ? ";

    public static final String FIND_ALL_DESC = FIND_ALL + "ORDER BY questions.question_id DESC ";

    public static final String SAVE_NO_VARIANTS = "INSERT INTO questions(text, answer, question_type) VALUES (?, ?, ?) ";

    public static final String SAVE_WITH_VARIANTS = "INSERT INTO questions(text, question_type) VALUES (?, ?) ";

    public static final String UPDATE_NO_VARIANTS = "UPDATE questions SET text = ?, answer = ?, question_type = ? WHERE questions.question_id = ? ";

    public static final String UPDATE_WITH_VARIANTS = "UPDATE questions SET text = ?, question_type = ? WHERE questions.question_id = ? ";

    public static final String DELETE = "DELETE FROM questions WHERE questions.question_id = ? ";

    public static final String FIND_ALL_PAGINATED = FIND_ALL_DESC + "LIMIT ?, ?";
    public static final String COUNT_ALL = "SELECT COUNT(*) FROM questions ";
}
