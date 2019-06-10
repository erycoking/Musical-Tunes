import { Album } from './../models/album/album';
import { Artist } from './../models/artist/artist';
import { Song } from './../models/song/song';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/Http';
import { Observable, throwError, BehaviorSubject  } from 'rxjs';
import { catchError } from 'rxjs/operators';

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

  constructor(private httpClient: HttpClient) { }

  updateFilter(filter: string) {
    this.filterBy.next(filter);
    this.currentFilterBy.subscribe(key => console.log(key));
  }

  public getAlbum(albumId: number): Observable<Album>{
    return this.httpClient.get<Album>(`${this.baseUrl}/albums/` + albumId, this.httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public getArtist(artistId: number): Observable<Artist>{
    return this.httpClient.get<Artist>(`${this.baseUrl}/artists/` + artistId, this.httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public getSong(sondId: number): Observable<Song>{
    return this.httpClient.get<Song>(`${this.baseUrl}/songs/` + sondId, this.httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public getAllAlbums(): Observable<Album[]>{
    return this.httpClient.get<Album[]>(`${this.baseUrl}/albums`).pipe(
      catchError(this.handleError)
    );
  }

  public getAllArtists(): Observable<Artist[]>{
    return this.httpClient.get<Artist[]>(`${this.baseUrl}/artists`).pipe(
      catchError(this.handleError)
    );
  }

  public getAllSongs(): Observable<Song[]>{
    return this.httpClient.get<Song[]>(`${this.baseUrl}/songs`).pipe(
      catchError(this.handleError)
    );
  }

  public addAlbum(album : Album): Observable<Album>{
    if (album.albumId){
      return this.httpClient.put<Album>(`${this.baseUrl}/albums/` + album.albumId, album, this.httpOptions).pipe(
        catchError(this.handleError)
      );
    } else {
      return this.httpClient.post<Album>(`${this.baseUrl}/albums`, album, this.httpOptions).pipe(
        catchError(this.handleError)
      );
    }
  }

  public addArtists(artist : Artist): Observable<Artist>{
    if (artist.artistId){
      return this.httpClient.put<Artist>(`${this.baseUrl}/artists/` + artist.artistId, artist, this.httpOptions).pipe(
        catchError(this.handleError)
      );
    } else {
      return this.httpClient.post<Artist>(`${this.baseUrl}/artists`, artist, this.httpOptions).pipe(
        catchError(this.handleError)
      );
    }
  }

  public addSong(song : Song): Observable<Song>{
    if (song.songId){
      return this.httpClient.put<Song>(`${this.baseUrl}/songs/` + song.songId, song, this.httpOptions).pipe(
        catchError(this.handleError)
      );
    } else {
      return this.httpClient.post<Song>(`${this.baseUrl}/songs`, song, this.httpOptions).pipe(
        catchError(this.handleError)
      );
    }
  }

  public deleteAlbum(albumId: number): Observable<any>{
    return this.httpClient.delete(`${this.baseUrl}/albums/${albumId}`, this.httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public deleteArtists(artistId: number): Observable<any>{
    return this.httpClient.delete(`${this.baseUrl}/artists/${artistId}`, this.httpOptions).pipe(
      catchError(this.handleError)
    );
  }

  public deleteSong(sondId: number): Observable<any>{
    return this.httpClient.delete(`${this.baseUrl}/songs/${sondId}`, this.httpOptions).pipe(
      catchError(this.handleError)
    );
  }


  private handleError(error: HttpErrorResponse){
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    // return an observable with a user-facing error message
    return throwError('Something bad happened; please try again later.');
  }
}
