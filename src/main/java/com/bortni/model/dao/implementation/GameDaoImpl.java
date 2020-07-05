package com.bortni.model.dao.implementation;

import com.bortni.model.dao.GameDao;
import com.bortni.model.dao.specification.Specification;
import com.bortni.model.entity.Configuration;
import com.bortni.model.entity.Game;
import com.bortni.model.entity.Statistics;
import com.bortni.model.database_mapper.GameDatabaseMapper;
import com.bortni.model.entity.User;
import com.bortni.model.exception.EntityNotFoundException;
import com.bortni.model.exception.MySqlException;
import com.bortni.model.sql_query.GameSqlQuery;
import com.bortni.model.sql_query.QuestionsSqlQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDaoImpl implements GameDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDaoImpl.class);

    private final Connection connection;
    private Specification<Game> specification;
    GameDatabaseMapper gameMapper;


    public GameDaoImpl(Connection connection) {
        this.connection = connection;
        gameMapper = new GameDatabaseMapper();
        specification = new Specification<>(gameMapper, connection);
    }


    @Override
    public Game save(Game entity) {
        String sql = GameSqlQuery.CREATE;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            Configuration configuration = entity.getConfiguration();

            preparedStatement.setString(1, entity.getGameIdentification());
            preparedStatement.setInt(2, configuration.getRoundTime());
            preparedStatement.setInt(3, configuration.getPlayersNumber());
            preparedStatement.setInt(4, configuration.getRoundsNumber());
            preparedStatement.setBoolean(5, entity.isAvailable());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in saving game: {}", e.getMessage());
            throw new MySqlException("Sql error in saving game", e);
        }
        return entity;
    }

    @Override
    public void update(Game entity) {
        String sql = GameSqlQuery.UPDATE;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            Statistics statistics = entity.getStatistics();

            preparedStatement.setInt(1, statistics.getExpertScore());
            preparedStatement.setInt(2, statistics.getOpponentScore());
            preparedStatement.setBoolean(3, entity.isAvailable());
            preparedStatement.setInt(4, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in updating game: {}", e.getMessage());
            throw new MySqlException("Sql error in updating game", e);

        }
    }

    @Override
    public void delete(Game entity) {
        String sql = GameSqlQuery.DELETE;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, entity.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in deleting game: {}", e.getMessage());
            throw new MySqlException("Sql error in deleting game", e);

        }

    }

    @Override
    public List<Game> findAll() {

        String sql = GameSqlQuery.FIND_ALL;

        return findAll(specification, sql);
    }


    @Override
    public Game findById(int id) {

        String sql = GameSqlQuery.FIND_BY_ID;

        return findById(specification, sql, id);
    }

    @Override
    public Game findByIdentificator(String identificator) {
        String sql = GameSqlQuery.FIND_BY_IDENT;
        Game game;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, identificator);

            final ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                game = gameMapper.getFromResultSet(resultSet);
            }
            else {
                LOGGER.info("No games found");
                throw new EntityNotFoundException("game");
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in finding game by identificator: {}", e.getMessage());
            throw new MySqlException("Sql error in finding game by identificator", e);
        }

        return game;
    }

    @Override
    public void saveUserToGame(User user, Game game) {

        String sql = GameSqlQuery.CREATE_USER_GAME;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, game.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Sql error in saving user to game: {}", e.getMessage());
            throw new MySqlException("Sql error in saving user to game", e);
        }

    }

    @Override
    public List<Game> findByUserId(int id) {
        String sql = GameSqlQuery.FIND_BY_USER_ID;

        List<Game> gameList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                gameList.add(new GameDatabaseMapper().getFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in finding games by user id: {}", e.getMessage());
            throw new MySqlException("Sql error in finding games by user id", e);
        }
        return gameList;

    }

    @Override
    public List<Game> findByUserId(int id, long from, long to) {

        String sql = GameSqlQuery.FIND_BY_USER_ID_PAGINATED;

        List<Game> gameList = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, id);
            preparedStatement.setLong(2, from);
            preparedStatement.setLong(3, to);

            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                gameList.add(new GameDatabaseMapper().getFromResultSet(resultSet));
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in finding games by user id paginated: {}", e.getMessage());
            throw new MySqlException("Sql error in finding games by user id paginated", e);
        }
        return gameList;

    }

    @Override
    public long getGamesCountByUserId(int userId) {
        String sql = GameSqlQuery.COUNT_ALL_BY_USER_ID;

        long count = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            final ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                count = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            LOGGER.error("Sql error in counting questions");
            throw new MySqlException("Sql error in counting questions", e);
        }
        return count;
    }

    @Override
    public void close() {
        try{
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error in closing connection");
            throw new MySqlException("Error in closing connection", e);
        }
    }
}
