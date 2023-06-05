import { DeleteDisabledService } from './../../../services/admin/delete-disabled.service';
import { Component } from '@angular/core';
import { DeleteDisabled } from 'src/app/interfaces/delete-disabled';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from '../../shared/pop-ups/pop-up-msg/pop-up-msg.component';

@Component({
  selector: 'app-delete-all-disabled-admin',
  templateUrl: './delete-all-disabled-admin.component.html',
  styleUrls: ['./delete-all-disabled-admin.component.scss'],
})
export class DeleteAllDisabledAdminComponent {
  deleteDisabledSummary: DeleteDisabled = {
    users_disabled: 0,
    tournaments_disabled: 0,
    events_disabled: 0,
    inscriptions_disabled: 0,
    watchlists_disabled: 0,
    tokens_disabled: 0,
    sports_types_disabled: 0,
  };

  form: FormGroup = new FormGroup({});

  constructor(
    private DeleteDisabledService: DeleteDisabledService,
    private AuthService: AuthService,
    private fb: FormBuilder,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      users_disabled: [''],
      tournaments_disabled: [''],
      events_disabled: [''],
      inscriptions_disabled: [''],
      watchlists_disabled: [''],
      tokens_disabled: [''],
      sports_types_disabled: [''],
    });
    this.DeleteDisabledService.getDisabledSummary().subscribe(
      (response) => {
        console.log(response);
        this.deleteDisabledSummary = {
          users_disabled: response.users_disabled,
          tournaments_disabled: response.tournaments_disabled,
          events_disabled: response.events_disabled,
          inscriptions_disabled: response.inscriptions_disabled,
          watchlists_disabled: response.watchlists_disabled,
          tokens_disabled: response.tokens_disabled,
          sports_types_disabled: response.sports_types_disabled,
        };

        this.form.setValue({
          users_disabled: this.deleteDisabledSummary.users_disabled,
          tournaments_disabled: this.deleteDisabledSummary.tournaments_disabled,
          events_disabled: this.deleteDisabledSummary.events_disabled,
          inscriptions_disabled:
            this.deleteDisabledSummary.inscriptions_disabled,
          watchlists_disabled: this.deleteDisabledSummary.watchlists_disabled,
          tokens_disabled: this.deleteDisabledSummary.tokens_disabled,
          sports_types_disabled:
            this.deleteDisabledSummary.sports_types_disabled,
        });
      },
      (error) => {
        this.back();
      }
    );
  }

  back(): void {
    this.AuthService.getPath();
  }

  deleteDisableds(): void {
    this.DeleteDisabledService.deleteDisableds().subscribe(
      (response) => {
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Las entidades deshabilitadas han sido eliminadas correctamente.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.back();
          });
      },
      (error) => {
        this.dialog
          .open(PopUpMsgComponent, {
            data: {
              msg: 'Error al borrar.',
            },
          })
          .afterClosed()
          .subscribe(() => {
            this.back();
          });
      }
    );
  }
}
