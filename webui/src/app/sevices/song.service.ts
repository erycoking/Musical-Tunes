import { Album } from './../models/album/album';
import { Artist } from './../models/artist/artist';
import { Song } from './../models/song/song';
import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/Http';
import { Observable, BehaviorSubject  } from 'rxjs';
import {ApiRequestService} from "./api/api-request.service";

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
      'Accepts' : 'application/json'
    })
  };

  constructor(
    private httpClient: ApiRequestService
  ) { }

  updateFilter(filter: string) {
    this.filterBy.next(filter);
    this.currentFilterBy.subscribe(key => console.log(key));
  }

  public getAlbum(albumId: number): Observable<Album>{
    return this.httpClient.get(`/albums/` + albumId);
  }

  public getArtist(artistId: number): Observable<Artist>{
    return this.httpClient.get(`/artists/` + artistId);
  }

  public getSong(sondId: number): Observable<Song>{
    return this.httpClient.get(`/songs/` + sondId);
  }

  public getAllAlbums(): Observable<Album[]>{
    return this.httpClient.get(`/albums`);
  }

  public getAllArtists(): Observable<Artist[]>{
    return this.httpClient.get(`/artists`);
  }

  public getAllSongs(): Observable<Song[]>{
    return this.httpClient.get(`/songs`);
  }

  public addAlbum(album : Album): Observable<Album>{
    if (album.albumId){
      return this.httpClient.put(`/albums/` + album.albumId, album);
    } else {
      return this.httpClient.post(`/albums`, album);
    }
  }

  public addArtists(artist : Artist): Observable<Artist>{
    if (artist.artistId){
      return this.httpClient.put(`/artists/` + artist.artistId, artist);
    } else {
      return this.httpClient.post(`/artists`, artist);
    }
  }

  public addSong(song : Song): Observable<Song>{
    if (song.songId){
      return this.httpClient.put(`/songs/` + song.songId, song);
    } else {
      return this.httpClient.post(`/songs`, song);
    }
  }

  public deleteAlbum(albumId: number): Observable<any>{
    return this.httpClient.delete(`/albums/${albumId}`);
  }

  public deleteArtists(artistId: number): Observable<any>{
    return this.httpClient.delete(`/artists/${artistId}`);
  }

  public deleteSong(sondId: number): Observable<any>{
    return this.httpClient.delete(`/songs/${sondId}`);
  }
}
