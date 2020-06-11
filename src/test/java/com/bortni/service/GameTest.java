package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.GameDao;
import com.bortni.model.entity.Game;
import com.bortni.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameTest {

    @Mock
    DaoFactory daoFactory;

    @Mock
    GameDao gameDao;

    @InjectMocks
    GameService gameService = new GameService();

    @Test
    public void shouldCallSave_WhenSaveGame(){
        when(daoFactory.createGameDao()).thenReturn(gameDao);
        gameService.save(getGame());
        verify(gameDao).save(any());
    }

    @Test
    public void shouldCallUpdate_WhenUpdateGame(){
        when(daoFactory.createGameDao()).thenReturn(gameDao);
        gameService.update(getGame());
        verify(gameDao).update(any());
    }

    @Test
    public void shouldReturnGame_WhenFindByIdentificator(){
        when(daoFactory.createGameDao()).thenReturn(gameDao);
        when(gameDao.findByIdentificator(anyString())).thenReturn(getGame());
        Game expected = getGame();
        Game actual = gameService.findByIdent("ident");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnGameList_WhenFindById(){
        when(daoFactory.createGameDao()).thenReturn(gameDao);
        when(gameDao.findByUserId(anyInt())).thenReturn(getGameList());

        List<Game> expected = getGameList();
        List<Game> actual = gameService.findByUserId(1);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowException_WhenLessThanTwo(){
        int number = 1;
        assertThrows(RuntimeException.class, () -> gameService.checkIfLessThanTwoOrThrowException(number));
    }

    @Test
    public void shouldSaveUserToGame_WhenSaveUserToGame(){
        when(daoFactory.createGameDao()).thenReturn(gameDao);
        gameService.saveUserToGame(getUser(), getGame());

        verify(gameDao).saveUserToGame(any(), any());
    }

    private User getUser() {
        return User.builder()
                .username("u")
                .password("p")
                .email("e")
                .build();
    }

    private Game getGame(){
        return Game.builder()
                .gameIdentification("gameId")
                .password("password")
                .build();
    }

    private List<Game> getGameList(){
        List<Game> gameList = new ArrayList<>();
        gameList.add(
                Game.builder()
                        .gameIdentification("g1")
                        .password("p1")
                        .build()
        );
        gameList.add(
                Game.builder()
                        .gameIdentification("g2")
                        .password("p2")
                        .build()
        );
        gameList.add(
                Game.builder()
                        .gameIdentification("g3")
                        .password("p3")
                        .build()
        );

        return gameList;
    }
}
