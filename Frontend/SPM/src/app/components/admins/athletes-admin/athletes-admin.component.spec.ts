import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AthletesAdminComponent } from './athletes-admin.component';

describe('AthletesAdminComponent', () => {
  let component: AthletesAdminComponent;
  let fixture: ComponentFixture<AthletesAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AthletesAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AthletesAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
