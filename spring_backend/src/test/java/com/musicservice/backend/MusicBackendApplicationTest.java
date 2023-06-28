package com.musicservice.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MusicBackendApplicationTest {

    private final MusicBackendApplication app;

    @Autowired
    public MusicBackendApplicationTest(MusicBackendApplication app) {
        this.app = app;
    }


    @Test
    void contextLoads() {
        assertNotNull(app);
    }
}
