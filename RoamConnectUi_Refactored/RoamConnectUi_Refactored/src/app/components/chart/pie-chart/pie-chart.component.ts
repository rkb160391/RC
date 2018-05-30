import { Component, ElementRef, AfterViewInit, OnDestroy, ViewChild, Input, OnInit, Renderer } from '@angular/core';
import { DataLoadSpinner } from '../../../services/http/common/dataloadspinner.service';
import sampleResponse from '../pie-chart/sampleResponse';

const Highcharts = require('highcharts/highcharts.src');
declare var require:any;
declare var require:any;
@Component({
    selector: 'app-pie-chart',
    templateUrl: './pie-chart.component.html',
    styleUrls: ['./pie-chart.component.css']
})

export class PieChartComponent implements OnInit {
    @Input() typeChart: boolean = false;
    @Input() pieCharts: any;
    @Input() showfullScreen: boolean;


    @ViewChild('chart') public chartEl: ElementRef;
    @ViewChild('largechart') public chartE2: ElementRef;

    toggleGraphView:boolean=true;
    private _chart: any;
    private _largechart: any;

    constructor(private renderer: Renderer, private elm: ElementRef, private _spinner: DataLoadSpinner) {
    }

    public ngOnInit() {
     // console.log("pie data>>>>>>>"+ JSON.stringify(sampleResponse));
     //this.pieCharts=sampleResponse;
        if (this.pieCharts && !this.pieCharts[0]) {
          if (this.pieCharts.response != null) {
            this._chart = new Highcharts.Chart(this.chartEl.nativeElement, this.getData(this.pieCharts));
            // this._largechart = new HighStock.Chart(this.chartE2.nativeElement, this.getData(this.pieCharts));
            if (this.showfullScreen) {
              this._chart.setSize(1183, 350);
            } else {
              this._chart.setSize(565, 350);
            }
          }
        }
      }
    
    
      ngOnChanges(pieCharts) {
    
        if (this.pieCharts) {
          if (this.pieCharts[0]) {
    
    
            if (this.pieCharts[0].response != null) {
              this._chart = new Highcharts.Chart(this.chartEl.nativeElement, this.getData(this.pieCharts));
              // this._largechart = new HighStock.Chart(this.chartE2.nativeElement, this.getData(this.pieCharts[0]));
              if (this.showfullScreen) {
                this._chart.setSize(1183, 350);
              } else {
                this._chart.setSize(565, 350);
              }
            }
          }
        }
      }
    

    getData(pieGraphReports: any) {
   
    let responseResultData = pieGraphReports.response.reports[0].dataList[0];
    let reportName=pieGraphReports.response.reportConfiguration.configuration.name;
   // console.log("---- responseResultData ----"+JSON.stringify(responseResultData));
     let seriesDataToShow: Array<any>=[];
     let dataArray: Array<any>=[];
     let count=0;
     for(let outerKey in responseResultData){
      //console.log("---- outerKey ----"+outerKey);
      var localTest={};
      if(responseResultData[outerKey]!=undefined){
        for(let innerKey in responseResultData[outerKey]){
         // console.log("---- innerKey ----"+innerKey);
         // console.log("---- responseResultData[outerKey] ----"+responseResultData[outerKey]);
          localTest[innerKey]=responseResultData[outerKey][innerKey];
         // console.log("---- localTest[innerKey] ----"+localTest[innerKey]);
          let data={
            name: innerKey,
            colorByPoint: true,
            y: parseInt(localTest[innerKey])
            };
            dataArray.push(data); 
        }
        let outerData={
          name:'COUNT',
          data:dataArray,
          pointWidth: 22
        }
        seriesDataToShow.push(outerData);
        dataArray=[];
      }
     }
    //-----------------------------------------Security----------------------------------------------
    // let seriesDataToShow: Array<any>=[];
    // let dataArray: Array<any>=[];
    // let count=0;
    // for(let outerKey in responseResultData.dataList){
    //  // console.log("---- responseResultData ----"+JSON.stringify(responseResultData.dataList[count])); 
    //      for(let innerKey in responseResultData.dataList[outerKey].Operation_Type){
    //           var localTest={};
    //           localTest[innerKey]=responseResultData.dataList[outerKey].Operation_Type[innerKey];
    //           let data={
    //           name: innerKey,
    //           colorByPoint: true,
    //           y: parseInt(localTest[innerKey])
    //           };
    //           dataArray.push(data);  
    //      }  
    //       let outerData={
    //         name:'Count',
    //         data:dataArray,
    //         pointWidth: 22
    //       }
    //      seriesDataToShow.push(outerData);
    //      dataArray=[];
    //     //  }
    // }
   //------------------------------------------------------------------------------------------------------

      
    
        let opt: any =
          {
            chart: {
              type: 'pie',
              height: 600,
              options3d: {
                enabled: true,
                alpha: 60
              }
    
            },
            credits: {
              enabled: false
            },
            colors: ['#058DC7', '#ED561B', '#C70620', '#AB2EAB', '#50B432', '#540A02', '#3030B8', '#5E5708', '#38057A'],
            exporting: {
              enabled: true
            },
            tooltip: {
              //pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
             series: {
                   pointPadding: 0,
                   groupPadding: 5,
                 },
              pie: {
                // innerSize: 150,
                gapSize:5,
                size: '80%',
                allowPointSelect: true,
                showInLegend: true,
                cursor: 'pointer',
                depth: 45,
                overflow: 'justify',
                minSize: null,
                align: 'right',
                dataLabels: {
                  enabled: true,
                  format: '{point.percentage:.1f} %',
                  style: {
                    color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                  },
                  connectorColor: 'silver'
                }
              }
            },
    
            title: {
              text: '',
              style: {
                fontSize: '20px',
                fontFamily: "Tahoma"
              }
            },
    
            legend: {
              enabled: true,
              useHTML: true,
              align: 'right',
              backgroundColor: 'White',
              layout: 'vertical',
              verticalAlign: 'top',
              y: 50,
              x: 10,
              //title : {
              //	text : 'Select Metrics'
              //},
              // labelFormatter: function () {
              //   return '<span class="abc" data-index="' + this.index + '">' + propertyList[1].name + '</span>';
              // }
            },
           // series: [{name:'Count',data: dataArray}]
            series : seriesDataToShow
          }
        return opt;
      }

    ngAfterViewInit() {
        // Component views have been Initialized

        this._spinner.hide();
    }



}
