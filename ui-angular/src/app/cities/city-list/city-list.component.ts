import {Component, Input} from '@angular/core';
import {CityResponse} from "../../core/models/city";

@Component({
  selector: 'cities-list',
  templateUrl: 'city-list.component.html',
  styleUrls: ['city-list.component.css']
})
export class CityListComponent {

  @Input() cities?: CityResponse[];
}
