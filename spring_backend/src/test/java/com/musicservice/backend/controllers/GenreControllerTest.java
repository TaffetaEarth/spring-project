package com.musicservice.backend.controllers;

import com.musicservice.backend.domain.Genre;
import com.musicservice.backend.repositories.GenreRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class GenreControllerTest {

    private final MockMvc mockMvc;

    private final GenreRepository rep;

    @Autowired
    GenreControllerTest(MockMvc mockMvc, GenreRepository rep) {
        this.mockMvc = mockMvc;
        this.rep = rep;
    }

    @Test
    void getAllGenres() throws Exception {
        int size = IterableUtil.sizeOf(rep.findAll());

        mockMvc.perform(
                        get("/api/genres/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(size)))
                .andExpect(status().isOk());
    }

    @Test
    void getGenreById() throws Exception {
        Optional<Genre> res = rep.findById(1L);
        assertTrue(res.isPresent());

        Genre genre = res.get();

        String genreJson = String.format("{\"id\": %d, \"name\": \"%s\", \"photo_url\": \"%s\"}",
                genre.getId(), genre.getName(), genre.getPhoto_url());
        mockMvc.perform(
                        get("/api/genres/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(genreJson))
                .andExpect(status().isOk());
    }

    @Test
    void getGenresByName() throws Exception {
        Optional<Genre> res = rep.findById(1L);
        assertTrue(res.isPresent());
        Genre genre = res.get();

        Iterable<Genre> list = rep.findByNameContainingIgnoreCase(genre.getName());

        genre = list.iterator().next();

        assertEquals(1, IterableUtil.sizeOf(list));

        String artistJson = String.format("[{\"id\": %d, \"name\": \"%s\", \"photo_url\": \"%s\"}]",
                genre.getId(), genre.getName(), genre.getPhoto_url());
        mockMvc.perform(
                        get(String.format("/api/genres/name/%s", genre.getName()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(artistJson))
                .andExpect(status().isOk());
    }
}