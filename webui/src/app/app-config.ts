import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppConfig {
  // Provide all Application Configs here
  public version = '1.0.0';
  public locale  = 'en-US';
  public currencyFormat = { style: 'currency', currency: 'Ksh'};
  public dateFormat = { year: 'numeric', month: 'short', day: 'numeric'};

  // Api Related Configs
  public apiPort = '4200';
  public apiProtocol: string;
  public apiHostname: string;
  public baseApiPath: string;

  constructor() {
    if (this.apiProtocol === undefined) {
      this.apiProtocol = window.location.protocol;
    }
    if (this.apiPort === undefined) {
      this.apiPort = window.location.port;
    }
    if (this.apiHostname === undefined) {
      this.apiHostname = window.location.hostname;
    }
    if (this.apiHostname.includes('erycoking') || this.apiHostname.includes('heroku')) {
      this.baseApiPath = this.apiProtocol + '//' + this.apiHostname + '/';
    } else {
      this.baseApiPath = this.apiProtocol + '//' + this.apiHostname + ':8080/api/v1';
    }
    if (this.locale === undefined) {
      this.locale = navigator.language;
    }
  }

}
