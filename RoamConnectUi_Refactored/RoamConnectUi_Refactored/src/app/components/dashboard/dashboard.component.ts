import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DashboardService } from '../../services/http/dashboard/dashboard.service';
import { AuthdataService } from '../../services/http/common/authdata.service';
import { DataLoadSpinner } from '../../services/http/common/dataloadspinner.service';
import { UserdataService } from '../../services/common/user/userdata.service';
import sampleData from './sampleData';

declare var $: any;

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  dashboards: any;
  operatorId: any;
  userDetails:any;
  operatorName:any;
  isBTUser:boolean=false;
  constructor(private router: Router, private route: ActivatedRoute, private dashboardService: DashboardService,private authdataService :AuthdataService ,private spinner : DataLoadSpinner,private userdataService : UserdataService) {
    //console.log("--------"+JSON.stringify(this.userdataService.getUserDetails()));
    if(this.userdataService.getUserDetails()!=undefined){
      let data=this.userdataService.getUserDetails();
     // console.log("--------"+JSON.stringify( data.carrierName));
      this.operatorName=data.carrierName;
    }
  }
  ngOnInit() {
    this.getDashboardDetails();
  }
    openNav() {
    document.getElementById("mySidenav").style.width = "250px";
  }

   closeNav() {
    document.getElementById("mySidenav").style.width = "0";
  }
  getDashboardDetails() {
    
    //this.operatorName=this.userdataService.getUserDetails();
      this.route.queryParams.subscribe(params => {
      this.operatorId = params['operatorId'];
    });
    if( this.userdataService.getDashboardList() != null && this.userdataService.getDashboardList().length > 0)
    {
      this.dashboards =  this.userdataService.getDashboardList();
     
    }
   else if ( this.dashboards == undefined  && this.operatorId != undefined && this.operatorId != null) {
    this.spinner.show();
      this.userdataService.setOperatorId(this.operatorId);
      this.dashboardService.getDashboards(this.operatorId).subscribe(data => {
        this.spinner.hide();
        this.authdataService.setAuthToken(data.headers.get("auth-token"));
        let dashboarddata = data.json();
        this.dashboards = dashboarddata.dashboardReports;
        this.userdataService.setUserDetails(dashboarddata.userDetails);
        this.isBTUser=this.userdataService.isBtUser(this.operatorId);
        this.userdataService.setDashboardList(this.dashboards);
        this.userdataService.setOperatorId(this.operatorId);
        this.operatorName=dashboarddata.userDetails.carrierName;


      });
    }
    //this.dashboards = sampleData.dashboardReports;
  }
  getReport(report , dashboard) {
    if(report.reportType=="HeatMapForTop10MSU"){
        console.log("Heat Map Report");
        this.dashboardService.setDashboard(dashboard);
        this.router.navigateByUrl("/roamconnect/heatmap")
    }else{
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
  getActiveDashboard(){
    let date = new Date();
    let month=date.getMonth()+1;
    let dateString=date.getDate()+'-'+month+'-'+date.getFullYear();
    this.dashboardService.getActiveDashboard(dateString);
  }
  // adminClick()
  // {
  //   this.router.navigateByUrl("/roamconnect/inventory");
  // }

  getDashboards() 
  {
    console.log("--------"+JSON.stringify(this.userdataService.getUserDetails()));
    let data=this.userdataService.getUserDetails();
    this.operatorName=data.carrierName;
    this.router.navigateByUrl('/roamconnect?operatorId=' + this.userdataService.getOperatorId());
  }

  loadLogout()
  {
    this.router.navigateByUrl("/roamconnect/logout");
  }
  
  ngAfterViewInit()
  {
    $('#btn_yes').on(
      'click',
      function(evt)
      {
          console.log('triggered');
      }
      );
  }


  }
