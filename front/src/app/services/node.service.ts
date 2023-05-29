import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NodeService {

  constructor(private http : HttpClient) { }

  getPath(days : number, transports : number, dayStart : number, dayEnd : number, timeSpent : number, artworks : string) : Observable<Node[]>{
    return this.http.get("http://localhost:8080/searchPath?stayDuration=" + days + "&transportSpeed=" + transports + "&startDay=" + dayStart + "&endDay=" + dayEnd + "&timeSpent=" + timeSpent + "&artworkType=" + artworks) as Observable<Node[]>;
  }
}
