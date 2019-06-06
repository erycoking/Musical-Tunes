package com.erycoking.MusicStore.repositories;

import com.erycoking.MusicStore.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer> {


    @Query("SELECT p FROM Album p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH  s.artist")
    List<Album> findAll();

    /**
     * find album by name
     * @param name
     * @return a List of albums
     */
    @Query("SELECT p FROM Album p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH  s.artist WHERE p.albumName LIKE %:name%")
    List<Album> findAllByAlbumName(@Param("name") String name);

    @Query("SELECT p FROM Album p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH  s.artist WHERE p.albumId=:id")
    Optional<Album> findById(@Param("id") int id);

    @Query("SELECT p FROM Album p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH  s.artist WHERE p.albumName LIKE %:name%")
    Album findByAlbumName(String name);

    /**
     * find a particular album
     * @param name
     * @return album
     */
    @Query("SELECT p FROM Album p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH  s.artist WHERE s.songName LIKE %:name%")
    List<Album> findBySongsContaining(@Param("name") String name);
}