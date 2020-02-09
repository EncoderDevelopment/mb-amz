import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { params, httpHeadersToken } from '../const/params.tokenize';
import { Token } from '../entity/token';
import { ToastService } from './toast.service';
import { Storage } from '@ionic/storage';
import { apiBase, apiToken, apiLogin } from '../const/api';
import { User } from '../entity/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public isLogin: boolean;  

  constructor(private http: HttpClient,
    private toast: ToastService,
    private storage: Storage) { }

  login(email: string, password: string) {    
    this.autenticatedForToken(email, password);
  }

  autenticatedForToken(nick: string, password: string) {
    this.http.post<Token>(apiBase + apiToken, params, { headers: httpHeadersToken }).subscribe((token: Token) => {      
      this.http.post(apiBase + apiLogin, this.param(nick, password), { headers: this.httpHeadersRequest(token) }).subscribe((r: any) => {        
        this.save('user', r.user);
        this.toast.showSingleToast(r.responseValidate.description);
        setTimeout(() => {
            window.location.href = "home"  
        }, 2000);
        
      }, e => {
        this.toast.showSingleToast(e.error.responseValidate.description);      
      });
    }, e => {
      this.toast.showSingleToast(e.message);      
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
    this.storage.remove('user');
  }

  public getStorage(){
    return this.storage;
  }
}
