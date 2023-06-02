import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Inject } from '@angular/core';

@Component({
  selector: 'app-pop-up-register',
  templateUrl: './pop-up-register.component.html',
  styleUrls: ['./pop-up-register.component.scss'],
})
export class PopUpRegisterComponent {
  msg: String = '';
  register: boolean = false;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.msg = this.data.msg;
    this.register = this.data.register;
  }

  back() {
    if (this.register) {
      this.router.navigate(['/login']);
    } else {
      this.router.navigate(['/register-athlete']);
    }
  }
}
