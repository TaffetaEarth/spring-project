package com.musicservice.backend.repositories;

import com.musicservice.backend.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Iterable<Artist> findByNameContainingIgnoreCase(String name);
}
