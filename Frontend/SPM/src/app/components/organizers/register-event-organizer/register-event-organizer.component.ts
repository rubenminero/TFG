import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SportTypeService } from 'src/app/services/sport_type/sport-type.service';
import { SportType } from 'src/app/interfaces/sport.type/SportType';
import { EventServiceService } from 'src/app/services/event/event-service.service';
import { PopUpMsgComponent } from '../../shared/pop-ups/pop-up-msg/pop-up-msg.component';
import {
  FormControl,
  FormGroup,
  FormBuilder,
  Validators,
} from '@angular/forms';
import { RegisterEvent } from 'src/app/interfaces/event/register-event';
import { AuthService } from 'src/app/services/auth/auth-service.service';

@Component({
  selector: 'app-register-event-organizer',
  templateUrl: './register-event-organizer.component.html',
  styleUrls: ['./register-event-organizer.component.scss'],
})
export class RegisterEventOrganizerComponent {
  sports_types_list: SportType[] = [];

  event: RegisterEvent = {
    name: '',
    location: '',
    address: '',
    description: '',
    organizer: '',
    sport_type: '',
  };
  form: FormGroup = new FormGroup({});

  constructor(
    private sportTypeService: SportTypeService,
    private eventService: EventServiceService,
    private AuthService: AuthService,
    private dialog: MatDialog,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.sportTypeService.getSportTypes().subscribe(
      (data: any[]) => {
        for (let i = 0; i < data.length; i++) {
          let sport_type_aux = {
            id: data[i].id,
            name: data[i].name,
            enabled: data[i].enabled,
          };
          this.sports_types_list.push(sport_type_aux);
        }
      },
      (error) => {
        if (error.status == 404) {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'No hay deportes.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.AuthService.getPath();
            });
        } else {
          this.dialog
            .open(PopUpMsgComponent, {
              data: {
                msg: 'Error al cargar los deportes.',
              },
            })
            .afterClosed()
            .subscribe(() => {
              this.AuthService.getPath();
            });
        }
      }
    );

    this.form = this.fb.group({
      name: ['', Validators.required],
      location: ['', Validators.required],
      address: ['', Validators.required],
      description: ['', Validators.required],
      sport_type_name: ['', Validators.required],
    });
  }

  back(): void {
    window.history.back();
  }

  ngOnSubmit(): void {
    this.event = {
      name: this.form.value.name,
      location: this.form.value.location,
      address: this.form.value.address,
      description: this.form.value.description,
      organizer: '',
      sport_type: this.form.value.sport_type_name.name,
    };
    console.log(this.event);
    this.eventService.createEvent(this.event).subscribe(
      (response) => {
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'El evento se ha creado correctamente.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      },
      (error) => {
        console.log(error);
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Error al crear el evento.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.ngOnInit();
          });
      }
    );
  }
}
