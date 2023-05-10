import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';
import {CoreModule} from './core/core.module';
import {FooterComponent} from './shared/layout/footer/footer.component';
import {HeaderComponent} from './shared/layout/header/header.component';
import {SharedModule} from './shared/shared.module';
import {CitiesModule} from "./cities/cities.module";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NotFoundComponent} from "./shared/layout/not-found/not-found.component";
import {MAT_SNACK_BAR_DEFAULT_OPTIONS} from "@angular/material/snack-bar";
import SnackBarConfig from "./core/configs/snack-bar-config";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CoreModule,
    SharedModule,
    CitiesModule,
    AppRoutingModule
  ],
  providers: [
    {provide: MAT_SNACK_BAR_DEFAULT_OPTIONS, useValue: SnackBarConfig.DEFAULT}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
