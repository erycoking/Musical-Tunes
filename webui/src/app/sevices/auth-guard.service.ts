import {
  ActivatedRouteSnapshot,
  CanActivate,
  CanActivateChild,
  Router,
  RouterStateSnapshot,
  UrlTree
} from "@angular/router";
import {UserInfoService} from "./user-info.service";
import {Observable} from "rxjs";
import {AuthService} from "./auth.service";

export class AuthGuard implements CanActivate, CanActivateChild {

  constructor(
    private router: Router,
    private authService: AuthService,
    private userInfoService: UserInfoService
  ){}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    let url: string = state.url;
    return this.checkLogin(url);
  }

  canActivateChild(childRoute: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.canActivate(childRoute, state);
  }


  checkLogin(url: string): boolean {
    if (this.userInfoService.isLoggedIn()){
      return true
    }
    console.log("user not logged in")
    this.authService.landingPage = url;
    this.router.navigate(['login']);
    return false;
  }
}
