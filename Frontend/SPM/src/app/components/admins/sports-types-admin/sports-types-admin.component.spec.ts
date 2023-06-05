import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SportsTypesAdminComponent } from './sports-types-admin.component';

describe('SportsTypesAdminComponent', () => {
  let component: SportsTypesAdminComponent;
  let fixture: ComponentFixture<SportsTypesAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SportsTypesAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SportsTypesAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
