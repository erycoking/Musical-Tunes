import { Artist } from './../artist/artist';

export class Song {
  songId: number;
  songName: string;
  songDownloadUri: string;
  songFileType: string;
  songSize: number;
  type: string;
  artist: Artist;
}

