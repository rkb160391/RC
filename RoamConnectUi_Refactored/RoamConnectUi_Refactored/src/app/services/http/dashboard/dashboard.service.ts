import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, Response, RequestMethod, Request} from '@angular/http';
import { AppConfig } from '../../../config/AppConfig';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';


@Injectable()
export class DashboardService {

 public dashboard: any;
 public operatorId: any;
  constructor(private http: Http, private appConfig: AppConfig) { }

 public getDashboards(operatoreId)
  {
    let postheaders = new Headers({ 'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*' });
    postheaders.append('operatorId',operatoreId);
    let options = new RequestOptions({ headers: postheaders });
    return this.http.post(this.appConfig.getDashboards, "", options).map(res => res);
  }

  getActiveDashboard(dateString:any){    
    window.open(this.appConfig.activeDashboardLink+"startDate="+dateString+"&endDate="+dateString);
  }
  
    public getDashboard() {
        return this.dashboard;
    }

    public setDashboard(dashboard:any)
    {
    this.dashboard=dashboard;
    }

    public setScope(dashboard: any): void {
        this.dashboard = dashboard;
    }
}
