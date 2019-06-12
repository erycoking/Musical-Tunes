package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.models.Artist.Artist;
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
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final Logger log = LoggerFactory.getLogger(ArtistController.class);
    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    Collection<Artist> artists(){
        return artistService.getAllArtist();
    }

    @GetMapping("/{id:[\\d]+}")
    ResponseEntity<?> getArtist(@PathVariable int id) {
        Optional<Artist> artist = artistService.getArtistById(id);
        return artist.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{name:[a-z]+}")
    ResponseEntity<?> getArtist(@PathVariable String name) {
        Artist artist = artistService.getArtist(name);
        if(artist == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(artist);
    }

    @GetMapping("/{name}/artist")
    ResponseEntity<?> getAllArtistByName(@PathVariable String name) {
        List<Artist> artist = artistService.getAllArtistByName(name);
        if(artist == null || artist.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(artist);
    }

    @GetMapping("/{name}/songs")
    ResponseEntity<?> getAllArtistBySong(@PathVariable String name) {
        List<Artist> artist = artistService.getAllArtistBySong(name);
        if(artist == null || artist.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(artist);
    }

    @PostMapping
    ResponseEntity<?> createArtist(@Valid @RequestBody Artist artist) throws URISyntaxException {
        log.info("Request to create artist: {}", artist);
        if (artistService.exist(artist.getArtistName())){
            return ResponseEntity.badRequest().body("Song already Exists");
        }else {
            try {
                Artist result = artistService.save(artist);
                return ResponseEntity.created(new URI("/api/artist/" + result.getArtistId()))
                        .body(result);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateArtist(@PathVariable int id, @Valid @RequestBody Artist artist){
        if (artistService.getArtistById(id) == null){
            return ResponseEntity.notFound().build();
        }else {
            log.info("Request to update artist: {}", artist);
            try {
                Artist result = artistService.save(artist);
                return ResponseEntity.ok().body(result);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteArtist(@PathVariable int id){
        log.info("Request to delete artist: {}", id);
        artistService.deleteArtist(id);
        return ResponseEntity.ok().build();
    }
}
