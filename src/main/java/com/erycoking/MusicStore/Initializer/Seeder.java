package com.erycoking.MusicStore.Initializer;

import com.erycoking.MusicStore.models.PlayList;
import com.erycoking.MusicStore.models.Artist;
import com.erycoking.MusicStore.models.Song;
import com.erycoking.MusicStore.services.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class Seeder implements CommandLineRunner {

    @Autowired
    private PlayListService playListService;


    @Override
    public void run(String... args) throws Exception {

//        playListService.deleteAllPlayList();
        PlayList playList1 = new PlayList("kings",
                Arrays.asList(
                        new Song("hi", "url", "audio/mp3", 2, "rave song" , new Artist("vybz cartel")),
                        new Song("chaguo la moyo", "url", "audio/mp3", 2, "love song" , new Artist("otile brown")),
                        new Song("mazishi" , "url", "audio/mp3", 2,"local music",  new Artist("khaligraph"))
                ));

        PlayList playList2 = new PlayList("queens",
                Arrays.asList(
                        new Song("moyo", "url", "audio/mp3", 2, "rave song" , new Artist("rayvanny")),
                        new Song("nakupenda", "url", "audio/mp3", 2, "love song" , new Artist("diamond")),
                        new Song("nimedata", "url", "audio/mp3", 2,"local music",  new Artist("beyonce"))
                ));

        PlayList playList3 = new PlayList("prince",
                Arrays.asList(
                        new Song("bafuchafu", "url", "audio/mp3", 2, "rave song" , new Artist("wyre")),
                        new Song("dem wa nai", "url", "audio/mp3", 2, "love song" , new Artist("king kaka")),
                        new Song("noma" , "url", "audio/mp3", 2,"local music",  new Artist("meja"))
                ));

        PlayList playList4 = new PlayList("voltures",
                Arrays.asList(
                        new Song("toa form", "url", "audio/mp3", 2, "rave song" , new Artist("kenrazy")),
                        new Song("chokoza", "url", "audio/mp3", 2, "love song" , new Artist("avril")),
                        new Song("money" , "url", "audio/mp3", 2,"local music",  new Artist("rickross"))
                ));

        PlayList playList5 = new PlayList("wolves",
                Arrays.asList(
                        new Song("naskia kuzitoka", "url", "audio/mp3", 2, "rave song" , new Artist("kristoff")),
                        new Song("rambadam", "url", "audio/mp3", 2, "love song" , new Artist("rambadam")),
                        new Song("bazokizo" , "url", "audio/mp3", 2,"local music",  new Artist("bazokizo"))
                ));

//        playListService.saveAllPlayList(Arrays.asList(playList1, playList2, playList3, playList4, playList5));
    }
}
