import { SongService } from './sevices/song.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Music-Store';
  search: string;

  constructor(private songService: SongService) {}

  ngOnInit() {
  }

  updateFilter(){
    this.songService.updateFilter(this.search);
  }

}
