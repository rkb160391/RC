import { Component, ElementRef, AfterViewInit, OnDestroy, ViewChild, Input, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import 'rxjs/add/operator/toPromise';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { UserdataService } from '../../../services/common/user/userdata.service';

import sampledata from './sampleData';

declare var $: any;
declare var require: any;
const Highstock = require('highcharts/highcharts.src');

@Component({
    selector: 'app-linechart',
    templateUrl: './linechart.component.html',
    styleUrls: ['./linechart.component.css']
})
export class LinechartComponent implements OnInit {
    @Input()
    reportData: any;
    timestampSeries: Set<string> = new Set();
    dataMap: Map<any, any[]> = new Map();
    operatorName:any;

    @ViewChild('chart') public chartEl: ElementRef;
    private _chart: any;
    constructor(private userdataService: UserdataService) { 
       this.operatorName=this.userdataService.getOperatorName();
    }

    ngOnInit() {
       // this.reportData = sampledata.dashboardReports[0].reports[0];
       // console.log("Reports: "+this.reportData)
        this._chart = new Highstock.chart(this.chartEl.nativeElement, this.getChartChata(this.reportData));
    }
    getChartChata(reportData) {
        let seriesData: Array<any> = [];
        let value = 0;
        let xTitle:any;
        for (let response of reportData.response.reports) {
            console.log("Reports++: "+response);
            this.timestampSeries.add(response.timeStamp);
            //xTitle=response.operatorName;
            xTitle=this.operatorName;
            for (let property of reportData.response.reportConfiguration.configuration.properties) {
                value = 0;
                if (property.type == null || property.type == undefined || property.type == '') {

                    if (response[property.refrenceField] == null || response[property.refrenceField] == undefined || response[property.refrenceField] == '') {
                        if (response.data[property.refrenceField] != null || response.data[property.refrenceField] != undefined) {
                            value = response.data[property.refrenceField];

                        }
                        else {
                            value = 0;
                        }
                    }
                    else {
                        value = response[property.refrenceField];
                    }
                }
                else {
                    let type = property.type;
                    let arr: Array<any> = type.split("+");
                    for (let element of arr) {
                        let d = response.data[element];
                        if (d != undefined) {
                            value = value + response.data[element];
                        }

                    }
                }
                if (this.dataMap.get(property.name) == null) {
                    let mapValue = [];
                    let obj:any={y:{},x:{}};
                    obj["x"] = (new Date(response.timeStamp)).getTime();
                    obj["y"] = value;
                    mapValue.push(obj);
                    this.dataMap.set(property.name, mapValue);
                }
                else {
                    let mapValue = this.dataMap.get(property.name);
                    let obj:any={y:{},x:{}};
                    obj["x"] = (new Date(response.timeStamp)).getTime();
                    obj["y"] = value;
                    mapValue.push(obj);
                    this.dataMap.set(property.name, mapValue);
                }
            }
        }

        this.dataMap.forEach((value: [any[]], key: String) => {
            let data = {
                "name": key,
                "data": value,
                "marker": {
                    "symbol": 'square'
                },
            }
            seriesData.push(data);
            console.log("Series: "+JSON.stringify(seriesData));
        });
 
       
        let opt: any = {
            chart: {
                type: 'line'
            },
            title: {
                text: reportData.response.reportConfiguration.configuration.name,
                x: -20 //center
            },
           xAxis: {
                //categories: Array.from(timestampSeries),
                title: {
                    text: xTitle
                },
            type: 'datetime',
            labels:{
                     formatter:function(){
                     return  Highstock.dateFormat('%e-%b %H:%M:%S',new Date(this.value))
                  }
            }
            },
            yAxis: {
                labels: {
                   x: 1,
               },
                title: {
                    text: "Inbound/Outbound Count"
                },
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    //enableMouseTracking: false
                }
            },
            tooltip: {
                    // crosshairs: true,
                    shared: true
    
                },
            series: seriesData
            // title: {
            //     text: reportData.response.reportConfiguration.configuration.name,
            //     x: -20 //center
            // },
            // // chart: {
            // //     zoomType: 'xy',
            // //     panning: true,
            // //     panKey: 'shift',
            // //     type: 'line'
            // // },
            // chart: {
            //     type: 'line'
            // },
            // xAxis: {
            //     //categories: Array.from(timestampSeries),
            //     title: {
            //         text: xTitle
            //     },
            // type: 'datetime',
            // labels:{
            //              formatter:function(){
            //                 return  Highstock.dateFormat('%e-%b %H:%M:%S',new Date(this.value))
            //              }
            // }
            // },
            // yAxis: {
            //     labels: {
            //        x: 1,
            //    },
            //     title: {
            //         text: "Inbound/Outbound Count"
            //     },
            // },
            // tooltip: {
            //     // crosshairs: true,
            //     shared: true

            // },
            // // plotOptions: {
            // //     line: {
            // //         dataLabels: {
            // //             enabled: true
            // //         }
            // //     }
            // // },
            // legend: {
            //     layout: 'vertical',
            //     align: 'right',
            //     verticalAlign: 'middle'
            // },
        
            // plotOptions: {
            //     line: {
            //         dataLabels: {
            //             enabled: true
            //         }
            //         // enableMouseTracking: false
            //     },
            //     series: {
            //         label: {
            //             connectorAllowed: false
            //         },
            //         pointStart: 2010
            //     }
            // },
    
            // series: seriesData
        };
        return opt;
    }
}
