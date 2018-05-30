import { Component, OnInit, Input,SimpleChanges } from '@angular/core';
import {HeatMapService} from '../../../services/http/charts/heatMap.service';
import { DashboardService } from '../../../services/http/dashboard/dashboard.service';
import sampleData from './sampleData';
import { DataLoadSpinner } from '../../../services/http/common/dataloadspinner.service';
import { UserdataService } from '../../../services/common/user/userdata.service';
import { ActivatedRoute, Routes, RouterModule, Router, Params } from '@angular/router';
import { SimpleChange } from '@angular/core/src/change_detection/change_detection_util';

declare var $: any;
@Component({
  selector: 'app-heatmap',
  templateUrl: './heatmap.component.html',
  styleUrls: ['./heatmap.component.css']
})
export class HeatmapComponent implements OnInit {
 
  reportData: any;
  metrics: Array<any> = [];
  legends: Array<any> = [];
  dataArray: Array<any> = [];
  dataArrayRes: Array<any> = [];

  errorReportData:any;
  errorMetrics:Array<any> = [];
  errorDataArrayRes:Array<any> = [];
  errorDataArray: Array<any> = [];
  isErrorListing: boolean=false;
  isHeatMap: boolean=false;
  userDetails:any;
  isBTUser:boolean=false;

  successReportData :any;
  successMetrics :Array<any> = [];
  successDataArrayRes :Array<any> = [];
  successDataArray: Array<any> = [];

  dashboard:any;
  operatorName:any;
  operatrId:any;
  currentFullDate: any;
  selectedDate:any;
  displayFullDaySelection: boolean = false;
  startDate: any;
  endDate: any;
  

  constructor(private heatMapService:HeatMapService,private router: Router,private dashboardService: DashboardService, private spinner : DataLoadSpinner,private userdataService: UserdataService) {
    this.userDetails= this.userdataService.getUserDetails();
    this.dashboard=this.dashboardService.getDashboard();
    this.operatrId=this.userdataService.getOperatorId();
    this.userDetails=this.userdataService.getUserDetails();
    this.operatorName=this.userdataService.getUserDetails().carrierName;
    this.isBTUser=this.userdataService.isBtUser(this.userDetails.id);
    if(this.userDetails.id.toString().length==9){
      this.isBTUser=true;
    }
    
  



   }
   getDashboards() {
    this.router.navigateByUrl('/roamconnect?operatorId=' + this.userdataService.getOperatorId());
  }
  ngOnInit() {

    
    
    // // console.log("heatmap");
     
    // // this.getReportData();
    //  this.drawErrorListingHeatMap();
    //  //this.getMap();
    //  this.getErrorListingHeatMapReports();
    //  if(this.userDetails.id.toString().length!=9){
    //   this.drawTop10SuccessRateHeatMap();
    //   this.getTop10SuccessRateData();
    //  }

    //  let date = new Date();
    //  this.selectedDate = date.getDate() + "-" + (date.getMonth() + 1) + "-" + date.getFullYear();
    
  }
  

  doNullIntialize()
  {
    this.reportData=null;
    this.errorReportData=null;
    this.successReportData=null;
    this.dataArray=null;
    this.metrics=null;
    this.dataArrayRes=null;
    this.successReportData =null;
    this.successMetrics =null;
    this.successDataArrayRes =null;
    this.successDataArray=null;
    this.errorReportData=null;
    this.errorMetrics=null;
    this.errorDataArrayRes=null;
    this.errorDataArray=null;
  }

  ngOnChanges(changes: SimpleChanges) 
  {
    this.dataArray=[];
    this.successDataArray=[];
    this.errorDataArray=[];
    
  }


  getHeatMap()
  {
        //console.log(this.selectedDate);
    let startDate=this.selectedDate;
    let endDate=this.selectedDate;
    
    this.dataArray=[];
    this.successDataArray=[];
    this.errorDataArray=[];

    this.setstartAndEnddate();
    this.getReportData();
    this.drawHeatMap();
    this.getErrorListingHeatMapReports();
    if(this.userDetails.id.toString().length!=9)
    {
     this.drawTop10SuccessRateHeatMap();
     this.getTop10SuccessRateData();
    }
   
    
    
 }


 adminClick()
 {
  this.router.navigateByUrl("/roamconnect/inventory");
 }

 loadLogout()
 {  
  this.router.navigateByUrl("/roamconnect/logout");
 }

  getDatepicker()
  {
    

  }

  setstartAndEnddate() 
  {
    let datearray = this.selectedDate.split("-");
   // alert("DataArray Contines"+datearray);
    let enddate: Date = new Date();
    let startDate:Date=new Date();

    enddate.setDate(datearray[0]);
    enddate.setMonth(datearray[1]-1);
    enddate.setFullYear(datearray[2]);
    enddate.setHours(23+5);
    enddate.setMinutes(59+30); 
    enddate.setSeconds(59); 
    enddate.setMilliseconds(0);
    this.endDate=enddate;


    startDate.setDate(datearray[0]);
    startDate.setMonth(datearray[1]-1);
    startDate.setFullYear(datearray[2]);
    startDate.setHours(0+5);
    startDate.setMinutes(0+30);
    startDate.setSeconds(0);  
    startDate.setMilliseconds(0);
    this.startDate=startDate;
  }

  ngAfterViewInit() {
    
        this.getDatepicker();
        let date = new Date();
        let tomorrowdate = (`0${date.getDate()}`).slice(-2);
        let currentDateSingle = date.getDate();
        let currentDate: any = (`0${currentDateSingle}`).slice(-2);
        let druiddate: string;
        let month = date.getMonth() + 1;
        let currentMonth = (`0${month}`).slice(-2);
        let currentYear = date.getFullYear();
        let currentHours = date.getHours();
        let currentMin = date.getMinutes();
        this.currentFullDate = currentDate + "-" + currentMonth + "-" + currentYear;
    
        let startDate;
        let parent = this;
        $("#date").datepicker({
          autoclose: true,
          minViewMode: 0,
          minDate: new Date(currentYear, month - 15, currentDateSingle + 1),
          maxDate: new Date(),
          dateFormat: "dd-mm-yyyy",
          orientation: "bottom",
          weekStart: 1,
          numberOfMonths: 12,
          onSelect: function (dateText) {
            $('#date').val(dateText);
            parent.selectedDate = dateText;
            console.log(dateText)
            //this.startDate=dateText;
            //parent.getUpdatedData();  
          }
        });
    
        let todayDate = `${currentDate}-${currentMonth}-${currentYear}`;
        //this.startDate=todayDate;
        $('#date').val(todayDate);
       
    
      }


  getReportData() { 
    let request:any;
    if(this.userDetails.id.toString().length!=9){
      request={
        reportType: "HeatMapForTop10MSU",
        dashboardId: "0EDvbL",
        level: 2,
        requestParam: {
          filter: {
            fields: [
              {
                value: this.userDetails.carrierName
              }
            ]
          },
         intervals: this.startDate.toISOString() + "/" + this.endDate.toISOString()
        }
      }
    }else{
      request = {
        reportType: 'HeatMapForTop10MSU',
        dashboardId: '0EDvbL',
        level:1,
        requestParam:{
          intervals: this.startDate.toISOString() + "/" + this.endDate.toISOString()
        }
      }
    }
       
   
    this.heatMapService.getHeatMapReport(request).subscribe(data => {
         this.dataArray=[];
         this.isHeatMap=true;
         this.reportData = data.dashboardReports[0].reports[0];
         this.metrics = this.reportData.response.reportConfiguration.configuration.properties;
         this.dataArrayRes = this.reportData.response.reports;
         for(let data of this.dataArrayRes){
            let value: any = 0;
            for(let header of this.metrics){
              value=0;
              if(data.data[header.refrenceField] == null || data.data[header.refrenceField] == undefined || data.data[header.refrenceField] == ''){
                data.data[header.refrenceField]=value;
              }
            }
            this.dataArray.push(data);

         }
         
    });
    //this.dataArray = [];
   //this.drawHeatMap();
  }

  getErrorListingHeatMapReports(){
    let request:any;
    if(this.userDetails.id.toString().length!=9){
      request={
        reportType: "ErrorListingHeatMap",
        dashboardId: "0EDvbL",
        level: 2,
        requestParam: {
          filter: {
            fields: [
              {
                value: this.userDetails.carrierName
              }
            ]
          },
         //intervals: "2017-02-23T10:00:00.000Z/2017-02-23T10:14:59.000Z"
         intervals: this.startDate.toISOString() + "/" + this.endDate.toISOString()
        }
      }
    }else{
      request={
        reportType: 'ErrorListingHeatMap',
        dashboardId: '0EDvbL',
        level:1,
        requestParam:{ intervals: this.startDate.toISOString() + "/" + this.endDate.toISOString()  }
      }
    }

    this.spinner.show();
     this.heatMapService.getHeatMapReport(request).subscribe(data => {
          this.spinner.hide();
          this.isErrorListing=true;
        //JSON.stringify("--ErrorListingHeatMap--"+data);
         this.errorReportData = data.dashboardReports[0].reports[0];
         this.errorMetrics = this.errorReportData.response.reportConfiguration.configuration.properties;
         let list=this.errorMetrics.filter(property => property.name !== "TotalMSU");
         this.errorMetrics=list;
         this.errorDataArrayRes = this.errorReportData.response.reports;
         for(let data of this.errorDataArrayRes){
            let value: any = 0;
            for(let header of this.errorMetrics){
              
              value=0;
              if(data.data[header.refrenceField] == null || data.data[header.refrenceField] == undefined || data.data[header.refrenceField] == ''){
                data.data[header.refrenceField]=value;
              }
            }
            this.errorDataArray.push(data);
         }
    });
  }
  drawTop10SuccessRateHeatMap(){
    if (this.reportData != undefined && this.reportData != null)
     {
      this.metrics = this.reportData.response.reportConfiguration.configuration.properties;
      //this.legends = this.reportData.response.reportConfiguration.configuration.legends;
      this.dataArray = this.reportData.response.reports;
    }
    
  }
  getTop10SuccessRateData(){
    let 
        request = {
        reportType: 'Top10SuccessRate',
        dashboardId: '0EDvbL',
        level:2,
        requestParam:{
        //intervals: "2017-02-23T10:00:00.000Z/2017-02-23T10:14:59.000Z",
        intervals: this.startDate.toISOString() + "/" + this.endDate.toISOString(),
        filter: { fields: [{ value: this.userDetails.carrierName }] },
        }
    }
  this.spinner.show();
    this.heatMapService.getHeatMapReport(request).subscribe(data => {
         this.successReportData = data.dashboardReports[0].reports[0];
         this.successMetrics = this.successReportData.response.reportConfiguration.configuration.properties;
         this.successDataArrayRes = this.successReportData.response.reports;
         for(let data of this.successDataArrayRes){
            let value: any = 0;
            for(let header of this.successMetrics){
              value=0;
              if(data.data[header.refrenceField] == null || data.data[header.refrenceField] == undefined || data.data[header.refrenceField] == ''){
                data.data[header.refrenceField]=value;
              }
            }
            this.successDataArray.push(data);
         }
    });
  }
 drawErrorListingHeatMap() {
    if (this.errorReportData != undefined && this.errorReportData != null) {
      this.errorMetrics = this.errorReportData.response.reportConfiguration.configuration.properties;
     let list=this.errorMetrics.filter(property => property.name !== "TotalMSU");
     this.errorMetrics=list;
      //this.legends = this.reportData.response.reportConfiguration.configuration.legends;
      this.errorDataArray = this.errorReportData.response.reports;
    }
  }


  drawHeatMap() {
    if (this.reportData != undefined && this.reportData != null) {
      this.metrics = this.reportData.response.reportConfiguration.configuration.properties;
      //this.legends = this.reportData.response.reportConfiguration.configuration.legends;
      this.dataArray = this.reportData.response.reports;
    }
  }

  getClass(metricvalue) {
    return "success";
    // if (metricvalue >= 0 && metricvalue <= 40) {
    //   return "success";
    // }
    // else if (metricvalue > 40  && metricvalue <= 70) {
    //   return "warning";
    // }
    // else if (metricvalue > 70 && metricvalue <= 100) {
    //   return "danger";
    // }
    // return "danger";
  }


  getReport_nav(report , dashboard) {
    
     if(report.reportType=="HeatMapForTop10MSU"){
         console.log("Heat Map Report");
         this.router.navigateByUrl("/roamconnect/heatmap")
 
     }else{
           console.log("-------report----",report);
           
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
 
}
