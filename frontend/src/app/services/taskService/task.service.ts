import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private http= inject(HttpClient);
  constructor() { }
  createTask(payload:any){
    return this.http.post("http://localhost:8080/task/create",payload,{observe: 'response',responseType:"text",withCredentials:true});
  }
  getTask(){
    return this.http.get(`http://localhost:8080/task/get`,{observe: 'response',responseType:"text",withCredentials:true});
  }
  updateTask(id:number,payload:any){
    return this.http.put(`http://localhost:8080/task/update/${id}`,payload,{observe: 'response',responseType:"text",withCredentials:true});

  }
}
