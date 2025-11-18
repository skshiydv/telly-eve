import {Component, inject, OnInit} from '@angular/core';
import {RoomService} from "../../../services/roomService/room.service";
import {NgForOf, DatePipe, NgClass} from "@angular/common"; // Import NgClass
import {RouterLink, RouterLinkActive} from "@angular/router";
import {TaskService} from "../../../services/taskService/task.service";
import { ToastService } from '../../../services/toastService/toast.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    NgForOf,
    RouterLink,
    RouterLinkActive,
    DatePipe,
    NgClass
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {

  roomService = inject(RoomService);
  taskService = inject(TaskService);
  toastService = inject(ToastService);

  rooms:any=[];
  Tasks:any=[];

  ngOnInit(): void {
    this.roomService.getAllUnjoinedRoom().subscribe({
      next: (res) => {
        if (res.body != null) {
          this.rooms = res.body;
        }
      },
      error: (err) => {
        console.log(err);
        this.toastService.show('error', 'Load Failed', 'Could not load available rooms.');
      }
    });

    this.taskService.getTask().subscribe({
      next: (res) => {
        if (res.body != null) {
          if (typeof res.body === 'string') {
            this.Tasks = JSON.parse(res.body);
          } else {
            this.Tasks = res.body;
          }
        }
      },
      error: (err) => {
        console.log(err);
        this.toastService.show('error', 'Load Failed', 'Could not load your tasks.');
      }
    });
  }

  joinRoom(roomName: string) {
    this.roomService.joinRoom(roomName).subscribe({
      next: (res) => {
        this.toastService.show('success', 'Room Joined!', `You have successfully joined ${roomName}.`);
        this.ngOnInit();
      },
      error: (err) => {
        console.log(err);
        this.toastService.show('error', 'Join Failed', `Could not join room ${roomName}.`);
      }
    });
  }

  // --- NEW METHOD ---
  // Toggles the completion status of a task
  toggleTaskCompletion(task: any, event: Event): void {
    event.stopPropagation(); // Prevent any parent click events
    const newStatus = !task.completed;

    // Assuming your taskService has an update method
    // this.taskService.updateTaskStatus(task.id, newStatus).subscribe({ ... });
    this.taskService.updateTask(task.id , newStatus).subscribe({
      next: (res) => {
        task.completed = newStatus;
        const statusText = newStatus ? 'completed' : 'pending';
        this.toastService.show('info', 'Task Updated', `Task marked as ${statusText}.`);
      },
      error: (err) => {
        console.log(err);
      }
    })
    // For demonstration, we'll update the status locally

  }
}
