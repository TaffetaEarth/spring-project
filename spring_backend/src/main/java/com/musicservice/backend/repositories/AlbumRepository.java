package com.musicservice.backend.repositories;

import com.musicservice.backend.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

    Iterable<Album> findByNameContainingIgnoreCase(String name);
}
