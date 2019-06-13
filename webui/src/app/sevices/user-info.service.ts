import { Injectable } from '@angular/core';

export interface UserInStorage {
  username: string;
  token: string;
  displayName: string;
}

export interface LoginInfoInStorage {
  success: boolean;
  message: string;
  landingPage: string;
  user?: UserInStorage;
}


@Injectable({
  providedIn: 'root'
})
export class UserInfoService {

  public currentUserKey = 'currentUser';
  public storage: Storage = sessionStorage;

  // store userInfo from sessionStorage
  storeUserInfo(userInfo: string) {
    this.storage.setItem(this.currentUserKey, userInfo);
  }

  // remove userInfo from sessionStorage
  removeUserInfo() {
    this.storage.removeItem(this.currentUserKey);
  }

  getUserInfo(): UserInStorage|null {
    try {
      const userInfoString: string  = this.storage.getItem(this.currentUserKey);
      if (userInfoString) {
        const userInfoObj: LoginInfoInStorage = JSON.parse(userInfoString);
        const userObj: UserInStorage = userInfoObj.user;
        console.log(userObj);
        return userObj;
      } else {
        return null;
      }
    } catch (e) {
      return null;
    }
  }

  isLoggedIn(): boolean {
    this.storage.getItem(this.currentUserKey) ? console.log('user available') : console.log('user not available');
    return this.storage.getItem(this.currentUserKey) ? true : false;
  }

  // Get users dispaly name from sessionStorage
  getUserName(): string|null {
    const userObj = this.getUserInfo();
    if (userObj !== null) {
      return userObj.displayName;
    }
    return null;
  }

  getStoredToken(): string|null {
    const userObj = this.getUserInfo();
    if (userObj !== null) {
      return userObj.token;
    }
    return null;
  }
}


