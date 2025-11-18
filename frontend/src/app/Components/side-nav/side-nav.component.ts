import {Component, inject, Input, OnInit} from '@angular/core';
import {RouterLink, RouterLinkActive} from "@angular/router";
import {RoomService} from "../../services/roomService/room.service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-side-nav',
  standalone: true,
  imports: [
    RouterLink,
    RouterLinkActive,
    NgForOf
  ],
  templateUrl: './side-nav.component.html',
  styleUrl: './side-nav.component.css'
})
export class SideNavComponent implements OnInit {
  @Input() collapsed!: boolean;
  rooms:any=[]
  private roomService=inject(RoomService);
  ngOnInit(): void {
    this.roomService.getAllRoomsByUserId().subscribe({
      next: (res)=>{
        this.rooms = res.body;
      },
      error: (err)=>{
        console.log(err);

      }
    })
  }



}
