import { SongService } from './sevices/song.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AngularFontAwesomeModule } from 'angular-font-awesome';

import { AppComponent } from './app.component';
import { SongComponent } from './components/song/song.component';
import { AlbumComponent } from './components/album/album.component';
import { RouterModule, Routes } from '@angular/router';
import appRouter from './routes';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';


@NgModule({
  declarations: [
    AppComponent,
    SongComponent,
    AlbumComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AngularFontAwesomeModule,
    RouterModule.forRoot(
      appRouter,
      { enableTracing: true}
    )
  ],
  providers: [SongService],
  bootstrap: [AppComponent]
})
export class AppModule { SongComponent; AlbumComponent; PageNotFoundComponent;}
