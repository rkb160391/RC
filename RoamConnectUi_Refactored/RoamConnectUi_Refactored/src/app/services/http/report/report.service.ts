import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response, RequestMethod, Request } from '@angular/http';
import { AppConfig } from '../../../config/AppConfig';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class ReportService 
{
  constructor(private http: Http, private appConfig: AppConfig) 
  { }
  public getReport(request)
   {
    let postheaders = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' });
    let options = new RequestOptions({ headers: postheaders });
    return this.http.post(this.appConfig.getReport, request, options).map(res => res.json());
  }

}
