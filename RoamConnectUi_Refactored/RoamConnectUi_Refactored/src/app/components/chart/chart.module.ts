import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeatmapComponent } from './heatmap/heatmap.component';
import { LinechartComponent } from './linechart/linechart.component';
import { PieChartComponent } from './pie-chart/pie-chart.component';
//import { DataTableComponent } from '../data-table/data-table.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [HeatmapComponent, LinechartComponent,PieChartComponent],
  exports:[HeatmapComponent, LinechartComponent,PieChartComponent]

})
export class ChartModule { }
