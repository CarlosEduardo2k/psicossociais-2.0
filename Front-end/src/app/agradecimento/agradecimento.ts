
import { Component, output } from '@angular/core';

@Component({
  selector: 'app-agradecimento',
  standalone: true,
  imports: [],
  templateUrl: './agradecimento.html',
  styleUrl: './agradecimento.css',
})
export class Agradecimento {
  voltar = output<void>();
}
