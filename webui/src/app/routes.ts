import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AlbumComponent } from './components/album/album.component';
import { SongComponent } from './components/song/song.component';
import { Routes } from '@angular/router';

const appRouter: Routes = [
  {
    path: '',
    redirectTo: 'songs',
    pathMatch: 'full'
  },
  {
    path: 'songs',
    component: SongComponent,
    pathMatch: 'full'
  },
  {
    path: 'albums',
    component: AlbumComponent,
    pathMatch: 'full'
  },
  { path: '**', component: PageNotFoundComponent }
];

export default appRouter;
