package com.musicservice.backend.services;

import com.musicservice.backend.domain.Artist;
import com.musicservice.backend.exceptions.NotFoundException;
import com.musicservice.backend.repositories.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public Artist findArtistById(Long id) {

        return artistRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Artist ID: %s does not exist", id)));
    }

    public Iterable<Artist> findAllArtists() {

        return artistRepository.findAll();
    }

    public Iterable<Artist> findArtistsByName(String name) {

        return artistRepository.findByNameContainingIgnoreCase(name);
    }
}
