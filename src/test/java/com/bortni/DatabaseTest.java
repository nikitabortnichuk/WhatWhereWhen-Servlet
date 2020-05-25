package com.bortni;

import com.bortni.model.dao.UserDao;
import com.bortni.model.dao.implementation.UserDaoImpl;
import com.bortni.model.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseTest {


    UserDao userDao;

    @Test
    @Disabled
    public void testAddUsers() {

        List<User> userList = getUserList();

    }

    private List<User> getUserList() {

        List<User> userList = new ArrayList<>();
        userList.add(
                User.builder()
                        .id(1)
                        .nickname("beem")
                        .email("beem@mail.com")
                        .password("hello")
                        .build()
        );
        userList.add(
                User.builder()
                        .id(2)
                        .nickname("master")
                        .email("master@gmail.com")
                        .password("bye")
                        .build()
        );

        return userList;
    }

}
