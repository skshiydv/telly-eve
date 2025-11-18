import {Component, OnInit, OnDestroy, ViewChild, ElementRef, AfterViewInit, inject, NgZone} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {NgForOf, NgIf} from '@angular/common';
import {FormsModule} from '@angular/forms';
import SockJS from "sockjs-client";
import {Stomp} from "@stomp/stompjs";
import {ChatRoomService} from "../../services/chatRoom/chat-room.service";
import {Observable} from "rxjs";
import {AuthService} from "../../auth/auth.service";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import {CreateTaskModalComponent} from "../taskModal/create-task-modal/create-task-modal.component";
import {ToastService} from "../../services/toastService/toast.service";

interface ChatMessage {
  sender: string;
  message: string;
  isOwnMessage: boolean;
}

interface User {
  username: string;
  email: string;
  image: string;
}

@Component({
  selector: 'app-chatRoom',
  standalone: true,
  imports: [FormsModule, NgIf, NgForOf],
  templateUrl: './chatRoom.component.html',
  styleUrl: './chatRoom.component.css'
})

export class ChatRoomComponent implements OnInit, OnDestroy, AfterViewInit {
  public roomName: string = '';
  public messages: ChatMessage[] = [];
  public newMessage: string = '';
  public currentUsername: User | null = null; // Initialize as null
  userEmail$!: Observable<any>;
  email = "";
  toastService = inject(ToastService)
  // --- WebSocket Properties ---
  private stompClient: any;
  private chatRoomService = inject(ChatRoomService);
  private authService = inject(AuthService);
  @ViewChild('messageContainer') private messageContainer!: ElementRef;
  private zone = inject(NgZone);

  constructor(private route: ActivatedRoute, public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.userEmail$ = this.authService.currentUser$;
    this.userEmail$.subscribe((user) => {
      let email = user.email;
      if (email) {
        this.email = email;
        this.chatRoomService.getUserByEmail(this.email).subscribe({
          next: (res) => {
            if (res.body) {
              this.currentUsername = res.body as User;
            }
          }, error: (err) => {
            console.error("Error fetching user profile:", err);
          }
        });
      }
    });
    this.route.params.subscribe(params => {
      this.roomName = params['roomName'];
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.loadInitialMessages(this.roomName);
      this.initializeWebSocketConnection();
    });
  }

  loadInitialMessages(roomName: string): void {
    this.chatRoomService.getAllMessagesOfRoom(roomName).subscribe({
      next: (res) => {
        if (res.body && this.currentUsername) {
          this.messages = (res.body as any[]).map(msg => ({
            ...msg, isOwnMessage: msg.sender === this.currentUsername!.username
          }));
          this.scrollToBottom();
        } else {
          this.messages = [];
        }
      }, error: (err) => {
        console.error("Error loading initial messages:", err);
        this.messages = [];
      }
    });
  }

  ngAfterViewInit() {
    this.scrollToBottom();
  }

  ngOnDestroy(): void {

    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }

  initializeWebSocketConnection(): void {
    const serverUrl = 'http://localhost:8080/chat';
    const ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    this.stompClient.connect({}, (frame: any) => {
      console.log('Connected to WebSocket for room: ' + this.roomName);
      this.stompClient.subscribe(`/topic/room/${this.roomName}`, (message: any) => {
        this.zone.run(() => {
          if (message.body) {
            const receivedMessage = JSON.parse(message.body);
            this.handleNewMessage(receivedMessage);
          }
        });
      });
    }, (error: any) => {
      console.error('Connection error: ' + error);
    });
  }

  handleNewMessage(message: any): void {
    if (!this.currentUsername) return;
    this.messages.push({
      sender: message.body.sender,
      message: message.body.message,
      isOwnMessage: message.body.sender === this.currentUsername.username
    });
    this.scrollToBottom();
  }

  sendMessage(): void {
    if (this.newMessage.trim() && this.stompClient && this.currentUsername) {
      const chatMessage = {
        sender: this.currentUsername.username, messageContent: this.newMessage, receiver: this.roomName
      };
      this.stompClient.send(`/app/sendMessage/${this.roomName}`, {}, JSON.stringify(chatMessage));

      this.newMessage = '';
    }
  }

  leaveRoom() {
    this.chatRoomService.leaveRoom(this.roomName).subscribe({
      next: (res) => {
        console.log(res.body)
        this.toastService.show("success", "Success", "Room left Successfully")
      }, error: (err) => {
        console.log(err)
        this.toastService.show("error", "Error", err.statusText)
      }
    })
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(CreateTaskModalComponent, {
      height: '400px', width: '400px', data: {roomName: this.roomName}
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed', result);
    });
  }

  private scrollToBottom(): void {
    try {
      setTimeout(() => {
        this.messageContainer.nativeElement.scrollTop = this.messageContainer.nativeElement.scrollHeight;
      }, 0);
    } catch (err) {
    }
  }
}
