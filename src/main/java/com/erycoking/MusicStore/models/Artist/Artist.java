package com.erycoking.MusicStore.models.Artist;

import com.erycoking.MusicStore.models.Song.Song;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@ApiModel(description = "Class representing an artist tracked by the application.")
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
    @ApiModelProperty(notes = "Unique identifier of the artist. No two artist can have the same id.", example = "1")
    private int artistId;

    @NotNull(message = "Artist name is required")
    @Size(min = 2, max = 50, message = "Artist name should be between 2 and 50 characters")
    @Column(name = "artist_name", nullable = false, length = 50)
    @ApiModelProperty(notes = "Name of the artist.", example = "John", required = true, position = 0)
    private String artistName;

    @JsonIgnore
    @OneToMany(mappedBy ="artist",  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ApiModelProperty(notes = "Songs of the artist.", position = 1)
    private List<Song> songs;

}
