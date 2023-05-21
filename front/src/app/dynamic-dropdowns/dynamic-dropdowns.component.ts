import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

interface Dropdown {
  label: string;
  options: string[];
}

interface SelectedOptions {
  [key: string]: any;
}

@Component({
  selector: 'app-dynamic-dropdowns',
  templateUrl: './dynamic-dropdowns.component.html',
  styleUrls: ['./dynamic-dropdowns.component.scss']
})
export class DynamicDropdownsComponent implements OnInit {

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  dropdowns = [
    {
      label: 'City',
      options: ['Bordeaux', 'Marseille', 'Lyon', 'Lille', 'Paris'],
      placeholder: 'Select a city'
    },
    {
      label: 'Days',
      options: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'],
      placeholder: 'Select the number of days of your trip'
    },
    {
      label: 'Type',
      options: ['Monument', 'Nature', 'Museum'],
      placeholder: 'Select the type of things you want to visit'
    }
  ];

  selectedOptions: SelectedOptions = {};

  onChange(event: Event, i: number) {
    this.selectedOptions[this.dropdowns[i].label] = (event.target as HTMLSelectElement).value;
  }

  getOwnPropertyNames(obj: {}) {
    return Object.getOwnPropertyNames(obj);
  };

  constructor(private router: Router) { }

  onClick() {
    this.router.navigate(["/dynamic-dropdowns.component.ts"]);
  }


}

