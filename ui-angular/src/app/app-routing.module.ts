import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {CityEditComponent} from "./cities/city-edit/city-edit.component";
import {NotFoundComponent} from "./shared/layout/not-found/not-found.component";

const routes: Routes = [
  {
    path: '',
    title: 'Home Page',
    loadChildren: () => import('./home/home.module')
      .then(module => module.HomeModule)
  },
  {
    path: 'cities',
    title: 'Cities',
    loadChildren: () => import('./cities/cities.module')
      .then(module => module.CitiesModule)
  },
  {
    path: 'cities/:cityId',
    title: 'City edit',
    component: CityEditComponent
  },
  {
    path: '**',
    title: 'Page not found',
    component: NotFoundComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    // preload all modules; optionally we could
    // implement a custom preloading strategy for just some
    // of the modules
    preloadingStrategy: PreloadAllModules
  })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
