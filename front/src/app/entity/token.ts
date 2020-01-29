export class Token {
    constructor (
      readonly access_token: string,
      readonly token_type: String,     
      readonly refresh_token: string,
      readonly expires_in: number,
      readonly scope: string
    ) {}
  }
  