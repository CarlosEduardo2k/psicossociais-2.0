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
    const identificacao = formulario.querySelector<HTMLInputElement>(
      this.perfilSelecionado === 'administrador' ? '#cpf' : '#matricula'
    )?.value.trim();
    const senha = formulario.querySelector<HTMLInputElement>('#senha')?.value.trim();

    if (!nome || !identificacao || !senha) {
      alert('Preencha todos os campos antes de continuar.');
      return;
    }

    // Monta o objeto
    const identificacaoInput = formulario.querySelector<HTMLInputElement>(
      this.perfilSelecionado === 'administrador' ? '#cpf' : '#matricula'
    )?.value.trim();

    const dadosLogin: any = {
      nome: nome,
      senha: senha
    };

    if (this.perfilSelecionado === 'administrador') {
      dadosLogin.cpf = identificacaoInput;
    } else {
      dadosLogin.matricula = identificacaoInput;
    }

    // Chamada  o endpoint do backend
    this.http.post<any>('http://localhost:8080/api/auth', dadosLogin).subscribe({
      next: (resposta) => {
        // Guarda os dados reais do usuário logado no navegador
        localStorage.setItem('usuarioLogado', JSON.stringify(resposta));

        // Redireciona para as suas respetiva tela: usuário ou administrador 
        void this.router.navigate([
          this.perfilSelecionado === 'administrador' ? '/administrador' : '/agradecimento',
        ]);
      },
      error: (err) => {
        console.error('Erro no login:', err);
        alert('Credenciais inválidas ou erro ao conectar com o servidor.');
      }
    });
  }
}