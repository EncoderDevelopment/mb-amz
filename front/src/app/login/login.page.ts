import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { ToastService } from '../service/toast.service';
import { Router } from '@angular/router';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  formUser: FormGroup;

  constructor(private auth: AuthService,
    private formBuilder: FormBuilder,
    private toast: ToastService,
    public navCtrl: NavController) {    
      this.auth.isProgress = false;
      this.redirect();
     }

  ngOnInit() {      
    this.init();
  }

  init() {    
    this.formUser = this.formBuilder.group({
      nick: ['', Validators.compose([Validators.required])],
      password: ['', Validators.compose([Validators.required, Validators.minLength(6)])]
    });    
  }

  async redirect(){
    return await this.auth.getStorage().get('user').then((user) => {     
      if (user != null){
        setTimeout(() => {
          this.navCtrl.navigateRoot('home');        
        }, 1000);
        
      }
    }).catch(()=>{
      
    });    
  }

  onClickSubmit(form) {    
    this.auth.login(form.nick, form.password);       
  }

}
