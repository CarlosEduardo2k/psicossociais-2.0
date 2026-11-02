
<<<<<<< HEAD
import { Component, signal } from '@angular/core';
import { Login } from './login/login';
import { Agradecimento } from './agradecimento/agradecimento';
import { DashboardComponent } from './dashboard/dashboard.component';
=======
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
>>>>>>> 758765d036ef08f4cb15e2455e9d3881a3148369

@Component({
  selector: 'app-root',
  standalone: true,
<<<<<<< HEAD
  imports: [Login, Agradecimento, DashboardComponent],
=======
  imports: [RouterOutlet],
>>>>>>> 758765d036ef08f4cb15e2455e9d3881a3148369
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {}
