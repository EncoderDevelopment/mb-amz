import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastService } from '../service/toast.service';
import { Pattern } from '../enum/pattern';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {
  formRegister: FormGroup;
  errorNick: string;
  errorPassword: string;

  constructor(private auth: AuthService,
    private formBuilder: FormBuilder,
    private toast: ToastService) { }

  ngOnInit() {
    this.init();
  }

  init() {    
    this.formRegister = this.formBuilder.group({
      sponsor: ['', Validators.compose([Validators.required])],
      nick: ['', Validators.compose([Validators.required])],
      name: ['', Validators.compose([Validators.required])],
      email: ['', Validators.compose([Validators.required, Validators.pattern(Pattern.email)])],
      doc: ['', Validators.compose([Validators.required])],
      gender: ['',  Validators.compose([Validators.required, Validators.pattern(Pattern.gender)])],
      phone: ['', Validators.compose([Validators.required])],      
      password: ['', Validators.compose([Validators.required])],
      confirmPassword: ['', Validators.compose([Validators.required])]           
   }); 
  }

  onClickSubmit(form) {

  }

  cssError() {
    return 'item-interactive item-input item-label item-label-floating ion-color ion-color-dark item md in-list ion-focusable hydrated ion-pristine ion-invalid ion-touched';
  }
}
