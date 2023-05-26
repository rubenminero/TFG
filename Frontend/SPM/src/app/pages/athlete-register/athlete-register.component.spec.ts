import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthleteRegisterComponent } from './athlete-register.component';

describe('AthleteRegisterComponent', () => {
  let component: AthleteRegisterComponent;
  let fixture: ComponentFixture<AthleteRegisterComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AthleteRegisterComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthleteRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
