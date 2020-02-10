import { Component } from '@angular/core';

import { Platform, NavController } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {
  public appPages = [
    {
      title: 'Inicio',
      url: '/home',
      icon: 'home'

    },
    {
      title: 'Pontos',
      url: '/list',
      icon: 'analytics'

    },
    {
      title: 'Patrocinados',
      url: '/list',
      icon: 'medal'

    }
  ];

  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private auth: AuthService,
    public navCtrl: NavController
  ) {
    this.auth.isProgress = false;
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      setTimeout(() => {
        this.splashScreen.hide();
      }, 1000);
    });
  }

  exit() {
    this.auth.isProgress = true;       
    this.auth.isLogin = false;    
    this.auth.remove();

    setTimeout(() => {
      this.navCtrl.navigateRoot('/');     
    }, 1000);
    
  }
}
