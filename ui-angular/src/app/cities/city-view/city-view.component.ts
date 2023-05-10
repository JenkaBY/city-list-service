import {Component, Input} from "@angular/core";
import {CityResponse} from "../../core/models/city";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'city-view',
  templateUrl: 'city-view.component.html',
  styleUrls: ['city-view.component.css']
})
export class CityViewComponent {
  @Input() city!: CityResponse;
  @Input() isEdit = true;

  constructor(private router: Router,
              private route: ActivatedRoute) {
  }

  navigateToEdit() {
    void this.router.navigate(['/cities/', this.city.id], {
      state: this.route.snapshot.queryParams
    });
  }
}
