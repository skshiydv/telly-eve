import {Component, Inject, inject, Input, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {TaskService} from "../../../services/taskService/task.service";
import {RoomService} from "../../../services/roomService/room.service";
import {ActivatedRoute} from "@angular/router";
import {NgForOf} from "@angular/common";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {ToastService} from "../../../services/toastService/toast.service";
interface getRoomByRoomName {
  roomName: string;
  roomDescription: string;
  users:any[]
}
export interface DialogData {
  roomName: string;
}

@Component({
  selector: 'app-create-task-modal',
  standalone: true,
  imports: [FormsModule, NgForOf],
  templateUrl: './create-task-modal.component.html',
  styleUrl: './create-task-modal.component.css'
})
export class CreateTaskModalComponent implements OnInit {
  taskName: string = "";
  deadLine: string = '';
  assignedTo: string = "";
  roomName: string = "";
  Users:any[]=[]
  ToastService = inject(ToastService)
  rooms:getRoomByRoomName |null=null
  roomService = inject(RoomService);
  taskService = inject(TaskService)
  constructor(private route: ActivatedRoute, @Inject(MAT_DIALOG_DATA) public data: DialogData) {
    this.roomName = data.roomName;
  }
  ngOnInit(): void {

      this.roomService.getRoomByRoomName(this.roomName).subscribe({
        next: (res) => {
          this.rooms = res.body as getRoomByRoomName;
          this.Users = this.rooms.users
        },
        error: err => {
          console.log(err)
        }
      })
  }
  createTask() {
    let payload = {
      task: this.taskName, assignedTo: this.assignedTo, dueDate: new Date(this.deadLine),
    }
    this.taskService.createTask(payload).subscribe({
      next: (res) => {
        this.ToastService.show("success", "Task created","",5000)
      },
      error: err => {
        console.log(err)
        this.ToastService.show("error", "Error",err.statusText,5000)
      }
    },)

  }


}
