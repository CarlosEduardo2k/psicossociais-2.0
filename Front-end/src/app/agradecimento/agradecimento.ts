
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agradecimento',
  standalone: true,
  imports: [],
  templateUrl: './agradecimento.html',
  styleUrl: './agradecimento.css',
})
export class Agradecimento {
  constructor(private readonly router: Router) {}

  voltarAoInicio(): void {
    void this.router.navigate(['/']);
  }
}
