package com.erycoking.MusicStore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Artist {

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    public Artist(String artistName, List<Song> songs) {
        this.artistName = artistName;
        this.songs = songs;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "artist_id")
    private int artistId;

    @NotNull(message = "Artist name is required")
    @Size(min = 2, max = 50, message = "Artist name should be between 2 and 50 characters")
    @Column(name = "artist_name", nullable = false, length = 50)
    private String artistName;

    @JsonIgnore
    @OneToMany(mappedBy ="artist",  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Song> songs;

}
