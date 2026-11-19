import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdministradorService {
  private http = inject(HttpClient);
  
  // URL base da área do administrador
  private apiUrl = 'http://localhost:8080/administradores';

  // Busca os dados do administrador pelo ID
  buscarPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Atualiza os dados do administrador
  atualizar(id: number, dadosEdicao: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, dadosEdicao);
  }
}
