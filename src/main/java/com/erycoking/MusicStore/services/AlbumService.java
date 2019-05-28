package com.erycoking.MusicStore.services;

import com.erycoking.MusicStore.models.Album;
import com.erycoking.MusicStore.models.Artist;
import com.erycoking.MusicStore.models.Song;
import com.erycoking.MusicStore.repositories.AlbumRepository;
import com.erycoking.MusicStore.repositories.ArtistRepository;
import com.erycoking.MusicStore.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public Optional<Album> getAlbumById(int albumId){
        return albumRepository.findById(albumId);
    }

    public  Album getAlbum(String name){
        return albumRepository.findByAlbumName(name);
    }

    public List<Album> getAllAlbum(){
        return albumRepository.findAll();
    }

    public List<Album> getAllAlbumByName(String name){
        return albumRepository.findBySongsContaining(name);
    }

    public Album save(Album song){
        return albumRepository.save(song);
    }

    public List<Album> saveAllAlbums(List<Album> songs){
        return albumRepository.saveAll(songs);
    }

    public void deleteAlbum(int id){
        albumRepository.deleteById(id);
    }

    public void deleteAlbum(Album song){
        albumRepository.delete(song);
    }
}
