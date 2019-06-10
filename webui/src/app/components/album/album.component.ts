import { Album } from './../../models/album/album';
import { Artist } from './../../models/artist/artist';
import { Song } from './../../models/song/song';
import { Component, OnInit } from '@angular/core';
import { SongService } from 'src/app/sevices/song.service';

@Component({
  selector: 'app-album',
  templateUrl: './album.component.html',
  styleUrls: ['./album.component.css']
})
export class AlbumComponent implements OnInit {

  allSongs: Song[];
  newSong = new Song();
  newArtist = new Artist();
  allAlbums: Album[];
  newAlbum = new Album();
  search: string;

  added = false;
  successMgs: string;
  deleted = false;
  errMsg: string;
  errOccurred = false;

  constructor(private songService: SongService) { }
  ngOnInit() {
    this.getAllAlbums();
  }

  open(id: number) {
    const table = document.getElementById(`album-songs-${id}`);
    if (table.style.display == 'block'){
      table.style.display = 'none';
    } else {
      table.style.display = 'block';
    }
  }

  getAllAlbums() {
    this.songService.getAllAlbums().subscribe(
      res => {
        this.allAlbums = res;
      },
      err => this.displayError(err)
    );
  }

  addAlbum(){
    this.songService.addAlbum(this.newAlbum).subscribe(
      res => {
        this.successMgs = 'Album successfully added';
        this.reset();
      },
      err => this.displayError(err)
    );
  }

  getSingleAlbum(id: number){
    this.songService.getAlbum(id).subscribe(
      res => {
        this.newAlbum = res;
      },
      err => this.displayError(err)
    );
  }

  deleteAlbum(id: number){
    this.songService.deleteAlbum(id).subscribe(
      res => {
        this.deleted = true;
        this.successMgs = 'Album successfully deleted';
        this.reset();
      },
      err => this.displayError(err)
    );
  }

  edit(id: number){
    this.getSingleAlbum(id);
  }

  delete(id: number){
    this.deleteAlbum(id);
  }

  reset(){
    this.newAlbum = new Album();
    this.newArtist = new Artist();
    this.newSong = new Song();

    this.getAllAlbums();
  }

  displayError(err){
    this.errOccurred = true;
    this.errMsg = err;
  }

}
