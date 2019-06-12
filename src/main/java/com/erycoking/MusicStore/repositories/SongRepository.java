package com.erycoking.MusicStore.repositories;

import com.erycoking.MusicStore.models.Song.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    @Query("SELECT p FROM Song p LEFT JOIN FETCH p.artist")
    List<Song> findAll();

    @Query(value = "select s from Song s join fetch s.artist where s.songId = :id")
    Optional<Song> findById(@Param("id") int id);

    /**
     * find song by name
     * @param name
     * @return a List of songs
     */
    @Query(value = "select s from Song s join fetch s.artist where s.songName LIKE %:name%")
    List<Song> findAllBySongName(String name);

    /**
     * find a particular song using its name
     * @param name
     * @return song
     */
    @Query(value = "select s from Song s join fetch s.artist where s.songName = :name")
    Song findBySongName(String name);

    /**
     * find by Artist
     * @param name
     * @return list of songs
     */
    @Query(value = "select s from Song s join fetch s.artist where s.artist.artistName LIKE %:name%")
    List<Song> findAllByArtist_ArtistName(String name);

    @Query(value = "select s from Song s join fetch s.artist a where a.artistId = :artistId")
    List<Song> findAllByArtist_ArtistId(int artistId);

    boolean existsBySongName(String name);
}
