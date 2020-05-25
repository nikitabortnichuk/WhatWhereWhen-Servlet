package com.bortni.model.entity;

import java.util.Objects;

public class StatisticsFormat {

    private Statistics statistics;

    public StatisticsFormat(Statistics statistics) {
        this.statistics = statistics;
    }

    public StatisticsFormat() {
    }

    public Statistics getStatistics() {
        return this.statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }

    public static StatisticsFormatBuilder builder() {
        return new StatisticsFormatBuilder();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticsFormat that = (StatisticsFormat) o;
        return statistics.equals(that.statistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statistics);
    }

    @Override
    public String toString() {
        return "StatisticsFormat{" +
                "statistics=" + statistics +
                '}';
    }

    public static class StatisticsFormatBuilder {
        private Statistics statistics;

        StatisticsFormatBuilder() {
        }

        public StatisticsFormat.StatisticsFormatBuilder statistics(Statistics statistics) {
            this.statistics = statistics;
            return this;
        }

        public StatisticsFormat build() {
            return new StatisticsFormat(statistics);
        }

        public String toString() {
            return "StatisticsFormat.StatisticsFormatBuilder(statistics=" + this.statistics + ")";
        }
    }
}
