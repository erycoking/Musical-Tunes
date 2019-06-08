package com.erycoking.MusicStore.services;

import com.erycoking.MusicStore.config.FileStorageProperties;
import com.erycoking.MusicStore.exception.FileStorageException;
import com.erycoking.MusicStore.exception.MyFileNotFoundException;
import com.erycoking.MusicStore.models.Artist;
import com.erycoking.MusicStore.models.Song;
import com.erycoking.MusicStore.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    private final Path  fileStorageLocation;
    private SongRepository songRepository;

    public SongService(SongRepository songRepository, FileStorageProperties fileStorageProperties) {
        this.songRepository = songRepository;
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        }catch (Exception ex){
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Optional<Song> getSongById(int artistId){
        return songRepository.findById(artistId);
    }

    public Song getSong(String name){
        return songRepository.findBySongName(name);
    }

    public String storeFile(MultipartFile file){
//        Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")){
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }catch (IOException ex){
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }

    }

    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()){
                return resource;
            }else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        }catch (MalformedURLException ex){
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public List<Song> getAllSong(){
        return songRepository.findAll();
    }

    public List<Song> getAllSongByName(String name){
        return songRepository.findAllBySongName(name);
    }

    public List<Song> getAllByArtistName(String artist){
        return songRepository.findAllByArtist_ArtistName(artist);
    }

    public List<Song> getAllByArtistId(int artistId){
        return songRepository.findAllByArtist_ArtistId(artistId);
    }

    public Song save(Song song){
        return songRepository.save(song);
    }

    public List<Song> saveAllSongs(List<Song> songs){
        return songRepository.saveAll(songs);
    }

    public void deleteSong(int id){
        songRepository.deleteById(id);
    }

    public void deleteSong(Song song){
        songRepository.delete(song);
    }
}
