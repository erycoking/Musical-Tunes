import { Injectable } from '@angular/core';

export interface UserInStorage{
  username: string;
  token: string;
  displayName: string;
}

export interface LoginInfoInStorage{
  success: boolean;
  message: string;
  landingPage: string;
  user?:UserInStorage;
}


@Injectable({
  providedIn: 'root'
})
export class UserInfoService {

  public currentUserKey:string = 'currentUser';
  public storage:Storage = sessionStorage;

  // store userInfo from sessionStorage
  storeUserInfo(userInfo:string){
    this.currentUserKey = this.storage.getItem(this.currentUserKey);
  }

  // remove userInfo from sessionStorage
  removeUserInfo(){
    this.storage.removeItem(this.currentUserKey);
  }

  getUserInfo():UserInStorage|null{
    try{
      let userInfoString:string  = this.storage.getItem(this.currentUserKey);
      if(userInfoString){
        let userObj:UserInStorage = JSON.parse(userInfoString);
        return userObj;
      }else {
        return null;
      }
    }catch (e) {
      return null;
    }
  }

  isLoggedIn():boolean {
    return this.storage.getItem(this.currentUserKey)? true: false;
  }

  // Get users dispaly name from sessionStorage
  getUserName(): string|null{
    let userObj = this.getUserInfo();
    if (userObj !== null){
      return userObj.displayName;
    }
    return null;
  }

  getStoredToken(): string|null {
    let userObj = this.getUserInfo();
    if (userObj !== null){
      return userObj.token
    }
    return null;
  }
}


