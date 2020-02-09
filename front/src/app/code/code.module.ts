import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { CodePageRoutingModule } from './code-routing.module';
import { CodePage } from './code.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    CodePageRoutingModule
  ],
  declarations: [CodePage]
})
export class CodePageModule {}
