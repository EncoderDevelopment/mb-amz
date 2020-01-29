import { HttpParams, HttpHeaders } from '@angular/common/http';

export const constuserNameTokenize = '46ba9d9e87eafb1cb338498c35b0e925';
export const passwordTokenize = '3d3be52677dec456a1272f4c1c4b8cf7';
export const botoaUserName = 'fbf50455be15b488f6ac555eeb5ef16c';
export const botoaPassword = '48dbc81d8d9a9cb3bf29c2aadcc10ea3';
export const params = new HttpParams().append('grant_type', 'password').append('username', constuserNameTokenize).append('password', passwordTokenize);
export const httpHeadersToken = new HttpHeaders({ 'Authorization': 'Basic ' + btoa(botoaUserName + ':' + botoaPassword) });

