package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.exception.BadRequestException;
import com.erycoking.MusicStore.exception.ResourceNotFoundException;
import com.erycoking.MusicStore.models.Artist.Artist;
import com.erycoking.MusicStore.models.Playlist.PlayList;
import com.erycoking.MusicStore.models.Song.Song;
import com.erycoking.MusicStore.services.ArtistService;
import com.erycoking.MusicStore.services.PlayListService;
import com.erycoking.MusicStore.services.SongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/songs")
@Api(description = "Set of endpoints for creating, editing, updating and deleting songs")
public class SongController {

    private final Logger log = LoggerFactory.getLogger(SongController.class);
    private SongService songService;
    private ArtistService artistService;
    private PlayListService playListService;
    private ServletContext context;

    @Autowired
    public SongController(SongService songService, ArtistService artistService, PlayListService playListService, ServletContext servletContext) {
        this.songService = songService;
        this.artistService = artistService;
        this.playListService = playListService;
        this.context = servletContext;
    }

    @GetMapping
    @ApiOperation("Return a list of all songs in the system")
    Collection<Song> songs(){
        return songService.getAllSong();
    }

    @GetMapping("/{id:[\\d]+}")
    @ApiOperation("Return a song using the specified id")
    ResponseEntity<Song> getSong(
            @ApiParam(value = "Id of the song to be obtained", example ="10", required = true)
            @PathVariable int id) {
        Optional<Song> song = songService.getSongById(id);
        return song.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
    }

    @GetMapping("/{name:[a-z]+}")
    @ApiOperation("Return a song using the specified name")
    ResponseEntity<Song> getSong(
            @ApiParam(value = "Name of the song to be obtained", example ="Love is real", required = true)
            @PathVariable String name) {
        Song song = songService.getSong(name);
        if(song == null){
            throw new ResourceNotFoundException("Song not found");
        }
        return ResponseEntity.ok().body(song);
    }

    @GetMapping("/{name}/songs")
    @ApiOperation("Return a list of all songs containing the specified characters in the system")
    ResponseEntity<List<Song>> getSongBySongName(
            @ApiParam(value = "character to be used for filtering", example ="lov", required = true)
            @PathVariable String name) {
        List<Song> song = songService.getAllSongByName(name);
        if(song == null){
            throw new ResourceNotFoundException("Song not found");
        }
        return ResponseEntity.ok().body(song);
    }

    @GetMapping("/{id:[\\d]+}/artist")
    @ApiOperation("Return a list of all songs belonging to the artist with the specified id in the system")
    ResponseEntity<List<Song>> getSongByArtistId(
            @ApiParam(value = "Id of the artist whose songs are to be obtained", example ="10", required = true)
            @PathVariable int id) {
        List<Song> song = songService.getAllByArtistId(id);
        if(song == null){
            throw new ResourceNotFoundException("Song not found");
        }
        return ResponseEntity.ok().body(song);
    }

    @GetMapping("/{name:[a-z]+}/artist")
    @ApiOperation("Return a list of all songs belonging to the artist with the specified characters in the artist name in the system")
    ResponseEntity<List<Song>> getSongByArtist(
            @ApiParam(value = "Name of the artist whose songs are to be obtained", example ="vybz", required = true)
            @PathVariable String name) {
        List<Song> song = songService.getAllByArtistName(name);
        if(song == null){
            throw new ResourceNotFoundException("Song not found");
        }
        return ResponseEntity.ok().body(song);
    }

    @GetMapping("/{id:[\\d]+}/playlist")
    @ApiOperation("Return a list of all songs belonging to the playlist with the specified id in the system")
    ResponseEntity<List<Song>> getSongByPlayListId(
            @ApiParam(value = "Id of the playlist whose songs are to be obtained", example ="10", required = true)
            @PathVariable int id) {
        Optional<PlayList> optionalPlayList = playListService.getPlayListId(id);
        if (optionalPlayList.isPresent()){
            List<Song> song = optionalPlayList.get().getSongs();
            if(song == null || song.isEmpty()) {
                throw new ResourceNotFoundException("Song not found");
            }
            return ResponseEntity.ok().body(song);
        }
        throw new ResourceNotFoundException("Song not found");

    }

    @GetMapping("/{name:[a-z]+}/playlist")
    @ApiOperation("Return a list of all songs belonging to the playlist with the specified characters in the system")
    ResponseEntity<List<Song>> getSongByAlbum(
            @ApiParam(value = "name of the playlist whose songs are to be obtained", example ="kings", required = true)
            @PathVariable String name) {
        PlayList playList = playListService.getPlayList(name);
        if (playList != null){
            List<Song> allSongs = playList.getSongs();
            if(allSongs == null || allSongs.isEmpty()){
                throw new ResourceNotFoundException("Song not found");
            }
            return ResponseEntity.ok().body(allSongs);
        }
        throw new ResourceNotFoundException("Song not found");
    }

    @PostMapping(headers=("content-type=multipart/*"))
    @ApiOperation("Adds a new song to the system")
    ResponseEntity<Song> createSong(
            @ApiParam(value = "Audio file of the song to be added", example ="Alivyo nipendaga.mp3", required = true)
            @RequestParam("song") MultipartFile inputFile,
            @ApiParam(value = "Type of the song to be added", example ="RNB", required = true)
            @RequestParam("type") String type,
            @ApiParam(value = "Artist Name", example ="diamond", required = true)
            @RequestParam("artist") String artist) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();

        if (!inputFile.isEmpty()) {
            try {
                String songName = songService.storeFile(inputFile);

                if(songService.getSong(songName) == null){
                    String fileDownLoadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/songs/downloads/")
                            .path(songName)
                            .toUriString();

                    Song result;
                    Artist artist1;
                    if ((artist1 = artistService.getArtist(artist)) != null) {
                        result = songService.save(new Song(
                                songName, fileDownLoadUri, inputFile.getContentType(),
                                inputFile.getSize(), type, artist1
                        ));
                    }else {
                        result = songService.save(new Song(
                                songName, fileDownLoadUri, inputFile.getContentType(),
                                inputFile.getSize(), type, new Artist(artist)
                        ));
                    }

                    log.info("File Uploaded: {}", songName);
                    headers.add("File Uploaded Successfully - ", inputFile.getOriginalFilename());

                    return ResponseEntity.created(new URI("/api/song/" + result.getSongId()))
                            .headers(headers)
                            .body(result);
                }else {
                    throw new BadRequestException("Song already Exists");
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new BadRequestException(e.getMessage());
            }
        }else {
            throw new BadRequestException("Song is required");
        }
    }

    @PutMapping("/{id}")
    @ApiOperation("Updates a song name in the system")
    ResponseEntity<Song> updateSong(
            @ApiParam(value = "Id of the song to be updated", example = "12", required = true)
            @PathVariable int id,
            @ApiParam(value = "Id of the song to be deleted", example = "12", required = true)
            @Valid @RequestBody String name){

        Optional<Song> song = songService.getSongById(id);
        if ( !song.isPresent()){
            throw new ResourceNotFoundException("Song not found");
        }else{
            log.info("Request to update song: {}", song);
            try {
                song.get().setSongName(name);
                Song result = songService.save(song.get());
                return ResponseEntity.ok().body(result);
            }catch (Exception e){
                throw new BadRequestException(e.getMessage());
            }
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletes a song in the system")
    ResponseEntity<String> deleteSong(
            @ApiParam(value = "Id of the song to be deleted", example = "12", required = true)
            @PathVariable int id){
        log.info("Request to delete song: {}", id);
        songService.deleteSong(id);
        return ResponseEntity.ok().body("Song successfully deleted");
    }

    @GetMapping("/downloads/{fileName:.+}")
    @ApiOperation("Return a the specified file from the system")
    ResponseEntity<Resource> downloadFile(
            @ApiParam(value = "Path of the file",example = "F://uploads/Boasty.mp3", required = true)
            @PathVariable("fileName") String fileName, HttpServletRequest request){
//        Load file as Resource
        Resource resource = songService.loadFileAsResource(fileName);

//        try to determine files content-type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }catch (IOException ex){
            log.info("Could not determine file type.");
        }

//        fallback to the default content-type if type could not be determined
        if (contentType == null){
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
