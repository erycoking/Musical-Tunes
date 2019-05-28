package com.erycoking.MusicStore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "song_id")
    private int songId;

    @NonNull
    @Column(name = "song_name", nullable = false)
    private String songName;

    @NonNull
    @Column(name = "song_type", nullable = false)
    private String type;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "artist")
    private Artist artist;

    @JsonIgnore
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "album")
    private Album album;
}
