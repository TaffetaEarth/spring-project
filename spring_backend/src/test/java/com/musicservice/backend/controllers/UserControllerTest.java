package com.musicservice.backend.controllers;

import com.musicservice.backend.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.validation.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
public class UserControllerTest {
    private final UserController controller;

    private BindingResult result;
    private User user;

    @Autowired
    public UserControllerTest(UserController controller) {
        this.controller = controller;
    }

    @BeforeEach
    void setUp() {
        result = mock(BindingResult.class);

        user = new User();
        user.setPassword("string");
        user.setId(1L);
        user.setFullName("string");
        user.setUsername("string");
    }

    @AfterEach
    void tearDown() {
        result = null;
    }

    @Test
    void registerUser() {
        ResponseEntity<?> response = controller.registerUser(user, result);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
