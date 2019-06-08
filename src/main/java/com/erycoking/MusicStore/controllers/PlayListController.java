package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.models.PlayList;
import com.erycoking.MusicStore.models.Song;
import com.erycoking.MusicStore.services.PlayListService;
import com.erycoking.MusicStore.services.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/playlists")
public class PlayListController {

    private final Logger log = LoggerFactory.getLogger(PlayListController.class);
    private PlayListService playListService;
    private SongService songService;

    @Autowired
    public PlayListController(PlayListService playListService, SongService songService) {
        this.playListService = playListService;
        this.songService = songService;
    }

    @GetMapping
    Collection<PlayList> playlists(){
        return playListService.getAllPlayList();
    }

    @GetMapping("/{id:[\\d]+}")
    ResponseEntity<?> getPlayList(@PathVariable int id) {
        Optional<PlayList> playList = playListService.getPlayListId(id);
        return playList.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{name:[a-z]+}")
    ResponseEntity<?> getPlayListByName(@PathVariable String name) {
        PlayList playList = playListService.getPlayList(name);
        if (playList == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playList);
    }

    @GetMapping("/{name}/playList")
    ResponseEntity<?> getAllPlayListByName(@PathVariable String name) {
        List<PlayList> playList = playListService.getAllPlayListByName(name);
        if (playList == null || playList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(playList);
    }

    @PostMapping
    ResponseEntity<?> createPlayList(@RequestParam("name") String playlist) throws URISyntaxException {
        log.info("Request to create playList: {}", playlist);
        if (playListService.getPlayList(playlist) != null){
            return ResponseEntity.badRequest().body("Song already Exists");
        }else {
            try {
                PlayList result = playListService.save(new PlayList(playlist));
                return ResponseEntity.created(new URI("/api/playList/" + result.getPlayListId()))
                        .body(result);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

    @PostMapping("/{playListName:[a-z]+}/songs")
    ResponseEntity<?> addsongsToPlayList(@PathVariable("playListName") String playListName,
                                      @RequestParam("songs") String[] songs) throws URISyntaxException {
        log.info("Request to add songs to playList: {}", songs);

        PlayList playList;
        if ((playList = playListService.getPlayList(playListName)) != null){
            ArrayList<Song> songsToBeAdded = new ArrayList<>();
            for (String id: songs){
                Song song;
                try {
                    Optional<Song> optionalSong = songService.getSongById(Integer.valueOf(id));
                    if(optionalSong.isPresent()){
                        songsToBeAdded.add(optionalSong.get());
                    }
                }catch (NumberFormatException ex){
                    return ResponseEntity.badRequest().body("Array of integers expected");
                }catch (Exception ex){
                    return ResponseEntity.badRequest().body("Something went wrong, try again later");
                }

            }

            try {
                List<Song> oldPlayList = playList.getSongs();
                songsToBeAdded.addAll(oldPlayList);
                playList.setSongs(songsToBeAdded);
                PlayList result = playListService.save(playList);
                return ResponseEntity.created(new URI("/api/playList/" + result.getPlayListId()))
                        .body(result);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        }else {
            return ResponseEntity.badRequest().body("PlayList does not exists");
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updatePlayList(@PathVariable int id, @Valid @RequestBody PlayList playList){
        if (playListService.getPlayListId(id) == null){
            return ResponseEntity.notFound().build();
        }else {
            log.info("Request to update playList: {}", playList);
            try {
                PlayList result = playListService.save(playList);
                return ResponseEntity.ok().body(result);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePlayList(@PathVariable int id){
        log.info("Request to delete playList: {}", id);
        playListService.deletePlayList(id);
        return ResponseEntity.ok().build();
    }
}
