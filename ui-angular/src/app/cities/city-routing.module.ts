import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CitiesViewComponent} from "./cities-view/cities-view.component";


const routes: Routes = [
  {
    path: 'cities',
    component: CitiesViewComponent,
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CitiesRoutingModule {
}
