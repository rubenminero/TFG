import { AuthService } from './../../../../services/auth/auth-service.service';
import { EventServiceService } from './../../../../services/event/event-service.service';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Events } from 'src/app/interfaces/event/event';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from '../../pop-ups/pop-up-msg/pop-up-msg.component';

@Component({
  selector: 'app-event-card',
  templateUrl: './event-card.component.html',
  styleUrls: ['./event-card.component.scss'],
})
export class EventCardComponent {
  @Input() admin: boolean = false;
  @Input() event: Events = {
    id: -1,
    name: '',
    location: '',
    address: '',
    description: '',
    organizer: '',
    sport_type: '',
  };
  form: FormGroup = new FormGroup({});

  constructor(
    private dialog: MatDialog,
    private router: Router,
    private eventService: EventServiceService,
    private AuthService: AuthService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    if (this.event.id == -1) {
      this.dialog.open(PopUpMsgComponent, {
        data: {
          msg: 'Error al intentar mostrar los datos del evento.',
        },
      });
    }
    this.form = this.fb.group({
      name: [{ value: '', disabled: !this.admin }, Validators.required],
      location: [{ value: '', disabled: !this.admin }, Validators.required],
      address: [{ value: '', disabled: !this.admin }, Validators.required],
      description: [{ value: '', disabled: !this.admin }, Validators.required],
      organizer: [{ value: '', disabled: !this.admin }, Validators.required],
      sport_type: [{ value: '', disabled: !this.admin }, Validators.required],
    });

    this.form.setValue({
      name: this.event.name,
      location: this.event.location,
      address: this.event.address,
      description: this.event.description,
      organizer: this.event.organizer,
      sport_type: this.event.sport_type,
    });
  }

  get name() {
    return this.form.get('name');
  }

  get location() {
    return this.form.get('location');
  }

  get address() {
    return this.form.get('address');
  }

  get description() {
    return this.form.get('description');
  }

  get organizer() {
    return this.form.get('organizer');
  }

  get sport_type() {
    return this.form.get('sport_type');
  }

  back(): void {
    window.history.back();
  }

  ngOnSubmit(): void {
    this.event = {
      id: this.event.id,
      name: this.form.value.name,
      location: this.form.value.location,
      address: this.form.value.address,
      description: this.form.value.description,
      organizer: this.form.value.organizer,
      sport_type: this.form.value.sport_type,
    };
    this.eventService.updateEvent(this.event).subscribe(
      (response) => {
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Datos del evento actualizados.',
          },
        });
      },
      (error) => {
        this.dialog.open(PopUpMsgComponent, {
          data: {
            msg: 'Error al actualizar.',
          },
        });
      }
    );
  }
}
