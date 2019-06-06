package com.erycoking.MusicStore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "album_id")
    private int albumId;

    @NonNull
    @Column(name = "album_name", nullable = false)
    private String albumName;

    @OneToMany(mappedBy ="album",  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Song> songs;

}
