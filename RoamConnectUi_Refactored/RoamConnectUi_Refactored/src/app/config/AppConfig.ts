import { Injectable, Inject} from '@angular/core';
import { environment } from '../../environments/environment';


@Injectable()
export class AppConfig {

 //baseUrl = "http://172.31.73.60:8080/"; 
 baseUrl = "http://localhost:8080/";

schemaName="ngoss/api";

constructor( ) {  }

activeDashboardLink="http://10.50.125.27:61006/npm/dynamicReportLTE.jsp?";

getDashboards = this.baseUrl+this.schemaName+"/login";
getReport=this.baseUrl+this.schemaName+"/reports/getspecificuserreport";

}
