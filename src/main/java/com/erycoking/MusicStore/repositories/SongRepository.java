package com.erycoking.MusicStore.repositories;

import com.erycoking.MusicStore.models.Album;
import com.erycoking.MusicStore.models.Artist;
import com.erycoking.MusicStore.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    /**
     * find song by name
     * @param name
     * @return a List of songs
     */
    List<Song> findAllBySongName(String name);

    /**
     * find a particular song using its namr
     * @param name
     * @return song
     */
    Song findBySongName(String name);

    /**
     * find by Artist
     * @param artist
     * @return list of songs
     */
    List<Song> findAllByArtist(Artist artist);
    List<Song> findAllByArtist_ArtistId(int artistId);

    /**
     * find by Albums
     * @param album
     * @return list of songs
     */
    List<Song> findAllByAlbum(Album album);
    List<Song> findAllByAlbum_AlbumId(int albumId);

}
