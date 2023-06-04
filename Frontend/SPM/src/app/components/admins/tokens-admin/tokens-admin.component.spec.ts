import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TokensAdminComponent } from './tokens-admin.component';

describe('TokensAdminComponent', () => {
  let component: TokensAdminComponent;
  let fixture: ComponentFixture<TokensAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TokensAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TokensAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
