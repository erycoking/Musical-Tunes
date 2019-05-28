package com.erycoking.MusicStore.Initializer;

import com.erycoking.MusicStore.models.Album;
import com.erycoking.MusicStore.models.Artist;
import com.erycoking.MusicStore.models.Song;
import com.erycoking.MusicStore.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class Seeder implements CommandLineRunner {

    @Autowired
    private AlbumService albumService;


    @Override
    public void run(String... args) throws Exception {

        Album album1 = new Album("kings");
        album1.setSongs(
                Arrays.asList(
                        new Song("hi", "rave song" , new Artist("vybz cartel"), album1),
                        new Song("chaguo la moyo", "love song" , new Artist("otile brown"), album1),
                        new Song("mazishi" ,"local music",  new Artist("khaligraph"), album1)
                )
        );

        Album album2 = new Album("queens");
        album2.setSongs(
                Arrays.asList(
                        new Song("moyo", "rave song" , new Artist("rayvanny"), album2),
                        new Song("nakupenda", "love song" , new Artist("diamond"), album2),
                        new Song("nimedata" ,"local music",  new Artist("beyonce"), album2)
                )
        );

        Album album3 = new Album("prince");
        album3.setSongs(
                Arrays.asList(
                        new Song("bafuchafu", "rave song" , new Artist("wyre"), album3),
                        new Song("dem wa nai", "love song" , new Artist("king kaka"), album3),
                        new Song("noma" ,"local music",  new Artist("meja"), album2)
                )
        );

        Album album4 = new Album("voltures");
        album4.setSongs(
                Arrays.asList(
                        new Song("toa form", "rave song" , new Artist("kenrazy"), album4),
                        new Song("chokoza", "love song" , new Artist("avril"), album4),
                        new Song("money" ,"local music",  new Artist("rickross"), album4)
                )
        );

        Album album5 = new Album("wolves");
        album5.setSongs(
                Arrays.asList(
                        new Song("naskia kuzitoka", "rave song" , new Artist("kristoff"), album5),
                        new Song("rambadam", "love song" , new Artist("rambadam"), album5),
                        new Song("bazokizo" ,"local music",  new Artist("bazokizo"), album5)
                )
        );

        List<Album> albumList = Arrays.asList(album1, album2, album3, album4, album5);
//        albumService.saveAllAlbums(albumList);
    }
}
