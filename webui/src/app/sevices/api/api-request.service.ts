import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {AppConfig} from '../../app-config';
import {UserInfoService} from '../user-info.service';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';




@Injectable({
  providedIn: 'root'
})
export class ApiRequestService {

  constructor(
    private appConfig: AppConfig,
    private http: HttpClient,
    private router: Router,
    private userInfoService: UserInfoService
  ) {}

  getHeaders(): HttpHeaders {
    let headers = new HttpHeaders();
    headers = headers.append('Content-Type', 'application/json');
    if (this.userInfoService.isLoggedIn()) {
      const token = this.userInfoService.getStoredToken();
      headers = headers.append('Authorization', 'Bearer ' + token);
    }
    return headers;
  }

  get(url: string, urlParams?: HttpParams): Observable<any> {
    const me = this;
    return this.http.get(this.appConfig.baseApiPath + url, {headers: this.getHeaders(), params: urlParams})
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  post(url: string, body: Object): Observable<any> {
    const me = this;
    return this.http.post(this.appConfig.baseApiPath + url, JSON.stringify(body), {headers: this.getHeaders()})
      .pipe(
        catchError(err => this.handleError(err))
      );
  }


  put(url: string, body: Object): Observable<any> {
    const me = this;
    return this.http.put(this.appConfig.baseApiPath + url, JSON.stringify(body), {headers: this.getHeaders()})
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  delete(url: string): Observable<any> {
    const me = this;
    return this.http.delete(this.appConfig.baseApiPath + url, {headers: this.getHeaders()})
      .pipe(
        catchError(err => this.handleError(err))
      );
  }

  private handleError(err: HttpErrorResponse): Observable<any> {
    const me  = this;
    if (err.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', err.error.message);
      // return an observable with a user-facing error message
      return throwError(err || 'Something bad happened; please try again later.');
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,

      console.log('Some error in catch');
      console.error(
        `Backend returned code ${err.status}, ` +
        `body was: ${err.error}`);
      if (err.status === 401 || err.status === 403) {
        me.router.navigate(['/logout']);
      }

      // return an observable with a user-facing error message
      return throwError(err || 'Something bad happened; please try again later.');
    }
  }

}
