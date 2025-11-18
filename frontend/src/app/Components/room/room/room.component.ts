import {Component, inject} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {RoomService} from "../../../services/roomService/room.service";
import {ToastService} from "../../../services/toastService/toast.service";

@Component({
  selector: 'app-room',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './room.component.html',
  styleUrl: './room.component.css'
})
export class RoomComponent {
  roomService = inject(RoomService)
  ToasterService = inject(ToastService)
  createRoomForm = new FormGroup({
    roomName: new FormControl('', [Validators.required]),
    roomType: new FormControl('', [Validators.required]),
    roomDescription: new FormControl('', [Validators.required]),
  })

  submitForm(){
    this.roomService.createRoom(this.createRoomForm.value).subscribe(
      {
        next: result => {
          this.ToasterService.show("success","Created","Room Created Successfully");
        },
        error: err => {
          this.ToasterService.show("error", "Error",err.statusText,5000)
        }
      }
    )
  }
}
