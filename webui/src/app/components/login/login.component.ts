import { Component, OnInit } from '@angular/core';
import {AuthService, userCredentials} from '../../sevices/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any =  {};
  errMsg: string;

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit() {
  }

  signIn() {
    this.authService.login(this.model.username, this.model.password).subscribe(
      data => {
        if (data.success) {
          this.router.navigate([data.landingPage]);
        }
        this.errMsg = 'Username or password is incorrect';
        return;
      },
      err => {
        switch (err.status) {
          case 401:
            this.errMsg = 'Username or password is incorrect';
            break;
          case 404:
            this.errMsg = 'Service not found';
            break;
          case 408:
            this.errMsg = 'Request Timedout';
            break;
          case 500:
            this.errMsg = 'Internal Server Error';
            break;
          default:
            this.errMsg = 'Server Error';
            break;
        }
      }
    );
  }

}
