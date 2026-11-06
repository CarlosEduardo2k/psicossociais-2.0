import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdministradorService {
  private http = inject(HttpClient);
  
  // URL base do nosso AdministradorController no Java (porta 8080)
  private apiUrl = 'http://localhost:8080/administradores';

  // Buscar dados do administrador pelo ID
  buscarPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Atualizar dados do administrador
  atualizar(id: number, dadosEdicao: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, dadosEdicao);
  }
}
