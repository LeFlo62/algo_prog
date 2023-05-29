import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ListService } from './services/list.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
  days = [{'name': '1 Day', value: 1},
          {'name': '2 Days', value: 2},
          {'name': '3 Days', value: 3},
          {'name': '4 Days', value: 4},
          {'name': '5 Days', value: 5},
          {'name': '6 Days', value: 6},
          {'name': '7 Days', value: 7},
          {'name': '8 Days', value: 8},
          {'name': '9 Days', value: 9},
          {'name': '10 Days', value: 10},
          {'name': '11 Days', value: 11},
          {'name': '12 Days', value: 12},
          {'name': '13 Days', value: 13},
          {'name': '14 Days', value: 14}];

  transports = [{'name': 'Car', value: 20},
               {'name': 'Subway', value: 18},
               {'name': 'Foot', value: 5},
               {'name': 'Bike', value: 14}];

  timeSpent = [{'name': '1 Hour', value: 1},
               {'name': '2 Hours', value: 2},
               {'name': '3 Hours', value: 3},
               {'name': '4 Hours', value: 4},
               {'name': '5 Hours', value: 5}];

  artworks : string[] = [];

  form : FormGroup = new FormGroup({
    days : new FormControl(''),
    transports : new FormControl(''),
    timeSpent : new FormControl(''),
    artworks : new FormControl('')
  });

  constructor(private listService: ListService) {
    this.listService.getArtworks().subscribe((data : string[]) => {
      this.artworks = data;
    });
  }

}
