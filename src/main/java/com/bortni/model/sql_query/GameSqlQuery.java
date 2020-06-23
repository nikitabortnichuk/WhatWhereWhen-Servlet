package com.bortni.model.sql_query;

public class GameSqlQuery {

    public static final String FIND_ALL = "SELECT * FROM games ";

    public static final String FIND_BY_ID = FIND_ALL +  "WHERE games.game_id = ? ";

    public static final String FIND_BY_IDENT = FIND_ALL + "WHERE game_identification = ?";

    public static final String CREATE = "INSERT INTO " +
            "games(game_identification, round_time, players_number, rounds_number, is_available) " +
            "VALUES (?, ?, ?, ?, ?) ";

    public static final String UPDATE = "UPDATE games " +
            "SET expert_score = ?, opponent_score = ?, is_available = ? " +
            "WHERE games.game_id = ? ";

    public static final String DELETE = "DELETE FROM games WHERE games.game_id = ? ";

    public static final String FIND_BY_USER_ID = "SELECT * FROM users_games " +
            "JOIN users ON users_games.user_id = users.user_id " +
            "JOIN games ON users_games.game_id = games.game_id " +
            "AND users.user_id = ? ";

    public static final String CREATE_USER_GAME = "INSERT INTO users_games " +
            "(user_id, game_id) VALUES (?, ?) ";

    public static final String FIND_BY_USER_ID_PAGINATED = FIND_BY_USER_ID + "LIMIT ?,? ";

    public static final String COUNT_ALL_BY_USER_ID = "SELECT COUNT(*) FROM users_games " +
            "JOIN users ON users_games.user_id = users.user_id " +
            "JOIN games ON users_games.game_id = games.game_id " +
            "AND users.user_id = ? ";
}
