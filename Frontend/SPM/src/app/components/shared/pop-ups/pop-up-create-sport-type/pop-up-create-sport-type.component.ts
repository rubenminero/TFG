import { Component, Inject } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { RegisterSportType } from 'src/app/interfaces/sport.type/RegisterSportType';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PopUpMsgComponent } from '../pop-up-msg/pop-up-msg.component';
import { SportsTypesAdminServiceService } from 'src/app/services/admin/sports-types/sport-types-admin.service';

@Component({
  selector: 'app-pop-up-create-sport-type',
  templateUrl: './pop-up-create-sport-type.component.html',
  styleUrls: ['./pop-up-create-sport-type.component.scss'],
})
export class PopUpCreateSportTypeComponent {
  sportType: RegisterSportType = {
    name: '',
  };
  form: FormGroup = new FormGroup({});

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: boolean,
    private fb: FormBuilder,
    private adminService: SportsTypesAdminServiceService,
    private dialogRef: MatDialogRef<any>,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.dialogRef.updateSize('600px');
    this.form = this.fb.group({
      name: ['', Validators.required],
    });
  }

  get name() {
    return this.form.get('name');
  }

  onSubmit() {
    if (this.form.valid) {
      this.sportType.name = this.form.value.name;
      this.adminService.createSportType(this.sportType).subscribe(
        (response) => {
          this.dialogRef.close();
          this.dialog.open(PopUpMsgComponent, {
            data: {
              msg: 'Creado correctamente',
            },
          });
        },
        (error) => {
          if (error.status === 400) {
            this.dialog.open(PopUpMsgComponent, {
              data: {
                msg: 'El tipo de deporte ya existe',
              },
            });
          }
        }
      );
    }
  }
}
