import { Component, OnInit } from '@angular/core';
import { AuthService } from '../service/auth.service';
import { FormGroup, FormBuilder,  Validators } from '@angular/forms';
import { ToastService } from '../service/toast.service';
import { Pattern } from '../enum/pattern';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { apiBase, register, apiToken } from '../const/api';
import { User } from '../entity/user';
import { Person } from '../entity/person';
import { Token } from '../entity/token';
import { params, httpHeadersToken } from '../const/params.tokenize';
import { From } from '../validator/from';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {
  formRegister: FormGroup;
  user: User;

  constructor(private auth: AuthService,
    private formBuilder: FormBuilder,
    private toast: ToastService,
    private http: HttpClient) { }

  ngOnInit() {
    this.init();
  }

  init() {
    this.formRegister = this.formBuilder.group({
      sponsor: ['', Validators.compose([Validators.required])],
      nick: ['', Validators.compose([Validators.required])],
      name: ['', Validators.compose([Validators.required])],
      email: ['', Validators.compose([Validators.required, Validators.pattern(Pattern.email)])],
      doc: ['', Validators.compose([Validators.required , Validators.pattern(Pattern.cpfOrCnpj)])],
      gender: ['', Validators.compose([Validators.required])],
      telephone: ['', Validators.compose([Validators.required , Validators.pattern(Pattern.telephone)])],
      password: ['', Validators.compose([Validators.required , Validators.minLength(6)])],
      confirmPassword: ['', Validators.compose([Validators.required , Validators.minLength(6)])]
    },
    {
      validator: [From.ConfirmpPassword]
    }); 
  }

  onClickSubmit(form) {    
      this.http.post<Token>(apiBase + apiToken, params, { headers: httpHeadersToken }).subscribe((token: Token) => {
        this.http.post<User>(apiBase + register, this.newUser(form), { headers: this.httpHeadersRequest(token) }).subscribe((r: any) => {                    
            window.location.href = "code"
        }, e => {          
          this.validator(e);
        });
      }, e => {
        console.log(e);
      });
  }

  validator(e: any) {    
    let m;
    e.error.forEach(element => {
      m += element.description +',<br>'       
      switch (element.field) {
        case 'email':                                  
          this.formRegister.patchValue({email: ''});  
          break;
        case 'nick':        
          this.formRegister.patchValue({nick: ''});      
          break;     
        case 'sponsor':        
          this.formRegister.patchValue({sponsor: ''});      
          break;     
        default: 
      }
    });         
    this.toast.showSingleToast(m);   
  }


  itemClick(item: string) {  
    switch (item) {
      case 'nick':
        this.toast.showSingleToast('O nick deve ser um nome único');
        break;
      case 'sponsor':
        this.toast.showSingleToast('Patrocinador, nick de indicação');
        break;
      case 'password':
        this.toast.showSingleToast('A senha deve conter o mínimo de 6 digitos');
        break;
      case 'confirm':
        this.toast.showSingleToast('As senhas devem ser exatamente iguais');
        break;
      case 'name':
        this.toast.showSingleToast('Informe o seu nome completo');
        break;
      case 'email':
        this.toast.showSingleToast('Informe um email válido. Será usado para confirmar o cadastro');
        break;
      case 'doc':
        this.toast.showSingleToast('Informe um documento de CPF ou CNPJ válido, somente números');
        break;
      case 'telephone':
        this.toast.showSingleToast('Telefone com ddd no formato xxxxxxxxxxx, somente números');
        break;
      case 'gender':
        this.toast.showSingleToast('Informe o seu gênero');
        break;
      default:
      
    }
  }

  private httpHeadersRequest(token: Token) {
    return new HttpHeaders({ 'Authorization': token.token_type + ' ' + token.access_token });
  }

  /*validate(form) {
    let field = form.value;
    if (field.password != field.confirmPassword) {
      this.toast.showSingleToast('As senhas não conferem');
      console.log(this.formRegister.hasError('different'));
      //this.formRegister.patchValue({password: '', confirmPassword: ''}); //limpa os campos citados no form     
      return false;
    }else{
      return true;
    }
  }*/

  newUser(form) {
    let field = form.value;
    this.user = new User();
    this.user.person = new Person();
    
    this.user.person.sponsor = field.sponsor;
    this.user.person.name = field.name;
    this.user.person.doc = field.doc;
    this.user.person.gender = field.gender;    
    this.user.person.telephone = field.telephone;
    this.user.nick = field.nick;
    this.user.email = field.email;
    this.user.password = field.password;

    return this.user;
  }
}
