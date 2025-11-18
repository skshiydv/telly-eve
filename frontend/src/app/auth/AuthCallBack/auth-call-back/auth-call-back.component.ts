import {Component, inject, OnInit} from '@angular/core';
import {AuthService} from "../../auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-auth-call-back',
  standalone: true,
  imports: [],
  templateUrl: './auth-call-back.component.html',
  styleUrl: './auth-call-back.component.css'
})
export class AuthCallBackComponent implements OnInit {
  private authService=inject(AuthService)
  private route=inject(ActivatedRoute)
  private router=inject(Router);
  ngOnInit(): void {
    const token = this.route.snapshot.queryParamMap.get('token');
    if (token) {
      this.authService.login(token);
      this.router.navigate(['/']);
    } else {
      this.router.navigate(['/login']);
    }

  }

}
