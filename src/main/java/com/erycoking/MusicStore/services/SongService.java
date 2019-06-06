package com.erycoking.MusicStore.services;

import com.erycoking.MusicStore.models.Album;
import com.erycoking.MusicStore.models.Artist;
import com.erycoking.MusicStore.models.Song;
import com.erycoking.MusicStore.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;



    public Optional<Song> getSongById(int artistId){
        return songRepository.findById(artistId);
    }

    public Song getSong(String name){
        return songRepository.findBySongName(name);
    }

    public List<Song> getAllSong(){
        return songRepository.findAll();
    }

    public List<Song> getAllSongByName(String name){
        return songRepository.findAllBySongName(name);
    }

    public List<Song> getAllByArtistName(String artist){
        return songRepository.findAllByArtist_ArtistName(artist);
    }

    public List<Song> getAllByArtistId(int artistId){
        return songRepository.findAllByArtist_ArtistId(artistId);
    }

    public List<Song> getAllByAlbumName(String album){
        return songRepository.findAllByAlbum_AlbumName(album);
    }

    public List<Song> getAllByAlbumId(int albumId){
        return songRepository.findAllByAlbum_AlbumId(albumId);
    }

    public Song save(Song song){
        return songRepository.save(song);
    }

    public List<Song> saveAllSongs(List<Song> songs){
        return songRepository.saveAll(songs);
    }

    public void deleteSong(int id){
        songRepository.deleteById(id);
    }

    public void deleteSong(Song song){
        songRepository.delete(song);
    }
}
