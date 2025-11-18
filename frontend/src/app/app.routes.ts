import {Routes} from '@angular/router';
import {DashboardComponent} from "./Components/dashboard/dashboard/dashboard.component";
import {HomeComponent} from "./Components/home/home/home.component";
import {RoomComponent} from "./Components/room/room/room.component";
import {LayoutComponent} from "./Components/layout/layout.component";
import {authGuard} from "./auth/auth.guard";
import {AuthCallBackComponent} from "./auth/AuthCallBack/auth-call-back/auth-call-back.component";
import {ChatRoomComponent} from "./Components/chatRoom/chatRoom.component";
import {LandingComponent} from "./Components/landing/landing/landing.component";


export const routes: Routes = [{path: 'login', component: HomeComponent},
  { path: 'auth/callback', component: AuthCallBackComponent},{
  path: '',
  component: LayoutComponent,
  canActivate: [authGuard],
  children: [
     {path:'', component: LandingComponent},
    {path: 'dashboard', component: DashboardComponent},
    {path: 'room/create', component: RoomComponent},
    { path: 'room/:roomName', component: ChatRoomComponent }
  ]
},

];
