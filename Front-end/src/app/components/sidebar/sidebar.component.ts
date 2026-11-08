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
  @Input() nomeAdmin: string = 'Administrador';
  @Input() emailAdmin: string = 'admin@empresa.com';
  
  @Output() logoutEvent = new EventEmitter<void>();
  @Output() mudarAbaEvent = new EventEmitter<string>();

  // aqui ele pega as iniciais do usuário e cria o avatar
  get iniciaisAvatar(): string {
    if (!this.nomeAdmin) return 'AD';
    const partes = this.nomeAdmin.trim().split(' ');
    if (partes.length >= 2) {
      return (partes[0][0] + partes[1][0]).toUpperCase();
    }
    return this.nomeAdmin.substring(0, 2).toUpperCase();
  }

  selecionarAba(aba: string): void {
    this.mudarAbaEvent.emit(aba);
  }

  onLogout(): void {
    this.logoutEvent.emit();
  }
}