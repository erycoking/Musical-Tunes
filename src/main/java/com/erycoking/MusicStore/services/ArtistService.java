package com.erycoking.MusicStore.services;

import com.erycoking.MusicStore.models.Artist.Artist;
import com.erycoking.MusicStore.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public Optional<Artist> getArtistById(int artistId){
        return artistRepository.findById(artistId);
    }

    public boolean exist(String name) {
        return artistRepository.existsByArtistName(name);
    }

    public  Artist getArtist(String name){
        return artistRepository.findByArtistName(name);
    }

    public List<Artist> getAllArtist(){
        return artistRepository.findAll();
    }

    public List<Artist> getAllArtistBySong(String name){
        return artistRepository.findBySongsContaining(name);
    }

    public List<Artist> getAllArtistByName(String name){
        return artistRepository.findAllByArtistName(name);
    }

    public Artist save(Artist song){
        return artistRepository.save(song);
    }

    public List<Artist> saveAllArtists(List<Artist> songs){
        return artistRepository.saveAll(songs);
    }

    public void deleteArtist(int id){
        artistRepository.deleteById(id);
    }

    public void deleteArtist(Artist song){
        artistRepository.delete(song);
    }
}
