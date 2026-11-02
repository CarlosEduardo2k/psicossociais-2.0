
import { Component, signal } from '@angular/core';
import { Login } from './login/login';
import { Agradecimento } from './agradecimento/agradecimento';
import { DashboardComponent } from './dashboard/dashboard.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Login, Agradecimento, DashboardComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  mostrarAgradecimento = signal(false);

  agradecer(): void {
    this.mostrarAgradecimento.set(true);
  }

  voltarAoLogin(): void {
    this.mostrarAgradecimento.set(false);
  }
}
