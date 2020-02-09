import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RemenberPageRoutingModule } from './remenber-routing.module';

import { RemenberPage } from './remenber.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RemenberPageRoutingModule
  ],
  declarations: [RemenberPage]
})
export class RemenberPageModule {}
