import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthletesMenuComponent } from './athletes-menu.component';

describe('AthletesMenuComponent', () => {
  let component: AthletesMenuComponent;
  let fixture: ComponentFixture<AthletesMenuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AthletesMenuComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AthletesMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
