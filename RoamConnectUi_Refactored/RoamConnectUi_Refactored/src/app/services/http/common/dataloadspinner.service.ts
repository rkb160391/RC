import {Injectable} from '@angular/core';
declare var $:any;

@Injectable()
export class DataLoadSpinner {

  private _selector:string = 'preloader';
  private _errorSelector : string ="toast-container";
  private _element:HTMLElement;
  public error_message: any;

  constructor() {
    this._element = document.getElementById(this._selector);
  }

  public show():void {
   $("#"+this._selector).addClass("show").removeClass("hide");
  }

  public hide():void {

     $("#"+this._selector).removeClass("show");
        $("#"+this._selector).addClass("hide");

  }

  public showErrorMessage(message):void{
  (message!=undefined && typeof message=='string' && message.match('user already exists')) ?  this.error_message = "User already exists" : this.error_message='Something went wrong !!!';
    $("#"+this._errorSelector).addClass("show");
    $("#"+this._errorSelector).removeClass("hide");
$("#"+this._errorSelector).children().addClass("toast-error");
    $("#errorMessage").text(this.error_message);

    setTimeout(()=>{
    $("#"+this._errorSelector).removeClass("show");
        $("#"+this._errorSelector).addClass("hide");
        $("#"+this._errorSelector).children('div').removeClass("toast-error");
        $("#errorMessage").text("");
    },2000);

    this.hide();

  }

  showSuccessMessage(message){
    $("#"+this._errorSelector).addClass("show");
    $("#"+this._errorSelector).removeClass("hide");
$("#"+this._errorSelector).children('div').addClass("toast-success");
    $("#successMessage").text(message);
    setTimeout(()=>{
    $("#"+this._errorSelector).removeClass("show");
        $("#"+this._errorSelector).addClass("hide");
          $("#"+this._errorSelector).children('div').removeClass("toast-success");
            $("#successMessage").text("");
    },4000);

  }
}
