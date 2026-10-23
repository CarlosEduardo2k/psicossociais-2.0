
import { Component, signal } from '@angular/core';
import { Login } from './login/login';
import { Agradecimento } from './agradecimento/agradecimento';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [Login, Agradecimento],
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
