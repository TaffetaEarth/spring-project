package com.musicservice.backend.services;

import com.musicservice.backend.domain.Genre;
import com.musicservice.backend.exceptions.NotFoundException;
import com.musicservice.backend.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public Genre findGenreById(Long id) {

        return genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Genre ID: %s does not exist", id)));
    }

    public Iterable<Genre> findAllGenres() {

        return genreRepository.findAll();
    }

    public Iterable<Genre> findGenresByName(String name) {

        return genreRepository.findByNameContainingIgnoreCase(name);
    }
}
