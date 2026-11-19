import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  @Input() abaAtiva: string = '';
  @Input() nomeAdmin: string = '';
  @Input() emailAdmin: string = '';
  @Output() mudarAba = new EventEmitter<string>();
  @Output() logoutEvent = new EventEmitter<void>();

  selecionarAba(aba: string): void {
    this.mudarAba.emit(aba);
  }

  // aqui ele pega as iniciais do usuário e cria o avatar
  get iniciaisAvatar(): string {
    if (!this.nomeAdmin) return 'AD';
    const partes = this.nomeAdmin.trim().split(' ');
    if (partes.length >= 2) {
      return (partes[0][0] + partes[1][0]).toUpperCase();
    }
    return this.nomeAdmin.substring(0, 2).toUpperCase();
  }

  onLogout(): void {
    this.logoutEvent.emit();
  }
}