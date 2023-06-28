package com.musicservice.backend.controllers;

import com.musicservice.backend.domain.Album;
import com.musicservice.backend.repositories.AlbumRepository;
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
public class AlbumControllerTest {


    private final MockMvc mockMvc;

    private final AlbumRepository rep;

    @Autowired
    public AlbumControllerTest(MockMvc mockMvc, AlbumRepository rep) {
        this.mockMvc = mockMvc;
        this.rep = rep;
    }

    @Test
    void getAllAlbumsTest() throws Exception {
        int size = IterableUtil.sizeOf(rep.findAll());

        mockMvc.perform(
                        get("/api/albums/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(size)))
                .andExpect(status().isOk());
    }

    @Test
    void getAlbumByIdTest() throws Exception {
        Optional<Album> res = rep.findById(1L);
        assertTrue(res.isPresent());

        Album album = res.get();

        String albumJson = String.format("{\"id\": %d, \"album_art_url\": \"%s\", \"name\": \"%s\"}",
                album.getId(), album.getAlbum_art_url(), album.getName());
        mockMvc.perform(
                        get("/api/albums/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(albumJson))
                .andExpect(status().isOk());
    }

    @Test
    void getAlbumByNameTest() throws Exception {
        Optional<Album> res = rep.findById(1L);
        assertTrue(res.isPresent());
        Album album = res.get();

        Iterable<Album> list = rep.findByNameContainingIgnoreCase(album.getName());

        album = list.iterator().next();

        assertEquals(1, IterableUtil.sizeOf(list));

        String albumJson = String.format("[{\"id\": %d, \"album_art_url\": \"%s\", \"name\": \"%s\"}]",
                album.getId(), album.getAlbum_art_url(), album.getName());
        mockMvc.perform(
                        get(String.format("/api/albums/name/%s", album.getName()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(albumJson))
                .andExpect(status().isOk());
    }
}
