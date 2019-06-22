package com.erycoking.MusicStore.models.Playlist;

import com.erycoking.MusicStore.models.Song.Song;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PlayList")
@ApiModel(description = "Class representing an playlist tracked by the application.")
public class PlayList {

    public PlayList(String playListName) {
        this.playListName = playListName;
    }

    public PlayList(String playListName, List<Song> songs) {
        this.playListName = playListName;
        this.songs = songs;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "playList_id")
    @ApiModelProperty
    private int playListId;

    @Column(name = "playList_name", nullable = false, length = 50)
    @NotNull(message = "PlayList name is required")
    @Size(min = 2, max = 50, message = "PlayList name should be between 2 and 50 characters")
    @ApiModelProperty(notes = "Name of the playlist.", example = "locals", required = true, position = 0)
    private String playListName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ApiModelProperty(notes = "Songs of the artist.", position = 1)
    private List<Song> songs;

}
