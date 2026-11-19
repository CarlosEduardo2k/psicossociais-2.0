import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-em-construcao',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './em-construcao.html',
})

export class EmConstrucao {

}