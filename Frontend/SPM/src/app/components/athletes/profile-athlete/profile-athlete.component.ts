import { Athlete } from 'src/app/interfaces/athlete/Athlete';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { AthleteServiceService } from 'src/app/services/athlete/athlete-service.service';
import { Component } from '@angular/core';
import { AthleteCardComponent } from '../../shared/models-cards/athlete-card/athlete-card.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-profile-athlete',
  templateUrl: './profile-athlete.component.html',
  styleUrls: ['./profile-athlete.component.scss'],
})
export class ProfileAthleteComponent {
  athlete_show: boolean = false;
  athlete: Athlete = {
    id: -1,
    username: '',
    nif: '',
    email: '',
    first_name: '',
    last_name: '',
    phone_number: '',
    enabled: false,
  };

  constructor(
    private AthleteServiceService: AthleteServiceService,
    private AuthService: AuthService
  ) {}

  ngOnInit(): void {
    this.AthleteServiceService.getAthlete(this.AuthService.getId()).subscribe(
      (response: Athlete) => {
        this.athlete = response;
        this.athlete_show = true;
      },
      (error) => {
        this.AuthService.getPath();
      }
    );
  }
}
