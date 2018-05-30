import { Component, OnInit, AfterViewInit, Input, ChangeDetectorRef } from '@angular/core';
import { Http } from '@angular/http';
import { ActivatedRoute, Routes, RouterModule, Router, Params } from '@angular/router';
import { ReportService } from '../../services/http/report/report.service';
import { DataLoadSpinner } from '../../services/http/common/dataloadspinner.service';
import { UserdataService } from '../../services/common/user/userdata.service';
import { DashboardService } from '../../services/http/dashboard/dashboard.service';


declare var $: any;


@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit, AfterViewInit {

  report: any;
  reportId: any;
  dashboardId: any;
  reportType: any = "";
  actualReportType: any = "";
  requestParam: any;
  dashboard:any;
  selected: any;
  operatrId:any;
  operatorName:any;
  userDetails:any;
  isBTUser:boolean=false;
  

  constructor(private dashboardService: DashboardService,private route: ActivatedRoute, private router: Router, private reportService: ReportService, private spinner: DataLoadSpinner, private userdataService: UserdataService) {
 
    this.dashboard=this.dashboardService.getDashboard();
    this.operatrId=this.userdataService.getOperatorId();
    this.userDetails=this.userdataService.getUserDetails();
    //this.operatorName=this.userdataService.getUserDetails().carrierName;
    this.isBTUser=this.userdataService.isBtUser(this.userDetails.id);
    route.params.subscribe(val => {
      let res = this.router.url.split('/');
      if (res != null) {
        //this.reportId = res[3];
        this.actualReportType=res[3];
        this.dashboardId = res[4];
      }
      this.getReportData();    
    });

  }

  adminClick()
  {
    this.router.navigateByUrl("/roamconnect/inventory");
  }

    openNav() {
    document.getElementById("mySidenav").style.width = "250px";
  }

   closeNav() {
    document.getElementById("mySidenav").style.width = "0";
  }
  getDashboards() {
   // console.log("--------"+JSON.stringify(this.userdataService.getUserDetails()));
    this.router.navigateByUrl('/roamconnect?operatorId=' + this.userdataService.getOperatorId());
  }

  ngAfterViewInit() {
    console.log("----ngDoCheck----");
   
  }
  loadLogout()
  {
    this.router.navigateByUrl("/roamconnect/logout");
  }





  ngOnInit() {

    // this.sub = this.route.params.subscribe(params => {
    //   this.paramsChanged(params['id']);
    // });

    this.route.params.subscribe(params => {
      let res = this.router.url.split('/');
      if (res != null) {
        //this.reportId = res[3];
        this.actualReportType=res[3];
        this.dashboardId = res[4];
      }

      //this.getReportData();
        });
     //this.getReportData();
  }
 
  getReportData() {
    console.log("------1----");
    this.spinner.show();
    let startdate = new Date();
    let enddate = new Date();
    startdate.setMinutes(startdate.getMinutes() - 15);
    let request :any;
    if(this.isBTUser){
      request = {
        // reportId: this.reportId,
         reportType:this.actualReportType,
         dashboardId: this.dashboardId,
         level:1,
         requestParam:{
         intervals: startdate.toISOString() + '/' + enddate.toISOString()
         }
       }
    }else{
      request = {
        // reportId: this.reportId,
         reportType:this.actualReportType,
         dashboardId: this.dashboardId,
         level:2,
         requestParam:{
          filter: { fields: [{ value: this.userDetails.carrierName }] },
         intervals: startdate.toISOString() + '/' + enddate.toISOString()
         
         }
   
       }
    }
    
    this.reportService.getReport(request).subscribe(data => {
      this.spinner.hide();
      if (data != undefined && data != null) {

        this.reportType = data.dashboardReports[0].reports[0].response.reportConfiguration.configuration.type;
       this.report=null;
        this.report = data.dashboardReports[0].reports[0];
        console.log(this.report);
      }

    });

  }
  getReport_nav(report , dashboard) {
   
    if(report.reportType=="HeatMapForTop10MSU"){
        console.log("Heat Map Report");
        this.router.navigateByUrl("/roamconnect/heatmap")

    }else{
          console.log("-------report----",report);
          this.selected=report;
          if (report.reportPolicy != null && report.reportPolicy.islink) {
            let url = report.reportPolicy.url;
          if(url != undefined &&  url != null)
            {let splitedURL = url.split('?');
            let fields: Array<string> = splitedURL[1].split('&');
            let index = -1;
            let date = new Date();
            let datestring = date.getDate() + '-' + ((date.getMonth()) + 1) + '-' + date.getFullYear();
            for (let field of fields) {
              index = index + 1;
              if (field.match('startDate') || field.match('endDate')) {
                field = field.split('=')[0] + '=' + datestring;
                fields[index] = field;
              }

            }
              window.open(splitedURL[0] + '?' + fields.join("&"));
          }
          }
          else {
            this.dashboardService.setDashboard(dashboard);
          
            this.router.navigateByUrl("/roamconnect/report/"+report.reportType+"/"+dashboard.id+"/1");
            

          }
    }
  }

  isActive(report)
  {
    return this.selected === report;
  }


  getActiveDashboard_all()
  {
    let date = new Date();
    let month=date.getMonth()+1;
    let dateString=date.getDate()+'-'+month+'-'+date.getFullYear();
    this.dashboardService.getActiveDashboard(dateString);
  }

}
