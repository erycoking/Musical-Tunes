package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.models.Artist;
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

    @GetMapping("/{id}")
    ResponseEntity<?> getArtist(@PathVariable int id) {
        Optional<Artist> group = artistService.getArtistById(id);
        return group.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    ResponseEntity<Artist> createArtist(@Valid @RequestBody Artist group) throws URISyntaxException {
        log.info("Request to create group: {}", group);
        Artist result = artistService.save(group);
        return ResponseEntity.created(new URI("/api/group/" + result.getArtistId()))
                .body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<Artist> updateArtist(@PathVariable int id, @Valid @RequestBody Artist group){
        if (artistService.getArtistById(id) == null){
            return ResponseEntity.notFound().build();
        }else {
            log.info("Request to update group: {}", group);
            Artist result = artistService.save(group);
            return ResponseEntity.ok().body(result);
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteArtist(@PathVariable int id){
        log.info("Request to delete group: {}", id);
        artistService.deleteArtist(id);
        return ResponseEntity.ok().build();
    }
}
