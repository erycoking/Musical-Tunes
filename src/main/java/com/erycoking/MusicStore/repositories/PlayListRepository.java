package com.erycoking.MusicStore.repositories;

import com.erycoking.MusicStore.models.Playlist.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayListRepository extends JpaRepository<PlayList, Integer> {


    @Query("SELECT p FROM PlayList p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH  s.artist")
    List<PlayList> findAll();

    /**
     * find playList by name
     * @param name
     * @return a List of albums
     */
    @Query("SELECT p FROM PlayList p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH  s.artist WHERE p.playListName LIKE %:name%")
    List<PlayList> findAllByPlayListName(@Param("name") String name);

    @Query("SELECT p FROM PlayList p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH  s.artist WHERE p.playListId=:id")
    Optional<PlayList> findById(@Param("id") int id);

    @Query("SELECT p FROM PlayList p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH  s.artist WHERE p.playListName LIKE %:name%")
    PlayList findByPlayListName(String name);

    /**
     * find a particular playList
     * @param name
     * @return playList
     */
    @Query("SELECT p FROM PlayList p LEFT JOIN FETCH p.songs s LEFT JOIN FETCH  s.artist WHERE s.songName LIKE %:name%")
    List<PlayList> findBySongsContaining(@Param("name") String name);

    boolean existsByPlayListName(String name);
}