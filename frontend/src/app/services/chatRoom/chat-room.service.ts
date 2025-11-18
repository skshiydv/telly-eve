import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ChatRoomService {
  private http = inject(HttpClient);
  constructor() { }
  getAllMessagesOfRoom(roomName:string){
    return this.http.get(`http://localhost:8080/get/${roomName}` , {observe: 'response' , withCredentials: true} );
  }
  getUserByEmail(email:string){
    return this.http.get(`http://localhost:8080/user/get-user/${email}` , {observe: 'response',withCredentials: true} );
  }
  leaveRoom(roomName:string){
    return this.http.get(`http://localhost:8080/room/leave/${roomName}`,{observe: 'response',responseType:'text',withCredentials: true} );
  }
}
