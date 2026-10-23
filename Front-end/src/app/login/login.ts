
import { Component, output } from '@angular/core';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  perfilSelecionado: 'funcionario' | 'administrador' = 'funcionario';

  entrar = output<void>();

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

    const matricula = formulario.querySelector<HTMLInputElement>(
      '#matricula'
    )?.value.trim();

    const senha = formulario.querySelector<HTMLInputElement>(
      '#senha'
    )?.value.trim();

    if (!nome || !matricula || !senha) {
      alert('Preencha todos os campos antes de continuar.');
      return;
    }

    this.entrar.emit();
  }
}
