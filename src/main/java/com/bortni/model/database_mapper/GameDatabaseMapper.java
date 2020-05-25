package com.bortni.model.database_mapper;

import com.bortni.model.entity.Configuration;
import com.bortni.model.entity.Game;
import com.bortni.model.entity.Statistics;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDatabaseMapper implements DatabaseMapper<Game> {
    @Override
    public Game getFromResultSet(ResultSet resultSet) throws SQLException {
        return Game.builder()
                .id(resultSet.getInt(1))
                .gameIdentification(resultSet.getString(2))
                .configuration(
                        Configuration.builder()
                                .roundTime(resultSet.getShort(3))
                                .playersNumber(resultSet.getByte(4))
                                .roundsNumber(resultSet.getByte(5))
                                .build()
                )
                .statistics(
                        Statistics.builder()
                                .expertScore(resultSet.getShort(6))
                                .opponentScore(resultSet.getShort(7))
                                .numberOfUsedHints(resultSet.getShort(8))
                                .averageTimePerRound(resultSet.getShort(9))
                                .averageScorePerRound(resultSet.getShort(10))
                                .build()
                )
                .build();
    }
}
