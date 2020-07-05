package com.bortni.model.entity.question;

import com.bortni.model.entity.Variant;

import java.util.List;
import java.util.Objects;

public class Question {

    private int id;
    private String questionText;
    private String answer;
    private List<Variant> variantList;
    private QuestionType questionType;

    public Question() {
    }

    public Question(int id, String questionText, String answer, List<Variant> variantList, QuestionType questionType) {
        this.id = id;
        this.questionText = questionText;
        this.answer = answer;
        this.variantList = variantList;
        this.questionType = questionType;
    }

    public static QuestionBuilder builder(){
        return new QuestionBuilder();
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getQuestionText(){
        return questionText;
    }

    public void setQuestionText(String questionText){
        this.questionText = questionText;
    }

    public QuestionType getQuestionType(){
        return questionType;
    }

    public void setQuestionType(QuestionType questionType){
        this.questionType = questionType;
    }

    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }

    public List<Variant> getVariantList(){
        return variantList;
    }

    public void setVariantList(List<Variant> variantList){
        this.variantList = variantList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        Question question = (Question) o;
        return id == question.id &&
                questionText.equals(question.questionText) &&
                answer.equals(question.answer) &&
                variantList == question.variantList &&
                questionType == question.questionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionText, answer, variantList, questionType);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", answer='" + answer + '\'' +
                ", variantList=" + variantList +
                ", questionType=" + questionType +
                '}';
    }

    public static class QuestionBuilder {
        private int id;
        private String questionText;
        private String answer;
        private List<Variant> variantList;
        private QuestionType questionType;

        public QuestionBuilder id(int id) {
            this.id = id;
            return this;
        }

        public QuestionBuilder questionText(String questionText) {
            this.questionText = questionText;
            return this;
        }

        public QuestionBuilder answer(String answer) {
            this.answer = answer;
            return this;
        }

        public QuestionBuilder variantList(List<Variant> variantList) {
            this.variantList = variantList;
            return this;
        }

        public QuestionBuilder questionType(QuestionType questionType) {
            this.questionType = questionType;
            return this;
        }

        public Question build(){
            return new Question(id, questionText, answer, variantList, questionType);
        }
    }

}
