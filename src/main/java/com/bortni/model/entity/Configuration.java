package com.bortni.model.entity;


import java.util.Objects;

public class Configuration {

    private int roundTime;
    private int playersNumber;
    private int roundsNumber;

    private Game game;

    private StatisticsFormat statisticsFormat;

    public Configuration() {
    }

    public Configuration(int roundTime, int playersNumber, int roundsNumber, Game game, StatisticsFormat statisticsFormat) {
        this.roundTime = roundTime;
        this.playersNumber = playersNumber;
        this.roundsNumber = roundsNumber;
        this.game = game;
        this.statisticsFormat = statisticsFormat;
    }

    public static ConfigurationBuilder builder() {
        return new ConfigurationBuilder();
    }

    public int getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(int roundTime) {
        this.roundTime = roundTime;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public int getRoundsNumber() {
        return roundsNumber;
    }

    public void setRoundsNumber(int roundsNumber) {
        this.roundsNumber = roundsNumber;
    }

    public StatisticsFormat getStatisticsFormat() {
        return statisticsFormat;
    }

    public void setStatisticsFormat(StatisticsFormat statisticsFormat) {
        this.statisticsFormat = statisticsFormat;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return roundTime == that.roundTime &&
                playersNumber == that.playersNumber &&
                roundsNumber == that.roundsNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roundTime, playersNumber, roundsNumber, game, statisticsFormat);
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "roundTime=" + roundTime +
                ", playersNumber=" + playersNumber +
                ", roundsNumber=" + roundsNumber +
                ", statisticsFormat=" + statisticsFormat +
                '}';
    }


    public static class ConfigurationBuilder {
        private int roundTime;
        private int playersNumber;
        private int roundsNumber;
        private Game game;
        private StatisticsFormat statisticsFormat;

        ConfigurationBuilder() {
        }

        public ConfigurationBuilder roundTime(int roundTime) {
            this.roundTime = roundTime;
            return this;
        }

        public ConfigurationBuilder playersNumber(int playersNumber) {
            this.playersNumber = playersNumber;
            return this;
        }

        public ConfigurationBuilder roundsNumber(int roundsNumber) {
            this.roundsNumber = roundsNumber;
            return this;
        }

        public ConfigurationBuilder statisticsFormat(StatisticsFormat statisticsFormat) {
            this.statisticsFormat = statisticsFormat;
            return this;
        }

        public ConfigurationBuilder game(Game game) {
            this.game = game;
            return this;
        }

        public Configuration build() {
            return new Configuration(roundTime, playersNumber, roundsNumber, game, statisticsFormat);
        }

        public String toString() {
            return "Configuration.ConfigurationBuilder(roundTime=" + this.roundTime + ", playersNumber=" + this.playersNumber + ", roundsNumber=" + this.roundsNumber + ", statisticsFormat=" + this.statisticsFormat + ")";
        }
    }
}
