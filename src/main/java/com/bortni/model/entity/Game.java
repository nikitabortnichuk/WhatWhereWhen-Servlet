package com.bortni.model.entity;

import com.bortni.model.entity.question.Question;

import java.util.List;

public class Game {

    private int id;
    private String gameIdentification;
    private String password;
    private List<User> userList;
    private Statistics statistics;
    private Configuration configuration;
    private List<Question> questionList;

    public Game() {
    }

    public Game(int id, String gameIdentification, String password, List<User> userList, Statistics statistics, Configuration configuration, List<Question> questionList) {
        this.id = id;
        this.gameIdentification = gameIdentification;
        this.password = password;
        this.userList = userList;
        this.statistics = statistics;
        this.configuration = configuration;
        this.questionList = questionList;
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGameIdentification() {
        return gameIdentification;
    }

    public void setGameIdentification(String gameIdentification) {
        this.gameIdentification = gameIdentification;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", gameIdentification='" + gameIdentification + '\'' +
                ", password=" + password +
                ", userList=" + userList +
                ", statistics=" + statistics +
                ", configuration=" + configuration +
                ", questionList=" + questionList +
                '}';
    }

    public static class GameBuilder {
        private int id;
        private String gameIdentification;
        private String password;
        private List<User> userList;
        private Statistics statistics;
        private Configuration configuration;
        private List<Question> questionList;

        GameBuilder() {
        }

        public GameBuilder id(int id) {
            this.id = id;
            return this;
        }

        public GameBuilder gameIdentification(String gameIdentification) {
            this.gameIdentification = gameIdentification;
            return this;
        }

        public GameBuilder password(String password){
            this.password = password;
            return this;
        }

        public GameBuilder userList(List<User> userList) {
            this.userList = userList;
            return this;
        }

        public GameBuilder statistics(Statistics statistics) {
            this.statistics = statistics;
            return this;
        }

        public GameBuilder configuration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public GameBuilder questionsList(List<Question> questionList) {
            this.questionList = questionList;
            return this;
        }

        public Game build() {
            return new Game(id, gameIdentification, password, userList, statistics, configuration, questionList);
        }
    }
}
