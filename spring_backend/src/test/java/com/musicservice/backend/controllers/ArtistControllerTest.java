package com.musicservice.backend.controllers;

import com.musicservice.backend.domain.Artist;
import com.musicservice.backend.repositories.ArtistRepository;
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
class ArtistControllerTest {

    private final MockMvc mockMvc;

    private final ArtistRepository rep;

    @Autowired
    ArtistControllerTest(MockMvc mockMvc, ArtistRepository rep) {
        this.mockMvc = mockMvc;
        this.rep = rep;
    }

    @Test
    void getAllArtists() throws Exception {
        int size = IterableUtil.sizeOf(rep.findAll());

        mockMvc.perform(
                        get("/api/artists/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(size)))
                .andExpect(status().isOk());
    }

    @Test
    void getArtistById() throws Exception {
        Optional<Artist> res = rep.findById(1L);
        assertTrue(res.isPresent());

        Artist artist = res.get();

        String artistJson = String.format("{\"id\": %d, \"name\": \"%s\", \"photo_url\": \"%s\"}",
                artist.getId(), artist.getName(), artist.getPhoto_url());
        mockMvc.perform(
                        get("/api/artists/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(artistJson))
                .andExpect(status().isOk());
    }

    @Test
    void getArtistsByName() throws Exception {
        Optional<Artist> res = rep.findById(1L);
        assertTrue(res.isPresent());
        Artist artist = res.get();

        Iterable<Artist> list = rep.findByNameContainingIgnoreCase(artist.getName());

        artist = list.iterator().next();

        assertEquals(1, IterableUtil.sizeOf(list));

        String artistJson = String.format("[{\"id\": %d, \"name\": \"%s\", \"photo_url\": \"%s\"}]",
                artist.getId(), artist.getName(), artist.getPhoto_url());
        mockMvc.perform(
                        get(String.format("/api/artists/name/%s", artist.getName()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(artistJson))
                .andExpect(status().isOk());
    }
}