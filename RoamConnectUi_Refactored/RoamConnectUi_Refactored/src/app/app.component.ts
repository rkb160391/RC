import { Component } from '@angular/core';
import { ActivatedRoute, Routes, RouterModule, Router, Params } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  enitySpecId:any;
   constructor(private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
      
  }
 
 }
