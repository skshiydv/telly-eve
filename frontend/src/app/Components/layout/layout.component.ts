import { Component } from '@angular/core';
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {RouterOutlet} from "@angular/router";
import {NgClass, NgStyle} from "@angular/common";
import {SideNavComponent} from "../side-nav/side-nav.component";
@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    MatSidenavContent,
    MatSidenav,
    MatSidenavContainer,
    RouterOutlet,
    NgStyle,
    NgClass,
    SideNavComponent
  ],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {
  isSidenavOpen: boolean = true;

  toggleSidebar() {
    this.isSidenavOpen = !this.isSidenavOpen;
  }

}
