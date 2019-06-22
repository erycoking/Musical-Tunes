package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.exception.BadRequestException;
import com.erycoking.MusicStore.exception.ResourceNotFoundException;
import com.erycoking.MusicStore.models.Playlist.PlayList;
import com.erycoking.MusicStore.models.Song.Song;
import com.erycoking.MusicStore.services.PlayListService;
import com.erycoking.MusicStore.services.SongService;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/playlists")
@Api(description = "Set of endpoints for creating, reading, updating and deleting playlists")
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
    @ApiOperation("Return a lists of all playlists in the system")
    Collection<PlayList> playlists(){
        return playListService.getAllPlayList();
    }

    @GetMapping("/{id:[\\d]+}")
    @ApiOperation("Returns a playlist with the specified id")
    ResponseEntity<PlayList> getPlayList(
            @ApiParam(value = "Id of the playlist to be obtained", example = "2", required = true)
            @PathVariable int id) {
        Optional<PlayList> playList = playListService.getPlayListId(id);
        return playList.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() ->  new ResourceNotFoundException("PlayList not found"));
    }

    @GetMapping("/{name:[a-z]+}")
    @ApiOperation("Returns a playlist with the specified name")
    ResponseEntity<PlayList> getPlayListByName(
            @ApiParam(value = "Name of the playlist to be obtained", example = "locals", required = true)
            @PathVariable String name) {
        PlayList playList = playListService.getPlayList(name);
        if (playList == null){
            throw new ResourceNotFoundException("PlayList not found");
        }
        return ResponseEntity.ok().body(playList);
    }

    @GetMapping("/{name}/playList")
    @ApiOperation("Returns a list of playlist with the specified characters")
    ResponseEntity<List<PlayList>> getAllPlayListByName(
            @ApiParam(value = "Characters used to filter the playlist to be obtained", example = "locals", required = true)
            @PathVariable String name) {
        List<PlayList> playList = playListService.getAllPlayListByName(name);
        if (playList == null || playList.isEmpty()){
            throw new ResourceNotFoundException("PlayList not found");
        }
        return ResponseEntity.ok().body(playList);
    }

    @PostMapping
    @ApiOperation("Adds a playlist to the system and returns the new playlist")
    ResponseEntity<PlayList> createPlayList(
            @ApiParam(value = "Name of the playlist to be added", example = "locals", required = true)
            @RequestParam("name") String playlist) throws URISyntaxException {
        log.info("Request to create playList: {}", playlist);
        if (playListService.getPlayList(playlist) != null){
            throw new BadRequestException("An error occurred while adding the playList, kindly check that the inputs are correct");
        }else {
            try {
                PlayList result = playListService.save(new PlayList(playlist));
                return ResponseEntity.created(new URI("/api/playList/" + result.getPlayListId()))
                        .body(result);
            }catch (Exception e){
                throw new BadRequestException(e.getMessage());
            }
        }
    }

    @PostMapping("/{playListName:[a-z]+}/songs")
    @ApiOperation("Adds songs to a playlist in the system and returns the playlist")
    ResponseEntity<PlayList> addsongsToPlayList(
            @ApiParam(value = "Name of the playlists to add songs", example = "locals", required = true)
            @PathVariable("playListName") String playListName,
            @ApiParam(value = "List of the songs to be added", example = "locals", required = true)
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
                    throw new BadRequestException(ex.getMessage());
                }catch (Exception ex){
                    throw new BadRequestException(ex.getMessage());
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
                throw new BadRequestException(e.getMessage());
            }

        }else {
            throw new ResourceNotFoundException("PlayList does not exists");
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Remove a song from playlist in the system")
    ResponseEntity<PlayList> updatePlayList(
            @ApiParam(value = "Id of the playlist to be updated", example = "12", required = true)
            @PathVariable int id,
            @ApiParam(value = "name of the song to be removed", example = "12", required = true)
            @Valid @RequestBody String name){

        Optional<PlayList> list = playListService.getPlayListId(id);
        if (!list.isPresent()){
            throw new ResourceNotFoundException("PlayList does not exists");
        }else {
            log.info("Request to update playList: {}", list.get());
            try {
                PlayList playList = list.get();
                List list1 =  playList.getSongs();
                list1.remove(songService.getSong(name));
                playList.setSongs(list1);
                PlayList result = playListService.save(playList);
                return ResponseEntity.ok().body(result);
            }catch (Exception e){
                throw new BadRequestException(e.getMessage());
            }
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletes a playlist in the system")
    ResponseEntity<?> deletePlayList(
            @ApiParam(value = "Id of the playlist to be deleted", example = "12", required = true)
            @PathVariable int id){
        log.info("Request to delete playList: {}", id);
        playListService.deletePlayList(id);
        return ResponseEntity.ok().body("Playlist successfully deleted");
    }
}
