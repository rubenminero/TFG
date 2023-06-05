import { Component } from '@angular/core';
import { Organizer } from 'src/app/interfaces/organizer/organizer';
import { OrganizerServiceService } from 'src/app/services/organizer/organizer-service.service';
import { AuthService } from 'src/app/services/auth/auth-service.service';

@Component({
  selector: 'app-profile-organizer',
  templateUrl: './profile-organizer.component.html',
  styleUrls: ['./profile-organizer.component.scss'],
})
export class ProfileOrganizerComponent {
  organizer_show: boolean = false;
  organizer: Organizer = {
    id: -1,
    username: '',
    nif: '',
    email: '',
    first_name: '',
    last_name: '',
    company: '',
    address: '',
    enabled: false,
  };

  constructor(
    private organizerService: OrganizerServiceService,
    private AuthService: AuthService
  ) {}

  ngOnInit(): void {
    this.organizerService.getOrganizer(this.AuthService.getId()).subscribe(
      (response: Organizer) => {
        this.organizer = response;
        this.organizer_show = true;
      },
      (error) => {
        this.AuthService.getPath();
      }
    );
  }
}
