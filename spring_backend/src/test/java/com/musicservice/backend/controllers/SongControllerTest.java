package com.musicservice.backend.controllers;

import com.musicservice.backend.domain.Song;
import com.musicservice.backend.repositories.SongRepository;
import org.assertj.core.util.IterableUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class SongControllerTest {

    private final MockMvc mockMvc;

    private final SongRepository rep;

    @Autowired
    SongControllerTest(MockMvc mockMvc, SongRepository rep) {
        this.mockMvc = mockMvc;
        this.rep = rep;
    }


    @Test
    void getAllSongs() throws Exception {
        int size = IterableUtil.sizeOf(rep.findAll());

        mockMvc.perform(
                        get("/api/songs/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(size)))
                .andExpect(status().isOk());
    }

    @Test
    void getSongById() throws Exception {
        Optional<Song> res = rep.findById(1L);
        assertTrue(res.isPresent());

        Song song = res.get();

        String songJson = String.format("{\"id\": %d, \"name\": \"%s\", \"link\": \"%s\"}",
                song.getId(), song.getName(), song.getLink());
        mockMvc.perform(
                        get("/api/songs/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(songJson))
                .andExpect(status().isOk());
    }

    @Test
    void getSongsByName() throws Exception {
        Optional<Song> res = rep.findById(1L);
        assertTrue(res.isPresent());

        Song song = res.get();

        Iterable<Song> list = rep.findByNameContainingIgnoreCase(song.getName());

        song = list.iterator().next();

        assertEquals(1, IterableUtil.sizeOf(list));

        String songJson = String.format("[{\"id\": %d, \"name\": \"%s\", \"link\": \"%s\"}]",
                song.getId(), song.getName(), song.getLink());
        mockMvc.perform(
                        get(String.format("/api/songs/name/%s", song.getName()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(songJson))
                .andExpect(status().isOk());
    }
}