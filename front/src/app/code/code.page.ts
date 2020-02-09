import { Component, OnInit} from '@angular/core';
import { AuthService } from '../service/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastService } from '../service/toast.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { apiBase, apiToken, register, code } from '../const/api';
import { params, httpHeadersToken } from '../const/params.tokenize';
import { User } from '../entity/user';
import { Token } from '../entity/token';



@Component({
  selector: 'app-code',
  templateUrl: './code.page.html',
  styleUrls: ['./code.page.scss'],
})
export class CodePage implements OnInit {  
  formCode: FormGroup;
  token: string;

  constructor(private auth: AuthService,
    private formBuilder: FormBuilder,
    private toast: ToastService,
    private http: HttpClient) { }

  ngOnInit() {   
    this.init();
  }

  init() {
    this.formCode = this.formBuilder.group({
      code: ['', Validators.compose([Validators.required , Validators.min(100000), Validators.max(999999)])]     
    }); 

  }

  onClickSubmit(form) {    
    console.log(form.value.code);
    this.http.post<Token>(apiBase + apiToken, params, { headers: httpHeadersToken }).subscribe((token: Token) => {
      this.http.post<User>(apiBase + code, form.value.code, { headers: this.httpHeadersRequest(token) }).subscribe((r: any) => {
        console.log(r);     
        this.toast.showSingleToast(r.responseValidate.description);   
        setTimeout(() => {
          window.location.href = "/"
        }, 2000);
      }, e => {                  
        this.toast.showSingleToast(e.error.responseValidate.description);
        this.formCode.patchValue({code: ''});         
      });
    }, e => {
      this.toast.showSingleToast('Não foi possível acessar o servidor de dados no momento. Verifique a sua conexão com a internet e tente novamente');
    });
  }  

  private httpHeadersRequest(token: Token) {
    return new HttpHeaders({ 'Authorization': token.token_type + ' ' + token.access_token });
  }
  
  itemClick(){
    this.toast.showSingleToast('O token é o código 6 digitos enviado para o seu email');
  }

  keyup(){
    this.token = '' + this.formCode.get('code').value;    
    if(this.token.length >= 6)
      this.formCode.patchValue({code: this.token.slice(0,6)});               
  }

}
