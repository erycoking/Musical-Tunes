package com.erycoking.MusicStore.models.Song;

import com.erycoking.MusicStore.models.Artist.Artist;
import com.erycoking.MusicStore.models.Playlist.PlayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Song")
public class Song {

    public Song(String songName, String songDownloadUri, String songFileType, long songSize, String type, @NonNull Artist artist) {
        this.songName = songName;
        this.songDownloadUri = songDownloadUri;
        this.songFileType = songFileType;
        this.songSize = songSize;
        this.type = type;
        this.artist = artist;
    }

    public Song(String songName, String songDownloadUri, String songFileType, long songSize, String type, @NonNull Artist artist, List<PlayList> playList) {
        this.songName = songName;
        this.songDownloadUri = songDownloadUri;
        this.songFileType = songFileType;
        this.songSize = songSize;
        this.type = type;
        this.artist = artist;
        this.playList = playList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_id")
    private int songId;

    @Column(name = "song_name", nullable = false)
    @NotNull(message = "song name is required")
    @Size(min = 2, max = 50, message = "song name should be between 2 and 50 characters")
    private String songName;

    @Column(name = "song_download_uri", nullable = false)
    @NotNull(message = "Song download Url is required")
    @Size(min = 2, message = "Song download Url should not be less than 2 characters")
    private String songDownloadUri;

    @Column(name = "song_file_type", nullable = false, length = 50)
    @NotNull(message = "song file type name is required")
    @Size(min = 2, max = 50, message = "song file type should be between 2 and 50 characters")
    private String songFileType;

    @Column(name = "song_size", nullable = true)
    @NotNull(message = "Song size is required")
    private long songSize;

    @Column(name = "song_type", nullable = false)
    @NotNull(message = "PlayList name is required")
    @Size(min = 2, max = 50, message = "song type should be between 2 and 50 characters")
    private String type;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "artist")
    @NotFound(action = NotFoundAction.IGNORE)
    private Artist artist;

    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PlayList> playList;
}
