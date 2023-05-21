import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';

import { ButtonModule } from 'primeng/button';
import { DynamicDropdownsComponent } from './dynamic-dropdowns/dynamic-dropdowns.component';

import { FormsModule } from '@angular/forms';
import { MapsComponent } from './maps/maps.component';


@NgModule({
  declarations: [
    AppComponent,
    DynamicDropdownsComponent,
    MapsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ButtonModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
