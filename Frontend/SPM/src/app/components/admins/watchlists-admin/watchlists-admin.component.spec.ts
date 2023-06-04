import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WatchlistsAdminComponent } from './watchlists-admin.component';

describe('WatchlistsAdminComponent', () => {
  let component: WatchlistsAdminComponent;
  let fixture: ComponentFixture<WatchlistsAdminComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WatchlistsAdminComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(WatchlistsAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
