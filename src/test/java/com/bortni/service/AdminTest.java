package com.bortni.service;

import com.bortni.model.dao.AdminDao;
import com.bortni.model.dao.DaoFactory;
import com.bortni.model.entity.Admin;
import com.bortni.model.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminTest {

    @Mock
    DaoFactory daoFactory;

    @Mock
    AdminDao adminDao;

    @InjectMocks
    AdminService adminService;

    @BeforeEach
    public void setUp(){
        when(daoFactory.createAdminDao()).thenReturn(adminDao);
    }

    @Test
    public void shouldReturnAdmin_WhenFindByLoginAndPassword(){
        when(adminDao.findByLoginAndPassword(anyString(), anyString())).thenReturn(getAdmin());

        Admin expected = getAdmin();
        Admin actual = adminService.getAdministratorByLoginAndPassword("l", "p");

        assertEquals(expected, actual);
    }

    @Test
    public void shouldThrowEntityNotFound_WhenFindByLoginAndPassword(){
        when(adminDao.findByLoginAndPassword(anyString(), anyString())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class,
                () -> adminService.getAdministratorByLoginAndPassword("l", "p"));
    }

    private Admin getAdmin() {
        return Admin.builder()
                .id(1)
                .password("p")
                .login("l")
                .build();
    }

}
