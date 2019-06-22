import { SongInfo } from './song.service';
import { Album } from './../models/album/album';
import { Artist } from './../models/artist/artist';
import { Song } from './../models/song/song';
import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/Http';
import { Observable, BehaviorSubject  } from 'rxjs';
import {ApiRequestService} from './api/api-request.service';


export interface SongInfo {
  songId?: number;
  song: any;
  type: string;
  artist: string;
}

@Injectable({
  providedIn: 'root'
})
export class SongService {

  private filterBy = new BehaviorSubject<string>('');
  currentFilterBy = this.filterBy.asObservable();

  baseUrl = 'http://localhost:8080/api/v1';
  httpOptions = {
    headers : new HttpHeaders({
      'Content-type' : 'application/json',
      Accepts : 'application/json'
    })
  };

  constructor(
    private requestService: ApiRequestService
  ) { }

  updateFilter(filter: string) {
    this.filterBy.next(filter);
    this.currentFilterBy.subscribe(key => console.log(key));
  }

  public getAlbum(albumId: number): Observable<Album> {
    return this.requestService.get(`/albums/` + albumId);
  }

  public getArtist(artistId: number): Observable<Artist> {
    return this.requestService.get(`/artists/` + artistId);
  }

  public getSong(sondId: number): Observable<Song> {
    return this.requestService.get(`/songs/` + sondId);
  }

  public getAllAlbums(): Observable<Album[]> {
    return this.requestService.get(`/albums`);
  }

  public getAllArtists(): Observable<Artist[]> {
    return this.requestService.get(`/artists`);
  }

  public getAllSongs(): Observable<Song[]> {
    return this.requestService.get(`/songs`);
  }

  public addAlbum(album: Album): Observable<Album> {
    if (album.albumId) {
      return this.requestService.put(`/albums/` + album.albumId, album);
    } else {
      return this.requestService.post(`/albums`, album);
    }
  }

  public addArtists(artist: Artist): Observable<Artist> {
    if (artist.artistId) {
      return this.requestService.put(`/artists/` + artist.artistId, artist);
    } else {
      return this.requestService.post(`/artists`, artist);
    }
  }

  public addSong(song: any): Observable<Song> {
    console.log(song);
    const newSong: SongInfo = {
      songId: song.songId,
      song: song.song,
      type: song.type,
      artist: song.artist
    };
    console.log(newSong);
    if (song.songId) {
      return this.requestService.put(`/songs/` + song.songId, song);
    } else {
      return this.requestService.post(`/songs`, song);
    }
  }

  public deleteAlbum(albumId: number): Observable<any> {
    return this.requestService.delete(`/albums/${albumId}`);
  }

  public deleteArtists(artistId: number): Observable<any> {
    return this.requestService.delete(`/artists/${artistId}`);
  }

  public deleteSong(sondId: number): Observable<any> {
    return this.requestService.delete(`/songs/${sondId}`);
  }
}
