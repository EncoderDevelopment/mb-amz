import { AbstractControl } from '@angular/forms';

export class From {
    static ConfirmpPassword(control: AbstractControl) {
      let password = control.get('password').value;
      let confirmPassword = control.get('confirmPassword').value;
  
      if (password === confirmPassword) return null;
        control.get('confirmPassword').setErrors({ different: true });
    }    
  }
  