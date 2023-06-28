package com.musicservice.backend.controllers;

import com.musicservice.backend.domain.Song;
import com.musicservice.backend.domain.User;
import com.musicservice.backend.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/likes")
@CrossOrigin
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/songs")
    public Iterable<Song> getAllLikedSongs(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        return likeService.findAllLikedSongsByUser(user);
    }

    @PutMapping("/songs/{id}")
    public ResponseEntity<?> toggleLikedSongById(@PathVariable Long id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Song song = likeService.toggleLikedSong(id, user);
        return new ResponseEntity<>(song, HttpStatus.OK);
    }
}
