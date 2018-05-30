import { Injectable } from '@angular/core';

@Injectable()
export class UserdataService {
  public operatorId: any;
  public dashboardList: Array<any> = [];
  public userDetails: any;
  constructor() { }

  setOperatorId(operatorId) {
    this.operatorId = operatorId;
  }

  getOperatorId() {
    return this.operatorId;
  }
  setUserDetails(userDetails){
     this.userDetails=userDetails;
  }
  getUserDetails(){
    return this.userDetails;
  }

  setDashboardList(dashboardList)
  {
  this.dashboardList = dashboardList;
  }

  getDashboardList()
  {
  return this.dashboardList;
  }

  isBtUser(id:any){
    if(id.toString().length===9){
     return true;
    }else{
      return false;
    }
  }
}
