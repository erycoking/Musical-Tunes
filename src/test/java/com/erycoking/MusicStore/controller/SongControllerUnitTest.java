package com.erycoking.MusicStore.controller;

import com.epages.restdocs.raml.RamlResourceSnippetParameters;
import com.erycoking.MusicStore.controllers.SongController;
import com.erycoking.MusicStore.models.Artist.Artist;
import com.erycoking.MusicStore.models.Playlist.PlayList;
import com.erycoking.MusicStore.models.Song.Song;
import com.erycoking.MusicStore.services.ArtistService;
import com.erycoking.MusicStore.services.PlayListService;
import com.erycoking.MusicStore.services.SongService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import static com.epages.restdocs.raml.RamlResourceDocumentation.ramlResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SongController.class, secure = false)
public class SongControllerUnitTest {

    @MockBean
    SongService songService;

    @MockBean
    ArtistService artistService;

    @MockBean
    PlayListService playListService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() {
        Song testSong = new Song("love",
                null, null, 10,
                "local", new Artist("Otile"), null);
        given(this.songService.getSong(anyString()))
                .willReturn(testSong);

        given(this.songService.getSongById(1))
                .willReturn(Optional.of(testSong));

        given(this.songService.save(any(Song.class)))
                .willReturn(testSong);

        given(this.songService.getAllSong())
                .willReturn(new ArrayList<>());

        doNothing().when(this.songService).deleteSong(anyInt());
    }

    @Test
    public void testSong() throws Exception {
        this.mockMvc.perform(get("/songs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andDo(document("songs-get",
                        ramlResource(RamlResourceSnippetParameters.builder()
                                .description("get all notes")
                                .responseFields(
                                        fieldWithPath("name").description("song title")
                                ).build()
                        )));
        verify(this.songService, times(1)).getAllSong();
        verifyNoMoreInteractions(this.songService);
    }
}
