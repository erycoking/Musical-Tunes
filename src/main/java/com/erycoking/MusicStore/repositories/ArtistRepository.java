package com.erycoking.MusicStore.repositories;

import com.erycoking.MusicStore.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    @Query("SELECT p FROM Artist p LEFT JOIN FETCH p.songs")
    List<Artist> findAll();

    @Query("SELECT p FROM Artist p LEFT JOIN FETCH p.songs WHERE p.artistId=:id")
    Optional<Artist> findById(@Param("id") int id);

    /**
     * find artist by name
     * @param name
     * @return a List of artists
     */
    @Query("SELECT p FROM Artist p LEFT JOIN FETCH p.songs WHERE p.artistName LIKE %:name%")
    List<Artist> findAllByArtistName(@Param("name") String name);

    @Query("SELECT p FROM Artist p LEFT JOIN FETCH p.songs WHERE p.artistName=:name")
    Artist findByArtistName(String name);

    /**
     * find a particular artist
     * @param name
     * @return artist
     */
    @Query("SELECT p FROM Artist p LEFT JOIN FETCH p.songs s WHERE s.songName LIKE %:name%")
    List<Artist> findBySongsContaining(String name);


}
