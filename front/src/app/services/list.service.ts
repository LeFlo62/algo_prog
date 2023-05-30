import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ListService {

  private apiUrl = "http://localhost:8080/";

  constructor(private http : HttpClient) { }

  getTypes() : Observable<string[]>{
    return this.http.get<string[]>(this.apiUrl + "list/types");
  }
}
