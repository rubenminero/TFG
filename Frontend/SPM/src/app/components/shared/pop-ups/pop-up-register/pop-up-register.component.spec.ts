import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopUpRegisterComponent } from './pop-up-register.component';

describe('PopUpRegisterAthleteComponent', () => {
  let component: PopUpRegisterComponent;
  let fixture: ComponentFixture<PopUpRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PopUpRegisterComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PopUpRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
