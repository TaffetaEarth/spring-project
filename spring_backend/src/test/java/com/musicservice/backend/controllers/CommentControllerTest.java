package com.musicservice.backend.controllers;

import com.musicservice.backend.domain.Comment;
import com.musicservice.backend.repositories.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class CommentControllerTest {


    private final MockMvc mockMvc;

    private final CommentRepository commentRepository;


    @Autowired
    CommentControllerTest(MockMvc mockMvc, MockMvc mockMvc1, CommentRepository commentRepository) {
        this.mockMvc = mockMvc1;
        this.commentRepository = commentRepository;
    }

    @Test
    void getCommentById() throws Exception {
        Optional<Comment> res = commentRepository.findById(1L);
        assertTrue(res.isPresent());

        Comment comment = res.get();

        String songJson = String.format("{\"id\": %d, \"content\": \"%s\"}",
                comment.getId(), comment.getContent());
        mockMvc.perform(
                        get("/api/comments/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(songJson))
                .andExpect(status().isOk());
    }

    @Test
    void getCommentsBySong() throws Exception {
        List<Comment> res = commentRepository.findAll();

        assertEquals(2, res.size());

        mockMvc.perform(
                        get("/api/comments/song/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}