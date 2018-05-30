import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { ReportComponent } from './components/report/report.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { HeatmapComponent } from './components/chart/heatmap/heatmap.component';
import { LinechartComponent } from './components/chart/linechart/linechart.component';
import { InventoryComponent} from './components/inventory/inventory.component';
import { LogoutComponent } from './components/logout/logout.component';



export const routes: Routes = [
 {
    path: 'roamconnect',
    children: [
      { path: '', component: DashboardComponent },
      { path: 'report', component: ReportComponent },
      { path: 'report/:reportType/:dashboardId/:level', component: ReportComponent },  
      { path: 'heatmap', component: HeatmapComponent },
      { path: 'linechart', component: LinechartComponent },
      { path: 'inventory', component: InventoryComponent},
      { path: 'logout', component:LogoutComponent },
    ]
    
  },
];

export const appRoutingProviders: any[] = [];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);

