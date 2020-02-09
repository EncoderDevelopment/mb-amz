import { Person } from './person';
import { Role } from './role';


export class User {
   iduser: number;
   email: string;
   nick: string;
   password: string;
   status: string;
   role: Role
   person: Person;
  }
  