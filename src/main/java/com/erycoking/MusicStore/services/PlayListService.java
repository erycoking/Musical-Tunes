package com.erycoking.MusicStore.services;

import com.erycoking.MusicStore.models.PlayList;
import com.erycoking.MusicStore.repositories.PlayListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlayListService {

    @Autowired
    private PlayListRepository playListRepository;

    public Optional<PlayList> getPlayListId(int albumId){
        return playListRepository.findById(albumId);
    }


    public PlayList getPlayList(String name){
        return playListRepository.findByPlayListName(name);
    }

    public Set<PlayList> getAllPlayList(){
        Set<PlayList> playListSet = new HashSet<>();
        playListSet.addAll(playListRepository.findAll());
        return playListSet;
    }

    public List<PlayList> getAllPlayListByName(String name){
        return playListRepository.findBySongsContaining(name);
    }

    public PlayList save(PlayList song){
        return playListRepository.save(song);
    }

    public List<PlayList> saveAllPlayList(List<PlayList> songs){
        return playListRepository.saveAll(songs);
    }

    public void deletePlayList(int id){
        playListRepository.deleteById(id);
    }

    public void deletePlayList(PlayList song){
        playListRepository.delete(song);
    }

    public void deleteAllPlayList(){
        playListRepository.deleteAll();
    }
}
