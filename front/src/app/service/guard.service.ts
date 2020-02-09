import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class GuardService implements CanActivate{
  constructor(private auth: AuthService) { }
  user:any;

  async canActivate(route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot) {
    return await this.auth.getStorage().get('user').then((user) => {     
      if (user != null) {        
        this.auth.isLogin = true;
        return true;
      } else {
        this.auth.isLogin = false;
        window.location.href = "/"
        return false;
      }
    }).catch(()=>{
      return false;
    });    
  }
}
