package com.erycoking.MusicStore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Song {

    public Song(@NonNull String songName, @NonNull String type, @NonNull Artist artist, Album album) {
        this.songName = songName;
        this.type = type;
        this.artist = artist;
        this.album = album;
    }

    public Song(@NonNull String songName, @NonNull String songDownloadUri, @NonNull String songFileType, @NonNull long songSize, @NonNull String type, @NonNull Artist artist) {
        this.songName = songName;
        this.songDownloadUri = songDownloadUri;
        this.songFileType = songFileType;
        this.songSize = songSize;
        this.type = type;
        this.artist = artist;
    }

    public Song(@NonNull String songName, @NonNull String songDownloadUri, @NonNull String songFileType, @NonNull long songSize, @NonNull String type, @NonNull Artist artist, Album album) {
        this.songName = songName;
        this.songDownloadUri = songDownloadUri;
        this.songFileType = songFileType;
        this.songSize = songSize;
        this.type = type;
        this.artist = artist;
        this.album = album;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_id")
    private int songId;

    @NonNull
    @Column(name = "song_name", nullable = false)
    private String songName;

    @NonNull
    @Column(name = "song_download_uri", nullable = true)
    private String songDownloadUri;

    @NonNull
    @Column(name = "song_file_type", nullable = true)
    private String songFileType;

    @NonNull
    @Column(name = "song_size", nullable = true)
    private long songSize;

    @NonNull
    @Column(name = "song_type", nullable = false)
    private String type;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "artist")
    private Artist artist;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "album")
    private Album album;
}
