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
                .id(resultSet.getInt("games.game_id"))
                .gameIdentification(resultSet.getString("game_identification"))
                .configuration(
                        Configuration.builder()
                                .roundTime(resultSet.getShort("round_time"))
                                .playersNumber(resultSet.getByte("players_number"))
                                .roundsNumber(resultSet.getByte("rounds_number"))
                                .build()
                )
                .statistics(
                        Statistics.builder()
                                .expertScore(resultSet.getShort("expert_score"))
                                .opponentScore(resultSet.getShort("opponent_score"))
                                .numberOfUsedHints(resultSet.getShort("number_of_used_hints"))
                                .averageTimePerRound(resultSet.getShort("average_time_per_round"))
                                .averageScorePerRound(resultSet.getShort("average_score_per_round"))
                                .build()
                )
                .isAvailable(resultSet.getBoolean("is_available"))
                .build();
    }
}
