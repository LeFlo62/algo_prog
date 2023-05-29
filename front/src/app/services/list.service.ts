import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { City } from '../data/lists';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ListService {

  private apiUrl = "http://localhost:8080/";

  constructor(private http : HttpClient) { }

  getArtworks() : Observable<string[]>{
    return this.http.get<string[]>(this.apiUrl + "list/artworks");
  }
}
