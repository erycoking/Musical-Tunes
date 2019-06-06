package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.models.Song;
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
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/songs")
public class SongController {

    private final Logger log = LoggerFactory.getLogger(SongController.class);
    private SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    Collection<Song> songs(){
        return songService.getAllSong();
    }

    @GetMapping("/{id:[\\d]+}")
    ResponseEntity<?> getSong(@PathVariable int id) {
        Optional<Song> song = songService.getSongById(id);
        return song.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{name:[a-z]+}")
    ResponseEntity<?> getSong(@PathVariable String name) {
        Song song = songService.getSong(name);
        if(song == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(song);
    }

    @GetMapping("/{name}/songs")
    ResponseEntity<?> getSongBySongName(@PathVariable String name) {
        List<Song> song = songService.getAllSongByName(name);
        if(song == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(song);
    }

    @GetMapping("/{id:[\\d]+}/artist")
    ResponseEntity<?> getSongByArtistId(@PathVariable int id) {
        List<Song> song = songService.getAllByArtistId(id);
        if(song == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(song);
    }

    @GetMapping("/{name:[a-z]+}/artist")
    ResponseEntity<?> getSongByArtist(@PathVariable String name) {
        List<Song> song = songService.getAllByArtistName(name);
        if(song == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(song);
    }

    @GetMapping("/{id:[\\d]+}/album")
    ResponseEntity<?> getSongByAlbumId(@PathVariable int id) {
        List<Song> song = songService.getAllByAlbumId(id);
        if(song == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(song);
    }

    @GetMapping("/{name:[a-z]+}/album")
    ResponseEntity<?> getSongByAlbum(@PathVariable String name) {
        List<Song> song = songService.getAllByAlbumName(name);
        if(song == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(song);
    }

    @PostMapping
    ResponseEntity<?> createSong(@Valid @RequestBody Song song) throws URISyntaxException {
        log.info("Request to create song: {}", song);
        if(songService.getSong(song.getSongName()) != null){
            return ResponseEntity.badRequest().body("Song already Exists");
        }else {
            Song result = songService.save(song);
            return ResponseEntity.created(new URI("/api/song/" + result.getSongId()))
                    .body(result);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateSong(@PathVariable int id, @Valid @RequestBody Song song){
        if (songService.getSongById(id) == null){
            return ResponseEntity.notFound().build();
        }else{
            log.info("Request to update song: {}", song);
            Song result = songService.save(song);
            return ResponseEntity.ok().body(result);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSong(@PathVariable int id){
        log.info("Request to delete song: {}", id);
        songService.deleteSong(id);
        return ResponseEntity.ok().build();
    }
}
