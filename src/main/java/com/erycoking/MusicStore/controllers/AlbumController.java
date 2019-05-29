package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.models.Album;
import com.erycoking.MusicStore.models.Artist;
import com.erycoking.MusicStore.services.AlbumService;
import com.erycoking.MusicStore.services.ArtistService;
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
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final Logger log = LoggerFactory.getLogger(AlbumController.class);
    private AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping
    Collection<Album> albums(){
        return albumService.getAllAlbum();
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getAlbum(@PathVariable int id) {
        Optional<Album> album = albumService.getAlbumById(id);
        return album.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    ResponseEntity<?> createAlbum(@Valid @RequestBody Album album) throws URISyntaxException {
        log.info("Request to create album: {}", album);
        if (albumService.getAlbum(album.getAlbumName()) != null){
            return ResponseEntity.badRequest().body("Song already Exists");
        }else {
            Album result = albumService.save(album);
            return ResponseEntity.created(new URI("/api/album/" + result.getAlbumId()))
                    .body(result);
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<Album> updateAlbum(@PathVariable int id, @Valid @RequestBody Album album){
        if (albumService.getAlbumById(id) == null){
            return ResponseEntity.notFound().build();
        }else {
            log.info("Request to update album: {}", album);
            Album result = albumService.save(album);
            return ResponseEntity.ok().body(result);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteAlbum(@PathVariable int id){
        log.info("Request to delete album: {}", id);
        albumService.deleteAlbum(id);
        return ResponseEntity.ok().build();
    }
}
