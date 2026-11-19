import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Agradecimento } from './agradecimento';

describe('Agradecimento', () => {
  let component: Agradecimento;
  let fixture: ComponentFixture<Agradecimento>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Agradecimento],
    }).compileComponents();

    fixture = TestBed.createComponent(Agradecimento);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
