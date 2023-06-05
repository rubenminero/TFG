import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthService } from 'src/app/services/auth/auth-service.service';
import { MatDialog } from '@angular/material/dialog';
import { PopUpMsgComponent } from '../pop-up-msg/pop-up-msg.component';

@Component({
  selector: 'app-pop-up-password',
  templateUrl: './pop-up-password.component.html',
  styleUrls: ['./pop-up-password.component.scss'],
})
export class PopUpPasswordComponent implements OnInit {
  form: FormGroup = new FormGroup({});

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: boolean,
    private fb: FormBuilder,
    private authService: AuthService,
    private dialogRef: MatDialogRef<any>,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.dialogRef.updateSize('600px');
    this.form = this.fb.group({
      oldpassword: ['', Validators.required],
      password: [
        '',
        Validators.compose([Validators.required, Validators.minLength(6)]),
      ],
      confirmpassword: [
        '',
        Validators.compose([Validators.required, Validators.minLength(6)]),
      ],
    });
  }

  get password() {
    return this.form.get('password');
  }

  get oldpassword() {
    return this.form.get('oldpassword');
  }

  get confirmpassword() {
    return this.form.get('confirmpassword');
  }
  onSubmit() {
    if (this.form.valid) {
      if (this.data) {
        this.authService
          .changePassword(
            this.authService.getId(),
            this.form.value.oldpassword,
            this.form.value.password,
            this.form.value.confirmpassword
          )
          .subscribe((response) => {
            this.dialogRef.close();
          });
      }
    }
  }
}
