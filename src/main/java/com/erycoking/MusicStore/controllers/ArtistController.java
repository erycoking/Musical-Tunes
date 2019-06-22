package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.exception.BadRequestException;
import com.erycoking.MusicStore.exception.ResourceNotFoundException;
import com.erycoking.MusicStore.models.Artist.Artist;
import com.erycoking.MusicStore.services.ArtistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(description = "Set of endpoint for creating, reading, updating and deleting songs")
public class ArtistController {

    private final Logger log = LoggerFactory.getLogger(ArtistController.class);
    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    @ApiOperation("Returns a list of all artists in the system")
    Collection<Artist> artists(){
        return artistService.getAllArtist();
    }

    @GetMapping("/{id:[\\d]+}")
    @ApiOperation("Returns the artist with the specified id in the system")
    ResponseEntity<Artist> getArtist(
            @ApiParam(value = "Id of the artist to be obtained", example = "2", required = true)
            @PathVariable int id) {
        Optional<Artist> artist = artistService.getArtistById(id);
        return artist.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
    }

    @GetMapping("/{name:[a-z]+}")
    @ApiOperation("Returns the artist with the specified name in the system")
    ResponseEntity<Artist> getArtist(
            @ApiParam(value = "Name of the artist to be obtained", example = "king", required = true)
            @PathVariable String name) {
        Artist artist = artistService.getArtist(name);
        if(artist == null){
            throw new ResourceNotFoundException("Artist not found");
        }
        return ResponseEntity.ok().body(artist);
    }

    @GetMapping("/{name}/artist")
    @ApiOperation("Returns a list of all artists containing the specified characters in the system")
    ResponseEntity<List<Artist>> getAllArtistByName(
            @ApiParam(value = "characters for filtering artists artist to be obtained", example = "vybz cartel", required = true)
            @PathVariable String name) {
        List<Artist> artist = artistService.getAllArtistByName(name);
        if(artist == null || artist.isEmpty()){
            throw new ResourceNotFoundException("Artist not found");
        }
        return ResponseEntity.ok().body(artist);
    }

    @GetMapping("/{name}/songs")
    @ApiOperation("Returns a list of all artists containing the specified song in the system")
    ResponseEntity<List<Artist>> getAllArtistBySong(
            @ApiParam(value = "Name of the song to be used in fetching the artist", example = "iron", required = true)
            @PathVariable String name) {
        List<Artist> artist = artistService.getAllArtistBySong(name);
        if(artist == null || artist.isEmpty()){
            throw new ResourceNotFoundException("Artist not found");
        }
        return ResponseEntity.ok().body(artist);
    }

    @PostMapping
    @ApiOperation("Adds a new artists in the system")
    ResponseEntity<Artist> createArtist(
            @ApiParam("Object containg artists details")
            @Valid @RequestBody Artist artist) throws URISyntaxException {
        log.info("Request to create artist: {}", artist);
        if (artistService.exist(artist.getArtistName())){
            throw new BadRequestException("Artist already Exists");
        }else {
            try {
                Artist result = artistService.save(artist);
                return ResponseEntity.created(new URI("/api/artist/" + result.getArtistId()))
                        .body(result);
            }catch (Exception e){
                throw new BadRequestException("Artist not found");
            }
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Updates an artist in the system")
    ResponseEntity<Artist> updateArtist(
            @ApiParam(value = "Id of the artist to be updated", example = "12", required = true)
            @PathVariable int id,
            @ApiParam(value = "New name of the artist to be updated", example = "12", required = true)
            @Valid @RequestBody String name){
        Optional<Artist> artist = artistService.getArtistById(id);
        if (!artist.isPresent()){
            throw new BadRequestException("Artist not found");
        }else {
            log.info("Request to update artist: {}", artist.get());
            artist.get().setArtistName(name);
            try {
                Artist result = artistService.save(artist.get());
                return ResponseEntity.ok().body(result);
            }catch (Exception e){
                throw new BadRequestException(e.getMessage());
            }
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletes an artist in the system")
    ResponseEntity<String> deleteArtist(
            @ApiParam(value = "Id of the artist to be deleted", example = "12", required = true)
            @PathVariable int id){
        log.info("Request to delete artist: {}", id);
        artistService.deleteArtist(id);
        return ResponseEntity.ok().body("Artist successfully deleted");
    }
}
