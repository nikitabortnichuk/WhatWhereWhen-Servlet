package com.bortni.service;

import com.bortni.model.dao.DaoFactory;
import com.bortni.model.dao.QuestionsDao;
import com.bortni.model.dao.UserDao;
import com.bortni.model.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @Mock
    DaoFactory daoFactory;

    @Mock
    UserDao userDao;

    @InjectMocks
    UserService userService = new UserService();

    @Test
    public void shouldCallSave_WhenSave(){
        when(daoFactory.createUserDao()).thenReturn(userDao);
        userService.save(getUser());
        verify(userDao).save(any());
    }

    @Test
    public void shouldReturnUser_WhenFindByUsernameAndPassword(){
        when(userDao.findByUsernameAndPassword(anyString(), anyString())).thenReturn(getUser());

        User expected = getUser();
        User actual = userDao.findByUsernameAndPassword("username", "password");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnUser_WhenFindByUsername() {
        when(userDao.findByUsername(anyString())).thenReturn(getUser());

        User expected = getUser();
        User actual = userDao.findByUsername("username");

        assertEquals(expected, actual);
    }

    private User getUser(){
        return User.builder()
                .id(1)
                .username("u")
                .password("p")
                .email("e")
                .build();
    }


}
