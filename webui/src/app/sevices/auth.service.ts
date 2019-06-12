import {Injectable} from "@angular/core";
import {ApiRequestService} from "./api/api-request.service";
import {LoginInfoInStorage, UserInfoService} from "./user-info.service";
import {BehaviorSubject, Observable} from "rxjs";

export interface userCredentials {
  username: string;
  password: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    private apiRequest: ApiRequestService,
    private userInfo: UserInfoService
  ){}

  public landingPage:string = "/songs";
  login(username: string, password: string): Observable<any>{
    let userObj: userCredentials = {
      username,
      password
    };

    let loginDataSubject: BehaviorSubject<any> = new BehaviorSubject([]);
    let loginInfoReturn: LoginInfoInStorage;

    this.apiRequest.post("/auth/signin", userObj).subscribe(
      data => {
        if (data.token !== undefined && data.token !== null) {
          loginInfoReturn = {
            success : true,
            landingPage: '/songs',
            message: 'Login success',
            user: {
              displayName: data.username,
              token: data.token,
              username: data.username
            }
          };

          this.userInfo.storeUserInfo(JSON.stringify(loginInfoReturn));
        }else {
          loginInfoReturn = {
            success: false,
            landingPage: 'login',
            message: data
          };
        }

        loginDataSubject.next(loginInfoReturn);
      },
      err => {
        loginInfoReturn = {
          success: false,
          message: err.url + ' >>> ' + err.statusText + ' >>> ' + '['+ err.status +']',
          landingPage: 'login'
        }
      }
    );

    return loginDataSubject;
  }
}
