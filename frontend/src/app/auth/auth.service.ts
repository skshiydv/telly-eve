import { inject, Injectable } from '@angular/core';
import { JwtHelperService } from "@auth0/angular-jwt";
import { BehaviorSubject, Observable } from "rxjs";


export interface User {
  name: string;
  email: string;
  avatarUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private jwtHelper = new JwtHelperService();

  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor() {
    this.loadUserFromToken();
  }

  isAuthenticated(): boolean {
    const token = localStorage.getItem("access_token");
    return token ? !this.jwtHelper.isTokenExpired(token) : false;
  }

  public get currentUserValue(): User | null {
    return this.currentUserSubject.value;
  }

  login(token: string): void {
    localStorage.setItem('access_token', token);
    // 4. After logging in, immediately load the user's data
    this.loadUserFromToken();
  }

  logout(): void {
    localStorage.removeItem('access_token');
    // 5. When logging out, clear the user data
    this.currentUserSubject.next(null);
  }

  // 6. This private method now holds the logic for decoding and setting user state.
  private loadUserFromToken(): void {
    const token = localStorage.getItem("access_token");
    if (token && !this.jwtHelper.isTokenExpired(token)) {
      const decodedToken = this.jwtHelper.decodeToken(token);

      const user: User = {
        email: decodedToken.sub, // 'sub' is standard for subject/user ID
        name: decodedToken.name,
        avatarUrl: decodedToken.avatarUrl
      };

      this.currentUserSubject.next(user);
    } else {
      // If token is invalid or missing, ensure state is null
      this.currentUserSubject.next(null);
    }
  }
}
