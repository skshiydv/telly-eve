
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { filter } from 'rxjs/operators';

// Define the structure for a toast message
export interface Toast {
  id: number;
  type: 'success' | 'error' | 'info' | 'warning';
  title: string;
  message: string;
  duration?: number; // Duration in milliseconds
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  private toasts: Toast[] = [];
  private toastSubject = new BehaviorSubject<Toast[]>([]);
  private lastId = 0;
  public toasts$: Observable<Toast[]> = this.toastSubject.asObservable();

  constructor() {}

  show(type: Toast['type'], title: string, message: string, duration = 5000): void {
    this.lastId++;
    const newToast: Toast = { id: this.lastId, type, title, message, duration };

    this.toasts = [...this.toasts, newToast];
    this.toastSubject.next(this.toasts);

    if (duration > 0) {
      setTimeout(() => this.remove(newToast.id), duration);
    }
  }
  remove(id: number): void {
    this.toasts = this.toasts.filter(toast => toast.id !== id);
    this.toastSubject.next(this.toasts);
  }
}
