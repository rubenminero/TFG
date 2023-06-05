import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopUpPasswordComponent } from './pop-up-password.component';

describe('PopUpPasswordComponent', () => {
  let component: PopUpPasswordComponent;
  let fixture: ComponentFixture<PopUpPasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopUpPasswordComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PopUpPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
