import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RoomService {
private http = inject(HttpClient);
  constructor() { }
  createRoom(payload:any){
    return this.http.post("http://localhost:8080/room/create-room", payload, {observe: "response" ,responseType:"text",withCredentials:true}
    );
  }
  getAllRooms() {
    return this.http.get(`http://localhost:8080/room`,{observe: "response",responseType:"text",withCredentials:true});
  }
  joinRoom(roomName: string) {
    return this.http.post("http://localhost:8080/room/join",roomName,{observe: "response",responseType:"text",withCredentials:true});
  }
  getAllRoomsByUserId(){
    return this.http.get("http://localhost:8080/room/joined",{observe: "response",withCredentials:true}  );
  }
  getAllUnjoinedRoom() {
    return this.http.get("http://localhost:8080/room/unknown-rooms", {observe: "response",withCredentials:true});
  }
  getRoomByRoomName(roomName: string) {
    return this.http.get(`http://localhost:8080/room/${roomName}`,{observe: "response",withCredentials:true});
  }
}
