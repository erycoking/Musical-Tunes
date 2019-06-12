import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AlbumComponent } from './components/album/album.component';
import { SongComponent } from './components/song/song.component';
import { Routes } from '@angular/router';
import {AuthGuard} from './sevices/auth-guard.service';
import {LoginComponent} from './components/login/login.component';

const appRouter: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full'
  },
  {
    path: 'songs',
    component: SongComponent,
    canActivate: [AuthGuard],
    pathMatch: 'full'
  },
  {
    path: 'albums',
    component: AlbumComponent,
    canActivate: [AuthGuard],
    pathMatch: 'full'
  },
  { path: '**', component: PageNotFoundComponent }
];

export default appRouter;
