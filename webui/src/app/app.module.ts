import { AppConfig } from './app-config';
import { UserInfoService } from './sevices/user-info.service';
import { ApiRequestService } from './sevices/api/api-request.service';
import { AuthService } from './sevices/auth.service';
import { AuthGuard } from './sevices/auth-guard.service';
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
import { LoginComponent } from './components/login/login.component';


@NgModule({
  declarations: [
    AppComponent,
    SongComponent,
    AlbumComponent,
    PageNotFoundComponent,
    LoginComponent
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
  providers: [
    SongService,
    AuthGuard,
    AppConfig,
    AuthService,
    ApiRequestService,
    UserInfoService],
  bootstrap: [AppComponent]
})
export class AppModule {
  SongComponent;
  AlbumComponent;
  PageNotFoundComponent;
  LoginComponent;
}
