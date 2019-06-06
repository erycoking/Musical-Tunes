package com.erycoking.MusicStore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "artist_id")
    private int artistId;

    @NonNull
    @Column(name = "artist_name", nullable = false)
    private String artistName;

    @JsonIgnore
    @OneToMany(mappedBy ="artist",  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Song> songs;

}
