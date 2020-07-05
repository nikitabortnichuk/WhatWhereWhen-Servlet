package com.bortni.model.entity;

import java.util.Objects;

public class Variant {

    private int id;
    private String text;
    private boolean isCorrect;
    private int questionId;

    public Variant(int id, String text, boolean isCorrect, int questionId) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
        this.questionId = questionId;
    }

    public Variant() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static VariantBuilder builder() {
        return new VariantBuilder();
    }

    public String getText() {
        return this.text;
    }


    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variant)) return false;
        Variant variant = (Variant) o;
        return id == variant.id &&
                isCorrect == variant.isCorrect &&
                questionId == variant.questionId &&
                text.equals(variant.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, isCorrect, questionId);
    }

    @Override
    public String toString() {
        return "Variant{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                ", questionId=" + questionId +
                '}';
    }

    public static class VariantBuilder {
        private int id;
        private String text;
        private boolean isCorrect;
        private int questionId;

        VariantBuilder() {
        }

        public VariantBuilder id(int id){
            this.id = id;
            return this;
        }

        public VariantBuilder text(String text) {
            this.text = text;
            return this;
        }

        public VariantBuilder isCorrect(boolean isCorrect){
            this.isCorrect = isCorrect;
            return this;
        }

        public VariantBuilder questionId(int questionId){
            this.questionId = questionId;
            return this;
        }

        public Variant build() {
            return new Variant(id, text, isCorrect, questionId);
        }

        @Override
        public String toString() {
            return "VariantBuilder{" +
                    "id=" + id +
                    ", text='" + text + '\'' +
                    ", isCorrect=" + isCorrect +
                    ", questionId=" + questionId +
                    '}';
        }
    }
}
