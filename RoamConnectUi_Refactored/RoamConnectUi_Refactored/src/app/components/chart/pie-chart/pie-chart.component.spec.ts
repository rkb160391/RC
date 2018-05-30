// import { async, ComponentFixture, TestBed } from '@angular/core/testing';

// import { PieChartComponent } from './pie-chart.component';

// describe('PieChartComponent', () => {
//   let component: PieChartComponent;
//   let fixture: ComponentFixture<PieChartComponent>;

//   beforeEach(async(() => {
//     TestBed.configureTestingModule({
//       declarations: [ PieChartComponent ]
//     })
//     .compileComponents();
//   }));

//   beforeEach(() => {
//     fixture = TestBed.createComponent(PieChartComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//   it('should be created', () => {
//     expect(component).toBeTruthy();
//   });
// });import { async, ComponentFixture, TestBed } from '@angular/core/testing';

// import { PieChartComponent } from './pie-chart.component';

// describe('PieChartComponent', () => {
//   let component: PieChartComponent;
//   let fixture: ComponentFixture<PieChartComponent>;

//   beforeEach(async(() => {
//     TestBed.configureTestingModule({
//       declarations: [ PieChartComponent ]
//     })
//     .compileComponents();
//   }));

//   beforeEach(() => {
//     fixture = TestBed.createComponent(PieChartComponent);
//     component = fixture.componentInstance;
//     fixture.detectChanges();
//   });

//   it('should be created', () => {
//     expect(component).toBeTruthy();
//   });
// });

/* tslint:disable:no-unused-variable */
import { Component, ElementRef, AfterViewInit, OnDestroy, ViewChild, Input, OnInit, Renderer } from '@angular/core';
import { TestBed, async ,ComponentFixture} from '@angular/core/testing';
import { PieChartComponent } from './pie-chart.component';

describe('PieChartComponent', () => {

  let component: PieChartComponent;
  let fixture: ComponentFixture<PieChartComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PieChartComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PieChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
