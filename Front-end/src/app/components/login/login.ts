import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private readonly router = inject(Router);
  private readonly http = inject(HttpClient);

  perfilSelecionado: 'funcionario' | 'administrador' = 'funcionario';

  fazerLogin(event: Event): void {
    event.preventDefault();

    const formulario = event.target as HTMLFormElement;

    if (!formulario.checkValidity()) {
      formulario.reportValidity();
      return;
    }

    const nome = formulario.querySelector<HTMLInputElement>('#nome')?.value.trim();
    const cpf = formulario.querySelector<HTMLInputElement>('#cpf')?.value.trim();
    const senha = formulario.querySelector<HTMLInputElement>('#senha')?.value.trim();

    if (!nome || !cpf || !senha) {
      alert('Preencha todos os campos antes de continuar.');
      return;
    }

    // Monta o objeto enviando o CPF para os dois perfis
    const dadosLogin: any = {
      nome: nome,
      cpf: cpf,
      senha: senha
    };

    this.http.post<any>('http://localhost:8080/api/auth/login', dadosLogin).subscribe({
      next: (resposta) => {
        // Guarda os dados do usuário logado no navegador para
        localStorage.setItem('usuarioLogado', JSON.stringify(resposta));

        void this.router.navigate([
          this.perfilSelecionado === 'administrador' ? '/administrador' : '/questionario', 
        ]);
      },
      error: (err) => {
        console.error('Erro no login:', err);
        alert('Credenciais inválidas ou erro ao conectar com o servidor.');
      }
    });
  }
}