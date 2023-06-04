import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InscriptionsAdminComponent } from './inscriptions-admin.component';

describe('InscriptionsAdminComponent', () => {
  let component: InscriptionsAdminComponent;
  let fixture: ComponentFixture<InscriptionsAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InscriptionsAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InscriptionsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
