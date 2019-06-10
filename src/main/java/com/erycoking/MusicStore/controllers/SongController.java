package com.erycoking.MusicStore.controllers;

import com.erycoking.MusicStore.models.Artist;
import com.erycoking.MusicStore.models.PlayList;
import com.erycoking.MusicStore.models.Song;
import com.erycoking.MusicStore.services.ArtistService;
import com.erycoking.MusicStore.services.PlayListService;
import com.erycoking.MusicStore.services.SongService;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/songs")
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

    @GetMapping("/{id:[\\d]+}/playlist")
    ResponseEntity<?> getSongByPlayListId(@PathVariable int id) {
        Optional<PlayList> optionalPlayList = playListService.getPlayListId(id);
        if (optionalPlayList.isPresent()){
            List<Song> song = optionalPlayList.get().getSongs();
            if(song == null || song.isEmpty()) {
                return ResponseEntity.ok().body("No songs available");
            }
            return ResponseEntity.ok().body(song);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{name:[a-z]+}/playlist")
    ResponseEntity<?> getSongByAlbum(@PathVariable String name) {
        PlayList playList = playListService.getPlayList(name);
        if (playList != null){
            List<Song> allSongs = playList.getSongs();
            if(allSongs == null || allSongs.isEmpty()){
                return ResponseEntity.ok().body("No allSongs available");
            }
            return ResponseEntity.ok().body(allSongs);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(headers=("content-type=multipart/*"))
    ResponseEntity<?> createSong(@RequestParam("song") MultipartFile inputFile,
                                 @RequestParam("type") String type,
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
                    return ResponseEntity.badRequest().body("Song already Exists");
                }
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }else {
            return ResponseEntity.badRequest().body("Song is required");
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateSong(@PathVariable int id, @Valid @RequestBody Song song){
        if (songService.getSongById(id) == null){
            return ResponseEntity.notFound().build();
        }else{
            log.info("Request to update song: {}", song);
            try {
                Song result = songService.save(song);
                return ResponseEntity.ok().body(result);
            }catch (Exception e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteSong(@PathVariable int id){
        log.info("Request to delete song: {}", id);
        songService.deleteSong(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/downloads/{fileName:.+}")
    ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request){
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
