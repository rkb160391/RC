import { Injectable } from '@angular/core';

@Injectable()
export class AuthdataService {
  public  authToken: any;
  constructor() { }

  setAuthToken(token) {
    this.authToken = token;
  }

  getAuthToken() {
    return this.authToken;
  }
}
