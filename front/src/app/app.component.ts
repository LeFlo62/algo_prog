import { Component } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ListService } from './services/list.service';


import * as Leaflet from 'leaflet';
import { NodeService } from './services/node.service';
import { Node } from './data/node';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  
  createIcon(color : string) : Leaflet.Icon {
    return new Leaflet.Icon({
      iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-' + color + '.png',
      shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
      iconSize: [25, 41],
      iconAnchor: [12, 41],
      popupAnchor: [1, -34],
      shadowSize: [41, 41]
    });
  }

  icons : Leaflet.Icon[] = [
    this.createIcon('red'),
    this.createIcon('blue'),
    this.createIcon('green'),
    this.createIcon('orange'),
    this.createIcon('yellow'),
    this.createIcon('violet'),
    this.createIcon('grey'),
    this.createIcon('black'),
    this.createIcon('gold')
  ];


  layers : Leaflet.Layer[] = [
    new Leaflet.TileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; OpenStreetMap contributors'
    } as Leaflet.TileLayerOptions),
  ];

  options: Leaflet.MapOptions = {
    layers: this.layers,
    zoom: 12,
    center: new Leaflet.LatLng(48.85341, 2.3488)
  };

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

  transports = [{'name': '🚙 Car', value: 20},
               {'name': '🚋 Subway', value: 18},
               {'name': '🚶 Foot', value: 5},
               {'name': '🚲 Bike', value: 14}];

  timeSpent = [{'name': '1 Hour', value: 1},
               {'name': '2 Hours', value: 2},
               {'name': '3 Hours', value: 3},
               {'name': '4 Hours', value: 4},
               {'name': '5 Hours', value: 5}];

  types : string[] = [];

  form : FormGroup = new FormGroup({
    days : new FormControl(''),
    transports : new FormControl(''),
    timeSpent : new FormControl(''),
    type : new FormControl(''),
    dayRange : new FormControl([9, 19])
  });

  received : boolean = false;

  constructor(private listService: ListService, private nodeService: NodeService) {
    this.listService.getTypes().subscribe((data : string[]) => {
      this.types = data;
    });
  }

  calculate() {
      let days = this.form.value.days.value;
      let transports = this.form.value.transports.value;
      let timeSpent = this.form.value.timeSpent.value;
      let type = this.form.value.type;
      let dayRange = this.form.value.dayRange;

      this.received = false;
      this.nodeService.getPath(days, transports, dayRange[0], dayRange[1], timeSpent, type).subscribe((data : Node[]) => {
        this.received = true;
        this.resetLayers();
        let markers : Leaflet.Marker[] = [];
        let lines : Leaflet.Polyline[] = [];
        let i = 0;
        for (let node of data) {
          let marker = new Leaflet.Marker([node.lat, node.lon]);
          marker.setIcon(this.icons[i % this.icons.length]);
          marker.bindTooltip(node.name);
          markers.push(marker);
          if (i > 0) {
            let line = new Leaflet.Polyline([[data[i - 1].lat, data[i - 1].lon], [node.lat, node.lon]]);
            lines.push(line);
          }
          i++;
        }
        this.layers.push(new Leaflet.LayerGroup(markers));
        this.layers.push(new Leaflet.LayerGroup(lines));
      });
  }

  resetLayers() {
    this.layers = [
      new Leaflet.TileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; OpenStreetMap contributors'
      } as Leaflet.TileLayerOptions),
    ];
    this.options = {
      layers: this.layers,
      zoom: 12,
      center: new Leaflet.LatLng(48.85341, 2.3488)
    };
  }

}
