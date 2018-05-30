import { Component, OnInit, AfterViewInit, Input, ElementRef, SimpleChanges } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import sampledata from './sampleTableData';
import samplePiedata from '../chart/pie-chart/sampleResponse';
import { ReportService } from '../../services/http/report/report.service';
import { DataLoadSpinner } from '../../services/http/common/dataloadspinner.service';
import { UserdataService } from '../../services/common/user/userdata.service';
import { forEach } from '@angular/router/src/utils/collection';

declare var $: any;

@Component({
  selector: 'app-data-table',
  templateUrl: './data-table.component.html',
  styleUrls: ['./data-table.component.css']
})
export class DataTableComponent implements OnInit {
  @Input() showfullScreenTable: boolean;
  @Input() toggleTableView: boolean = false;
  @Input() reportdata: any;
  @Input()
  tableData: any;
  status:any;
  columnsArray: Array<any> = [];
  dataArray: Array<any> = [];
  mainHeaderArray: Array<any> = [];
  reportId: any;
  dashboardId: any;
  filterValue: any = "";
  reportName: any;
  reportType: string = "table";
  actualReportType: any;
  reportSubType: string = "table";
  displayMainHeaders: boolean = false;
  displayTable: boolean = true;
  displayPieChart: boolean = false;
  displayLineGraph: boolean = false;

  hourValue: any;
  minuteValue: any;
  selectedDate: any;
  displayFullDaySelection: boolean = false;
  currentRequest: any;
  startDate: any;
  endDate: any;
  hours: any = ["00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"];
  minutes: any = ["00", "15", "30", "45"];
  isCheckedFullDay: boolean = false;
  currentFullDate: any;
  displayDateTime: boolean = true;
  requestMap: Map<any, any> = new Map();
  ReprtNames: Array<any> = [];
  userDetails:any;
  isBTUser:boolean=false;
  constructor(private route: ActivatedRoute, private router: Router, private reportService: ReportService, private spinner: DataLoadSpinner, private userdataService: UserdataService) {
    this.userDetails=this.userdataService.getUserDetails();
    this.isCheckedFullDay = false;
    this.isBTUser=this.userdataService.isBtUser(this.userDetails.id);

  }

  ngOnInit() {

    this.route.params.subscribe(params => {
      let res = this.router.url.split('/');
      if (res != null) {
        //  this.reportId = res[3];
        this.actualReportType = res[3];
        this.dashboardId = res[4];

      }
      //res=null;
    });
    let startDate: Date = this.startDate;
    let endDate: Date = this.endDate;
    //reportId: this.reportId,
    let firstRequest =
      {
        reportType: this.actualReportType,
        dashboardId: this.dashboardId,
        name: this.tableData.response.reportConfiguration.configuration.name
      };
    this.ReprtNames = [];
    this.ReprtNames.push({
      name: firstRequest.name,
      request: firstRequest,
      index: 0
    });
    this.dataArray = [];
    this.mainHeaderArray = [];
    this.getTabledata();
    let date = new Date();
    this.selectedDate = date.getDate() + "-" + (date.getMonth() + 1) + "-" + date.getFullYear();
    this.hourValue = date.getHours();
    this.minuteValue = date.getMinutes();
    this.setstartAndEnddate();
    this.requestMap.set("firstLevel", firstRequest);

  }

  getTabledata() {

    this.reportName = this.tableData.response.reportConfiguration.configuration.name;
    let headers = this.tableData.response.reportConfiguration.configuration.legends.concat(this.tableData.response.reportConfiguration.configuration.properties);
    //console.log("Header :"+JSON.stringify(headers));
    if (this.tableData.response.reportConfiguration.level != 1) {
      let headersconfigData: any;
      if (this.tableData.response.reportConfiguration.level == 3) {
        this.displayDateTime = false;
        headersconfigData = this.tableData.response.reportConfiguration.configuration.properties;
        let request = this.requestMap.get("thirdLevel");
        request["name"] = "Raw Data Report For " + this.filterValue;
        this.requestMap.set("thirdLevel", request);
        let objIndex = this.ReprtNames.findIndex((obj => obj.index == 2));
        if (objIndex == -1) {
          this.ReprtNames.push({
            name: request.name,
            request: request,
            index: 2
          });
        }
        else {
          this.ReprtNames[objIndex].name = request.name;
          this.ReprtNames[objIndex].request = request;
        }
      }
      else {
        headersconfigData = this.tableData.response.reportConfiguration.configuration.legends.concat(this.tableData.response.reportConfiguration.configuration.properties);
        let request:any;
        if(this.isBTUser){
          request = this.requestMap.get("secondLevel");
          request["name"] = this.reportName + " For " + this.filterValue;
          this.requestMap.set("secondLevel", request);
        }
        
        let objIndex = this.ReprtNames.findIndex((obj => obj.index == 1));
        if (objIndex == -1) {
          if(this.isBTUser){
            this.ReprtNames.push({
              name: request.name,
              request: request,
              index: 1
            });
          }
        }
        else {
          if(this.isBTUser){
            this.ReprtNames[objIndex].name = request.name;
            this.ReprtNames[objIndex].request = request;
          }
        }
      }
      headers = [];
      for (let data of headersconfigData) {
        if (data.subHeaders === undefined || data.subHeaders == null || data.subHeaders.length <= 0) {
        } else {
          for (let header of data.subHeaders) {
            headers.push(header);
          }
        }
      }
    }
    if (this.tableData.response.reportConfiguration.level == 2) {
      this.displayMainHeaders = true;
      this.mainHeaderArray = this.tableData.response.reportConfiguration.configuration.legends.concat(this.tableData.response.reportConfiguration.configuration.properties);
      if(this.tableData.response.reportConfiguration.configuration.name == 'Error Listing Report' || this.tableData.response.reportConfiguration.configuration.name == 'Security Violation Report'){
        let pieHeader =
        {
          name: "PieChartLink",
          displayName: "",
          link: true,
          type: "PIECHART",
          level: this.tableData.response.reportConfiguration.level,
          subHeaders: [
          ]
        }
        
        this.mainHeaderArray.push(pieHeader);
      }
   }
    this.processTableData(headers);
  }
  processTableData(headers) {

    let graphHeader =
      {
        name: "GraphLink",
        displayName: "LineGraph Link",
        link: true,
        type: "lineGraph",
        level: this.tableData.response.reportConfiguration.level
      }
    let pieHeader =
      {
        name: "PieChartLink",
        displayName: "PieChart Link",
        link: true,
        type: "PIECHART",
        level: this.tableData.response.reportConfiguration.level,
        subHeaders: [
          {
            displayName: "PieChart Link"
          },
          {
            displayName: "PieChart Link"
          }
        ]
      }
    for (let response of this.tableData.response.reports) {
      let responseobj: any = {};
      let value: any = 0;
      for (let header of headers) {
        value = 0;
        if (header.type == null || header.type == undefined || header.type == '') {
          if (response[header.refrenceField] == null || response[header.refrenceField] == undefined || response[header.refrenceField] == '') {
            if (response.data[header.refrenceField] != null || response.data[header.refrenceField] != undefined) {
              value = response.data[header.refrenceField];
            }
            else {
              value = 0;
            }
          }
          else {
            value = response[header.refrenceField];
          }
        }
        else {
          let type = header.type;
          let arr: Array<any> = type.split("+");
          for (let element of arr) {
            let d = response.data[element];
            if (d != undefined) {
              value = value + response.data[element];
            }
          }
        }
        if (header.name != null && header.name.toLowerCase() == 'timestamp') {
          value = new Date(value).toLocaleString();
        }
        responseobj[header.name] = value;
      }
        responseobj["GraphLink"] = "Graph";
        responseobj["PieChartLink"] = "PieChart";
        this.dataArray.push(responseobj);
      
    }
    if (this.tableData.response.reportConfiguration.level == 1 && this.tableData.response.reportConfiguration.configuration.name != 'Billing Report') {
      headers.push(graphHeader);
      if (this.tableData.response.reportConfiguration.configuration.name == 'Error Listing Report') {
        pieHeader.type="ERRORPIECHART";
        headers.push(pieHeader);
      }
      
    } else if (this.tableData.response.reportConfiguration.level == 2) {
      if (this.tableData.response.reportConfiguration.configuration.name == 'Error Listing Report') {
        pieHeader.type="ERRORPIECHART";
        headers.push(pieHeader);
      }else if(this.tableData.response.reportConfiguration.configuration.name == 'Security Violation Report'){
        pieHeader.type="PIECHART";
        headers.push(pieHeader);
      }
    }
    if(headers.length > 20){
      headers.splice(headers.length-1,1);
    }
    this.columnsArray = headers;
  }
  getfilteredReport(value, headerobj, rowdata) {
    //console.log("-----value---"+JSON.stringify(this.userDetails));
    this.spinner.show();
    let request: any;

    if(value == 'PieChart' &&  this.actualReportType=='ErrorReport' && headerobj.level==1){
      let startdate: Date = this.startDate;
      startdate.setDate(this.startDate.getDate());
      request = {
        "dashboardId": this.dashboardId,
        "reportType": this.actualReportType,
        "level": headerobj.level,
        "requestParam": {
          "graphType": headerobj.type,
          "intervals": startdate.toISOString() + '/' + this.endDate.toISOString(),
          "filter": { "fields": [{ "value": rowdata.operatorName }] }
        }
      }
    }else if(value == 'Graph'){
      let startdate: Date = this.startDate;
      startdate.setDate(this.startDate.getDate());
      this.userdataService.setOperatorName(rowdata.operatorName);
      request = {
        "dashboardId": this.dashboardId,
        "reportType": this.actualReportType,
        "level": headerobj.level,
        "requestParam": {
          "graphType": headerobj.type,
          "intervals": this.getIntervalforfullday(),
          "filter": { "fields": [{ "value": rowdata.operatorName }] }
        }
      }
    }
    else if ( value == 'PieChart' || (headerobj.level == 3 && headerobj.name != null)) {
      let startdate: Date = this.startDate;
      startdate.setDate(this.startDate.getDate());
      if(headerobj.level == 3 && headerobj.name != null){
        if(this.isBTUser){
          request = {
            "dashboardId": this.dashboardId,
            "reportType": this.actualReportType,
            "level": headerobj.level,
            "requestParam": {
              "graphType": headerobj.type,
              "intervals": startdate.toISOString() + '/' + this.endDate.toISOString(),
              "filter": { "fields": [{ "value": this.filterValue },{ "value": rowdata.operatorName }] },
              "totalRecords": value
            }
          }
        }else{
          request = {
            "dashboardId": this.dashboardId,
            "reportType": this.actualReportType,
            "level": headerobj.level,
            "requestParam": {
              "graphType": headerobj.type,
              "intervals": startdate.toISOString() + '/' + this.endDate.toISOString(),
              "filter": { "fields": [{ "value": this.userDetails.carrierName },{ "value": rowdata.operatorName }] },
              "totalRecords": value
            }
          }
        }
      }else{
        if(this.isBTUser){
          request = {
            "dashboardId": this.dashboardId,
            "reportType": this.actualReportType,
            "level": headerobj.level,
            "requestParam": {
              "graphType": headerobj.type,
              "intervals": startdate.toISOString() + '/' + this.endDate.toISOString(),
              "filter": { "fields": [{ "value": this.filterValue },{ "value": rowdata.operatorName }] }
             
            }
          }
        }else{
          request = {
            "dashboardId": this.dashboardId,
            "reportType": this.actualReportType,
            "level": headerobj.level,
            "requestParam": {
              "graphType": headerobj.type,
              "intervals": startdate.toISOString() + '/' + this.endDate.toISOString(),
              "filter": { "fields": [{ "value": this.userDetails.carrierName },{ "value": rowdata.operatorName }] }
             
            }
          }
        }
      }
    
     
    }
    else {
      if (headerobj.level != 3) {
          this.filterValue = value;
      }
      request =
        {
          "dashboardId": this.dashboardId,
          "reportType": this.actualReportType,
          "level": headerobj.level,
          "requestParam": {
            "filter": { "fields": [{ "value": rowdata.operatorName }] },
            "intervals": this.startDate.toISOString() + '/' + this.endDate.toISOString()
          }
        };
      if (headerobj.level == 3) {
        this.ReprtNames.push({
          name: "RawData",
          request: Request,
          index: 2
        });
        request.requestParam["totalRecords"] = value;
        this.requestMap.set("thirdLevel", request);

      }
      else {
        this.requestMap.set("secondLevel", request);
      }
    }
    if (this.isCheckedFullDay) {
      request.requestParam["intervals"] = this.getIntervalforfullday();
      request.requestParam["granularity"] = "day";

    }
    this.currentRequest = null;   //To work navigation bar at 3rd level
    this.getFilteredDataReponse(request, true);

  }
  getFilteredDataReponse(request, editName) {
    let graphType = request.requestParam.graphType;
    this.reportService.getReport(request).subscribe(data => {
      this.tableData = [];
      this.dataArray = [];
      this.columnsArray = [];
      this.mainHeaderArray = [];
      this.tableData = data.dashboardReports[0].reports[0];
      this.reportType = data.dashboardReports[0].reports[0].response.reportConfiguration.configuration.type;
      this.reportSubType = data.dashboardReports[0].reports[0].response.reportConfiguration.configuration.subType;

      if (this.reportType.toLowerCase() == 'table') {
        if (editName) { this.reportName = this.reportName + " For " + this.filterValue; }
        // console.log("---Table---" + graphType);
       
        if (this.reportSubType.toLowerCase() == 'pie' && (graphType == 'PIECHART' || graphType == 'ERRORPIECHART' )) {
          this.displayTable = false;
          this.displayDateTime = false;
          this.ReprtNames.push({
            name: graphType
            //request: Request,
            //index: 2
          });
          this.reportdata = data.dashboardReports[0].reports[0];
          this.displayPieChart = true;
          //console.log("--- Pie---");
        } else if (graphType == 'lineGraph') {
         // console.log("---Graph And Pie---");
          this.displayPieChart = false;
          this.displayTable = false;
          this.displayDateTime = false;
          this.ReprtNames.push({
              name: graphType
              //request: Request,
              //index: 2
            });
            this.displayLineGraph = true;
          this.reportdata = data.dashboardReports[0].reports[0];
        }else{
          this.getTabledata();
           this.displayTable = true;
          this.displayDateTime = true;
          this.displayPieChart = false;
          this.displayLineGraph = false;
        }
      }
      else {
        //console.log("---else--");
      }
      this.spinner.hide();
    });
  }
  getDashboards() {
    this.router.navigateByUrl('/roamconnect?operatorId=' + this.userdataService.getOperatorId());
  }

  getFullDayValue(event: any) {
    this.isCheckedFullDay = event;
    this.getUpdatedData();
  }

  getDatepicker() {

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
        if (dateText != parent.currentFullDate) {
          parent.displayFullDaySelection = true;
        }
        else {
          parent.displayFullDaySelection = false;
          parent.isCheckedFullDay = false;
        }
        //parent.getUpdatedData();  
      }
    });

    let todayDate = `${currentDate}-${currentMonth}-${currentYear}`;
    $('#date').val(todayDate);

  }

  getUpdatedData() {
    this.spinner.show();
    let request: any;
    this.setstartAndEnddate();
    if (this.currentRequest != null) {
      request = this.currentRequest;
      if (request.requestParam != 'undefined' && request.requestParam != null) {
        if (request.requestParam.type != null && request.requestParam.type == 'graph') {

          request.requestParam["intervals"] = this.getIntervalforfullday();
        }
        else {
          request.requestParam["intervals"] = this.startDate.toISOString() + '/' + this.endDate.toISOString();
        }
      }
      else {
        request["requestParam"] = {
          "intervals": this.startDate.toISOString() + '/' + this.endDate.toISOString()
        }
      }
      if (this.isCheckedFullDay) {
        request.requestParam["intervals"] = this.getIntervalforfullday();
        request.requestParam["granularity"] = "day";

      }
      //console.log("Time 0:"+request.requestParam["intervals"]);
      this.getFilteredDataReponse(request, true);
    } else {
      // console.log("UserDetails : "+JSON.stringify(this.userDetails));
      if( this.isBTUser){
        request = {
          reportType: this.actualReportType,
          dashboardId: this.dashboardId,
          level: 1,
          requestParam: {
            intervals: this.startDate.toISOString() + '/' + this.endDate.toISOString()
          }
        }
      }else{
        request = {
          reportType: this.actualReportType,
          dashboardId: this.dashboardId,
          level: 2,
          requestParam: {
            filter: { fields: [{ value: this.userDetails.carrierName }] },
            intervals: this.startDate.toISOString() + '/' + this.endDate.toISOString()
          }
        }
      }
     
      if (this.isCheckedFullDay) {
        request.requestParam["intervals"] = this.getIntervalforfullday();
        request.requestParam["granularity"] = "day";
        console.log("Time isChecked:"+request.requestParam["intervals"]);
      }
      //console.log("Time 1:"+request.requestParam["intervals"]);
      this.getFilteredDataReponse(request, false);
    }
  }

  setstartAndEnddate() {

    let datearray = this.selectedDate.split("-");
    let enddate: Date = new Date();
    enddate.setDate(datearray[0]);
    enddate.setMonth((datearray[1] - 1));
    enddate.setFullYear(datearray[2]);
    enddate.setHours(this.hourValue);
    enddate.setMinutes(this.minuteValue);

    let startdate = new Date();
    startdate.setDate(datearray[0]);
    startdate.setMonth((datearray[1] - 1));
    startdate.setFullYear(datearray[2]);
    startdate.setHours(this.hourValue);
    //alert("Date in dataTable"+startdate);

    let request: any;
    startdate.setMinutes(enddate.getMinutes() - 15);
    startdate.setSeconds(0);
    startdate.setMilliseconds(0);
    enddate.setSeconds(59);
    this.startDate = startdate;
    enddate.setMinutes(this.minuteValue-1);
    enddate.setMilliseconds(0);
    this.endDate = enddate;

    let objIndex = this.ReprtNames.findIndex((obj => obj.index == 0));
    if (objIndex != -1) {
      let request = {
        reportType: this.actualReportType,
        dashboardId: this.dashboardId,
        name: this.tableData.response.reportConfiguration.configuration.name,
        level: "1",
        requestParam: {
          intervals: this.startDate.toISOString() + "/" + this.endDate.toISOString()
        }
      };
      this.ReprtNames[objIndex].name = request.name;
      this.ReprtNames[objIndex].request = request;

    }

  }

  getIntervalforfullday() {
    let startdate: Date = this.startDate;
    
    //let startdatevalue = startdate.toISOString().split("T")[0];
    
    let enddate: Date = this.endDate;
    enddate.setDate(startdate.getDate());
    enddate.setHours(23+5);
    enddate.setMinutes(59+30);
    enddate.setSeconds(59);
    enddate.setMilliseconds(0);

    startdate.setHours(5);
    startdate.setMinutes(30);
    startdate.setSeconds(0);
    startdate.setMilliseconds(0);
    //console.log("Start Date :"+startdate +" -------End Date: "+enddate);
    //console.log("Date :"+startdate.toISOString() + '/' + enddate.toISOString());
    //let enddatevalue = enddate.toISOString().split("T")[0];
    return startdate.toISOString() + '/' + enddate.toISOString();
  }

    getReport(request) {
      this.spinner.show();
     this.getFilteredDataReponse(request, false);
      console.log("this.ReprtNames"+this.ReprtNames.length);
      this.ReprtNames.splice(request.level,this.ReprtNames.length);
   }

  ngOnChanges(changes: SimpleChanges) {
    this.displayPieChart = false;
    this.displayLineGraph = false;
    console.log(changes.tableData);
    if (changes.tableData.previousValue && (changes.tableData.currentValue.reportType != changes.tableData.previousValue.reportType)) {
      this.ngOnInit();
    }


  }

}
