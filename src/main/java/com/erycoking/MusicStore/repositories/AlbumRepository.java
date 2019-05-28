package com.erycoking.MusicStore.repositories;

import com.erycoking.MusicStore.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AlbumRepository extends JpaRepository<Album, Integer> {

    /**
     * find album by name
     * @param name
     * @return a List of albums
     */
    List<Album> findAllByAlbumName(String name);
    Album findByAlbumName(String name);

    /**
     * find a particular album
     * @param name
     * @return album
     */
    List<Album> findBySongsContaining(String name);
}