package com.erycoking.MusicStore.models;

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
    private int playListId;

    @Column(name = "playList_name", nullable = false, length = 50)
    @NotNull(message = "PlayList name is required")
    @Size(min = 2, max = 50, message = "PlayList name should be between 2 and 50 characters")
    private String playListName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Song> songs;

}
