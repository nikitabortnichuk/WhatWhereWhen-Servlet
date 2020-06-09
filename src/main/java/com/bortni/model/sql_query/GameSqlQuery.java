package com.bortni.model.sql_query;

public class GameSqlQuery {

    public static final String FIND_ALL = "SELECT * FROM games ";

    public static final String FIND_BY_ID = FIND_ALL +  "WHERE games.game_id = ? ";

    public static final String FIND_BY_IDENT = FIND_ALL + "WHERE game_identification = ?";

    public static final String CREATE = "INSERT INTO " +
            "games(game_identification, round_time, players_number, rounds_number, is_available) " +
            "VALUES (?, ?, ?, ?, ?) ";

    public static final String UPDATE = "UPDATE games " +
            "SET expert_score = ?, opponent_score = ?, number_of_used_hints = ?, " +
            "average_time_per_round = ?, average_score_per_round = ?, is_available = ? " +
            "WHERE games.game_id = ? ";

    public static final String DELETE = "DELETE FROM games WHERE games.game_id = ? ";

}
