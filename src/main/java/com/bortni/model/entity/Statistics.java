package com.bortni.model.entity;

import java.util.Objects;

public class Statistics {

    private int id;

    private int expertScore;
    private int opponentScore;

    private int numberOfUsedHints;
    private int averageTimePerRound;
    private int averageScorePerRound;

    public Statistics() {
    }

    public Statistics(int id, int expertScore, int opponentScore, int numberOfUsedHints, int averageTimePerRound, int averageScorePerRound) {
        this.id = id;
        this.expertScore = expertScore;
        this.opponentScore = opponentScore;
        this.numberOfUsedHints = numberOfUsedHints;
        this.averageTimePerRound = averageTimePerRound;
        this.averageScorePerRound = averageScorePerRound;
    }

    public static StatisticsBuilder builder() {
        return new StatisticsBuilder();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfUsedHints() {
        return numberOfUsedHints;
    }

    public void setNumberOfUsedHints(int numberOfUsedHints) {
        this.numberOfUsedHints = numberOfUsedHints;
    }

    public int getAverageTimePerRound() {
        return averageTimePerRound;
    }

    public void setAverageTimePerRound(int averageTimePerRound) {
        this.averageTimePerRound = averageTimePerRound;
    }

    public int getAverageScorePerRound() {
        return averageScorePerRound;
    }

    public void setAverageScorePerRound(int averageScorePerRound) {
        this.averageScorePerRound = averageScorePerRound;
    }

    public int getExpertScore() {
        return expertScore;
    }

    public void setExpertScore(int expertScore) {
        this.expertScore = expertScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return id == that.id &&
                expertScore == that.expertScore &&
                opponentScore == that.opponentScore &&
                numberOfUsedHints == that.numberOfUsedHints &&
                averageTimePerRound == that.averageTimePerRound &&
                averageScorePerRound == that.averageScorePerRound;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expertScore, opponentScore, numberOfUsedHints, averageTimePerRound, averageScorePerRound);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "id=" + id +
                ", expertScore=" + expertScore +
                ", opponentScore=" + opponentScore +
                ", numberOfUsedHints=" + numberOfUsedHints +
                ", averageTimePerRound=" + averageTimePerRound +
                ", averageScorePerRound=" + averageScorePerRound +
                '}';
    }

    public static class StatisticsBuilder {
        private int id;
        private int expertScore;
        private int opponentScore;
        private int numberOfUsedHints;
        private int averageTimePerRound;
        private int averageScorePerRound;

        StatisticsBuilder() {
        }

        public StatisticsBuilder id(int id) {
            this.id = id;
            return this;
        }

        public StatisticsBuilder expertScore(int expertScore) {
            this.expertScore = expertScore;
            return this;
        }

        public StatisticsBuilder opponentScore(int opponentScore) {
            this.opponentScore = opponentScore;
            return this;
        }

        public StatisticsBuilder numberOfUsedHints(int numberOfUsedHints) {
            this.numberOfUsedHints = numberOfUsedHints;
            return this;
        }

        public StatisticsBuilder averageTimePerRound(int averageTimePerRound) {
            this.averageTimePerRound = averageTimePerRound;
            return this;
        }

        public StatisticsBuilder averageScorePerRound(int averageScorePerRound) {
            this.averageScorePerRound = averageScorePerRound;
            return this;
        }

        public Statistics build() {
            return new Statistics(id, expertScore, opponentScore, numberOfUsedHints, averageTimePerRound, averageScorePerRound);
        }

        @Override
        public String toString() {
            return "StatisticsBuilder{" +
                    "id=" + id +
                    ", expertScore=" + expertScore +
                    ", opponentScore=" + opponentScore +
                    ", numberOfUsedHints=" + numberOfUsedHints +
                    ", averageTimePerRound=" + averageTimePerRound +
                    ", averageScorePerRound=" + averageScorePerRound +
                    '}';
        }
    }
}
