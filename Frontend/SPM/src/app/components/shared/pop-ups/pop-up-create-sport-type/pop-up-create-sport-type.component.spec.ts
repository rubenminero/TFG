import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopUpCreateSportTypeComponent } from './pop-up-create-sport-type.component';

describe('PopUpCreateSportTypeComponent', () => {
  let component: PopUpCreateSportTypeComponent;
  let fixture: ComponentFixture<PopUpCreateSportTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PopUpCreateSportTypeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PopUpCreateSportTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
