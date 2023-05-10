import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {CitiesRoutingModule} from "./city-routing.module";
import {CityListComponent} from "./city-list/city-list.component";
import {MatTabsModule} from "@angular/material/tabs";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {CitiesViewComponent} from "./cities-view/cities-view.component";
import {MatGridListModule} from "@angular/material/grid-list";
import {CitySearchComponent} from "./city-search/city-search.component";
import {CityEditComponent} from "./city-edit/city-edit.component";
import {MatInputModule} from "@angular/material/input";
import {MatSelectModule} from "@angular/material/select";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatButtonModule} from "@angular/material/button";
import {CityViewComponent} from "./city-view/city-view.component";
import {MatCardModule} from "@angular/material/card";
import {MatPaginatorModule} from "@angular/material/paginator";
import {NgOptimizedImage} from "@angular/common";


@NgModule({
  imports: [
    SharedModule,
    CitiesRoutingModule,
    MatTabsModule,
    MatProgressSpinnerModule,
    MatGridListModule,
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule,
    MatButtonModule,
    MatCardModule,
    MatPaginatorModule,
    NgOptimizedImage
  ],
  declarations: [
    CitiesViewComponent,
    CityListComponent,
    CitySearchComponent,
    CityEditComponent,
    CityViewComponent
  ]
})
export class CitiesModule {
}
