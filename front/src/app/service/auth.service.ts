import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { params, httpHeadersToken } from '../const/params.tokenize';
import { Token } from '../entity/token';
import { ToastService } from './toast.service';
import { Storage } from '@ionic/storage';
import { apiBase, apiToken, apiLogin } from '../const/api';
import { User } from '../entity/user';
import { NavController } from '@ionic/angular';


@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public isLogin: boolean;  
  public isProgress: boolean;  

  constructor(private http: HttpClient,
              private toast: ToastService,
              private storage: Storage,
              public navCtrl: NavController) 
              { 
                this.isProgress = false;  
              }

  login(email: string, password: string) {    
    this.autenticatedForToken(email, password);
  }

  autenticatedForToken(nick: string, password: string) {
    this.isProgress = true;
    this.http.post<Token>(apiBase + apiToken, params, { headers: httpHeadersToken }).subscribe((token: Token) => {      
      this.http.post(apiBase + apiLogin, this.param(nick, password), { headers: this.httpHeadersRequest(token) }).subscribe((r: any) => {        
        this.save('user', r.user);      
        this.navCtrl.navigateRoot('home');
        this.isProgress = false;          
      }, e => {
        this.isProgress = false;
        this.toast.showSingleToast(e.error.responseValidate.description);      
      });
    }, e => {
      this.isProgress = false;
      this.toast.showSingleToast("Servidor indisponível no momento");      
    });
  }

  private httpHeadersRequest(token: Token){
    return new HttpHeaders({ 'Authorization': token.token_type + ' ' + token.access_token });
  }

  private param(nick: string, password: string) {
    return new HttpParams().append('nick', nick).append('password', password);
  }
  private save(key: string, user: any) {
    return this.storage.set(key, user);
  }

  public update(key: string, user: any) {
    return this.save(key, user);
  }

  public remove(){
    this.storage.clear();
  }

  public getStorage(){
    return this.storage;
  }
}
