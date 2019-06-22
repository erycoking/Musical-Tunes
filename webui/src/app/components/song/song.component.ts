import { Artist } from './../../models/artist/artist';
import { Album } from './../../models/album/album';
import { Song } from './../../models/song/song';
import { SongService } from './../../sevices/song.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-song',
  templateUrl: './song.component.html',
  styleUrls: ['./song.component.css']
})
export class SongComponent implements OnInit {

  allSongs: Song[];
  song: { any };
  newSong = new Song();
  newArtist = new Artist();
  albums: Album[];
  newAlbum = new Album();
  search: string;

  added = false;
  successMgs: string;
  deleted = false;
  errMsg: string;
  errOccurred = false;

  constructor(private songService: SongService) { }

  ngOnInit() {
    this.songService.currentFilterBy.subscribe(key => this.search = key);
    console.log(this.search);
    this.getAllSongs();
  }

  getAllSongs() {
    this.songService.getAllSongs().subscribe(
      res => {
        this.allSongs = res;
      },
      err => this.displayError(err)
    );
  }

  addSong() {
    this.songService.addSong(this.song).subscribe(
      res => {
        this.successMgs = 'Song successfully added';
        this.reset();
      },
      err => this.displayError(err)
    );
  }

  getSingleSong(id: number) {
    this.songService.getSong(id).subscribe(
      res => {
        this.newSong = res;
        this.newArtist = new Artist();
        this.newArtist.artistName = this.newSong.artist.artistName;
      },
      err => this.displayError(err)
    );
  }

  deleteSong(id: number) {
    this.songService.deleteSong(id).subscribe(
      res => {
        this.deleted = true;
        this.successMgs = 'Artist successfully deleted';
        this.reset();
      },
      err => this.displayError(err)
    );
  }

  edit(id: number) {
    this.getSingleSong(id);
  }

  delete(id: number) {
    this.deleteSong(id);
  }

  reset() {
    this.newAlbum = new Album();
    this.newArtist = new Artist();
    this.newSong = new Song();

    this.getAllSongs();
  }

  displayError(err) {
    this.errOccurred = true;
    this.errMsg = err;
  }

}
