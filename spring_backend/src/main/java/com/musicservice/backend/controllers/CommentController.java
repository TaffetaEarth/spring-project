package com.musicservice.backend.controllers;

import com.musicservice.backend.domain.Comment;
import com.musicservice.backend.domain.User;
import com.musicservice.backend.payload.requests.CommentContentRequest;
import com.musicservice.backend.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("api/comments")
@CrossOrigin
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {

        Comment comment = commentService.findCommentById(id);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/song/{id}")
    public Iterable<Comment> getCommentsBySong(@PathVariable Long id) {

        return commentService.findCommentsBySongId(id);
    }

    @PostMapping("/song/{id}")
    public ResponseEntity<?> createCommentOnSong(@PathVariable Long id, @Valid @RequestBody CommentContentRequest commentContentRequest,
                                                 Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Comment comment = commentService.saveComment(id, commentContentRequest.getContent(), user);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCommentById(@PathVariable Long id, @Valid @RequestBody CommentContentRequest commentContentRequest,
                                               Authentication authentication) {


        User user = (User) authentication.getPrincipal();
        Comment comment = commentService.updateComment(id, commentContentRequest.getContent(), user);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        commentService.removeComment(id, user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
