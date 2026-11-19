import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CadastrarQuestionario } from './cadastrar-questionario';

describe('CadastrarQuestionario', () => {
  let component: CadastrarQuestionario;
  let fixture: ComponentFixture<CadastrarQuestionario>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CadastrarQuestionario],
    }).compileComponents();

    fixture = TestBed.createComponent(CadastrarQuestionario);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
