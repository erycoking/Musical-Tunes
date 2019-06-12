import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {AppConfig} from "../../app-config";
import {UserInfoService} from "../user-info.service";
import {Observable, throwError} from "rxjs";
import {catchError} from "rxjs/operators";




@Injectable({
  providedIn: 'root'
})
export class ApiRequestService{

  constructor(
    private appConfig: AppConfig,
    private http: HttpClient,
    private router: Router,
    private userInfoService: UserInfoService
  ) {}

  getHeaders(): HttpHeaders{
    let headers = new HttpHeaders();
    let token  = this.userInfoService.getStoredToken();
    headers = headers.append('Content-Type', 'application/json');
    if (token !== null) {
      headers = headers.append("Authorization", 'Bearer ' + token);
    }
    return headers;
  }

  get(url:string, urlParams?:HttpParams): Observable<any> {
    let me = this;
    return this.http.get(this.appConfig.baseApiPath + url, {headers: this.getHeaders(), params: urlParams})
      .pipe(
        catchError(err => {
          console.log("Some error in catch");
          if (err.status === 401 || err.status == 403){
            me.router.navigate(['/logout']);
          }
          return throwError(err || 'Server error')
        })
      );
  }

  post(url:string, body:Object): Observable<any>{
    let me = this;
    return this.http.post(this.appConfig.baseApiPath + url, JSON.stringify(body), {headers: this.getHeaders()})
      .pipe(
        catchError(err => {
          if (err.status === 401){
            me.router.navigate(['/logout']);
          }
          return throwError(err || 'Server error')
        })
      );
  }


  put(url:string, body:Object): Observable<any>{
    let me = this;
    return this.http.put(this.appConfig.baseApiPath + url, JSON.stringify(body), {headers: this.getHeaders()})
      .pipe(
        catchError(err => {
          if (err.status === 401){
            me.router.navigate(['/logout']);
          }
          return throwError(err || 'Server error')
        })
      );
  }

  delete(url:string): Observable<any>{
    let me = this;
    return this.http.delete(this.appConfig.baseApiPath + url, {headers: this.getHeaders()})
      .pipe(
        catchError(err => {
          if (err.status === 401){
            me.router.navigate(['/logout']);
          }
          return throwError(err || 'Server error')
        })
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
