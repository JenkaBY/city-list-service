import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule} from '@angular/router';
import {DefaultImageDirective} from "./directive/default-image-directive.directive";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    MatSnackBarModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule
  ],
  declarations: [
    DefaultImageDirective
  ],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    DefaultImageDirective,
    MatSnackBarModule,
    MatIconModule,
    MatToolbarModule,
    MatButtonModule
  ]
})
export class SharedModule {
}
