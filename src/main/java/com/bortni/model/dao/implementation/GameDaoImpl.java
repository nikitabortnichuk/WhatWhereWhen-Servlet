package com.bortni.model.dao.implementation;

import com.bortni.model.dao.GameDao;
import com.bortni.model.dao.specification.Specification;
import com.bortni.model.entity.Configuration;
import com.bortni.model.entity.Game;
import com.bortni.model.entity.Statistics;
import com.bortni.model.database_mapper.GameDatabaseMapper;
import com.bortni.model.sql_query.GameSqlQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GameDaoImpl implements GameDao {

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

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            //log
            throw new RuntimeException();
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
            preparedStatement.setInt(3, statistics.getNumberOfUsedHints());
            preparedStatement.setInt(4, statistics.getAverageTimePerRound());
            preparedStatement.setInt(5, statistics.getAverageScorePerRound());
            preparedStatement.setInt(6, entity.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            //log
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Game entity) {
        String sql = GameSqlQuery.DELETE;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setInt(1, entity.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            //log
            throw new RuntimeException();
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
            else throw new RuntimeException();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return game;
    }

    @Override
    public void close() {
        try{
            connection.close();
        } catch (SQLException e) {
            //log
            throw new RuntimeException();
        }
    }
}
