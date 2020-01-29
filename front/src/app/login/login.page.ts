import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastService } from '../service/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  formUser: FormGroup;
  errorNick: string;
  errorPassword: string;

  constructor(private auth: AuthService,
    private formBuilder: FormBuilder,
    private toast: ToastService) { }

  ngOnInit() {   
    this.init();
  }


  init() {    
    this.formUser = this.formBuilder.group({
      nick: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(6)])]
    });
  }

  onClickSubmit(form) {
    this.auth.login(form.nick, form.password);       

    /*if (form.nick.length <= 0 && form.password.length <= 0) {
      this.toast.showSingleToast('Todos os campos são obrigatórios');
      this.errorNick = this.cssError();
      this.errorPassword = this.cssError();
    } else if (form.password.length < 6) {
      this.toast.showSingleToast('Sua senha deve conter o minimo de 6 dígitos');
      this.errorPassword = this.cssError();
    } else {

      //this.authService.login(form.email, form.password);               

    }*/
  }

  /*itemClick(item: string){
    console.log(item);
    if (item=='nick')
      this.toast.showSingleToast('O nick deve ser um nome unico');

    if (item=='password')
      this.toast.showSingleToast('A senha deve conter o mínimo de 6 digitos');

  }*/

  cssError() {
    return 'item-interactive item-input item-label item-label-floating ion-color ion-color-dark item md in-list ion-focusable hydrated ion-pristine ion-invalid ion-touched';
  }

}
