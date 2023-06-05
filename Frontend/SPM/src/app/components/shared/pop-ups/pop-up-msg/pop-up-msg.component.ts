import { Component } from '@angular/core';
import { Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-pop-up-msg',
  templateUrl: './pop-up-msg.component.html',
  styleUrls: ['./pop-up-msg.component.scss'],
})
export class PopUpMsgComponent {
  msg: String = '';

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {}

  ngOnInit(): void {
    this.msg = this.data.msg;
  }

  back() {
    this.data.dialogRef.close();
  }
}
