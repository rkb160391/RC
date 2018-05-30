import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Routes, RouterModule, Router, Params } from '@angular/router';
import { Http, Headers, RequestOptions, Response, RequestMethod, Request} from '@angular/http';
import { DashboardService } from '../../services/http/dashboard/dashboard.service';
import { UserdataService } from '../../services/common/user/userdata.service';
import { ReportService } from '../../services/http/report/report.service';
import { Observable } from 'rxjs';

//import { setInterval, clearInterval } from 'timers';
declare var $: any;
@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.css']
})
export class InventoryComponent implements OnInit
 {
  actualPath:any;
  public file: File;
  public url: string;
  headers: Headers;
  fileList:any;
  data:any;
  isSuccess:boolean=false;

  dashboard: any;
  selected:any;
  operatrId:any;
  operatorName:any;
  userDetails:any;
  isBTUser:boolean=false;

  public hasBaseDropZoneOver:boolean = false;
  public hasAnotherDropZoneOver:boolean = false;

  
  
  constructor(private http: Http,public dashboardService:DashboardService,private router: Router, private userdataService: UserdataService) {
    console.log('file upload Initialized');
    this.dashboard=dashboardService.getDashboard();
    console.log();
    this.operatorName=this.userdataService.getUserDetails().carrierName;
    
    
     console.log("dashboard:"+this.dashboard);
    //set the header as multipart        
    this.headers = new Headers();
    this.headers.set('Content-Type', 'multipart/form-data');
    this.url = 'http://172.31.73.60:8080/ngoss/api/inventory/upload';



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

public fileOverBase(e:any):void {
  this.hasBaseDropZoneOver = e;
}

public fileOverAnother(e:any):void {
  this.hasAnotherDropZoneOver = e;
}

    ngOnInit()
    {

    }
   

     fileChange(e)
     {
      this.fileList = e.target.files;
    }

    uploadFile(){

      var fileIn = $("#fileInput")[0];
      if (fileIn.files === undefined || fileIn.files.length == 0)
       {
         this.isSuccess=false;
        //alert("Please select a file");
        $('#labelFile').text("Error:Please Select File");
        $('#labelFile').css("color","red");
        return;
       }


      if(this.fileList.length>0)
      {
        let file: File = this.fileList[0];

        $('#labelFile').text("File:"+file.name);
        $('#labelFile').css("color","black");
        
        let formData:FormData = new FormData();
  
        formData.append('uploadFile', file, file.name);
        //formData.append('info',this.model);
  
        console.log(file.size);
  
        let headers = new Headers();
        headers.set('Accept', 'application/json');
       // headers.set('Content-Type', 'multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW');
        headers.set('Access-Control-Allow-Origin', '*');
        headers.set("Authorization", "Negotiate");
        headers.append("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        let options = new RequestOptions({ headers: headers });
        this.http.post(this.url,formData, options)
          /*.map((res: Response) => res.json())*/
          .catch(error => Observable.throw(error))
          .subscribe(
            data =>{
              this.data = data._body;
              this.isSuccess=true;
              this.moveProgress();
              console.log(JSON.stringify(this.data));
            }
            ,
            error => console.log(error)
          )
      }
    }
  postFile(inputValue: any): void 
  {
    
        // var formData = new FormData();
        // formData.append("name", inputValue.files[0].name);
        // formData.append("file",  inputValue.files[0]);
        // console.dir(inputValue.files[0]); 
        let postheaders = new Headers({ 'Content-Type': '', 'Access-Control-Allow-Origin': '*' });
        let options = new RequestOptions({ headers: postheaders });
        let url=this.url+"?"+"name"+"="+inputValue.files[0].name+"&"+"file"+"="+inputValue.files[0];
        //this.http.post(url , {headers: this.headers });
      //  console.dir(url);
        let an:any =this.http.post(url,options).map(res => res);
        console.dir(an);
            
    }

    

      moveProgress()
     {
      // alert("Entered Into move");
       var progress=document.getElementById("myProgress");
      
       var width=1;
       var id=setInterval(frame,15);
       
       function frame()
      {
          
         if(width >=100)
         {
           clearInterval(this.id);
         }
          else
          {
           width++;
           console.log("width is"+width);
          //document.getElementById('myBar').style.width = width;
          $("#myBar").css("width",width+"%");
         }
        }
      }

      loadLogout()
      {
        this.router.navigateByUrl("/roamconnect/logout");
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





    getDashboards() {
      this.router.navigateByUrl('/roamconnect?operatorId=' + this.userdataService.getOperatorId());
    }

}
