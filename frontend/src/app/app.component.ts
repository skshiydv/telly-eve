import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ToastComponent} from "./Components/Toasr/toast/toast.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ToastComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'telly';
}
