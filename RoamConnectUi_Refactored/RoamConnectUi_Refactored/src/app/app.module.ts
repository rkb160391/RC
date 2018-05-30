import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule, BrowserXhr, Http, Request, RequestOptionsArgs, Response, XHRBackend, RequestOptions, ConnectionBackend, Headers } from '@angular/http';

// import { routing,appRoutingProviders } from './app.routing';
import { routing, appRoutingProviders } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReportComponent } from './components/report/report.component';
//config files
import { AppConfig } from './config/AppConfig';
//components
import { DataTableComponent } from './components/data-table/data-table.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
//services
import { DashboardService } from './services/http/dashboard/dashboard.service' ;
import { ReportService } from './services/http/report/report.service' ;
import { HttpClientService } from './services/http/common/httpClient.service';
import { AuthdataService } from './services/http/common/authdata.service'
import { DataLoadSpinner } from './services/http/common/dataloadspinner.service';
import { UserdataService } from './services/common/user/userdata.service';
import {HeatMapService} from './services/http/charts/heatMap.service'
//moduless
import { ChartModule } from './components/chart/chart.module';
import { InventoryComponent } from './components/inventory/inventory.component';
import { LogoutComponent } from './components/logout/logout.component';


export function HttpServiceFactory(xhrBackend: XHRBackend, requestOptions: RequestOptions, authdataService: AuthdataService ,spinner : DataLoadSpinner) {
  return new HttpClientService(xhrBackend, requestOptions,  authdataService ,spinner);
};

@NgModule({
  declarations: [
    AppComponent,
    ReportComponent, DataTableComponent, DashboardComponent, InventoryComponent, LogoutComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    routing,
    ChartModule
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [appRoutingProviders, AppConfig ,DashboardService ,ReportService,AuthdataService,DataLoadSpinner,UserdataService,HeatMapService,
    {
      provide: Http,
      useFactory: HttpServiceFactory,
      deps: [XHRBackend, RequestOptions, AuthdataService ,DataLoadSpinner]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
