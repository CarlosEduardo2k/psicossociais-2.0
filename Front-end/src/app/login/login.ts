
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  perfilSelecionado: 'funcionario' | 'administrador' = 'funcionario';

  constructor(private readonly router: Router) {}

  fazerLogin(event: Event): void {
    event.preventDefault();

    const formulario = event.target as HTMLFormElement;

    if (!formulario.checkValidity()) {
      formulario.reportValidity();
      return;
    }

    const nome = formulario.querySelector<HTMLInputElement>(
      '#nome'
    )?.value.trim();

    const identificacao = formulario.querySelector<HTMLInputElement>(
      this.perfilSelecionado === 'administrador' ? '#cpf' : '#matricula'
    )?.value.trim();

    const senha = formulario.querySelector<HTMLInputElement>(
      '#senha'
    )?.value.trim();

    if (!nome || !identificacao || !senha) {
      alert('Preencha todos os campos antes de continuar.');
      return;
    }

    void this.router.navigate([
      this.perfilSelecionado === 'administrador' ? '/administrador' : '/agradecimento',
    ]);
  }
}
