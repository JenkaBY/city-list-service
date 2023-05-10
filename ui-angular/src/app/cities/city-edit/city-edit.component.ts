import {Component, OnInit} from "@angular/core";
import {Location} from '@angular/common';

import {CityService} from "../../core/services/city-service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {CityResponse} from "../../core/models/city";
import {debounceTime} from "rxjs";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ApiError} from "../../core/models/api-error";
import {Constant} from "../../core/configs/constant";

@Component({
  selector: 'city-edit-component',
  templateUrl: 'city-edit.component.html',
  styleUrls: [
    'city-edit.component.css'
  ]
})
export class CityEditComponent implements OnInit {

  constructor(private cityService: CityService,
              private route: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar,
              private location: Location) {
  }

  cityId!: number
  cityUpdatedState!: CityResponse;
  cityInitialState!: CityResponse;
  cityForm: FormGroup = this.formBuilder.group({});
  isSaving = false;
  isComponentLoaded = false;
  queryParams?: Params | null;

  ngOnInit(): void {
    this.cityId = Number(this.route.snapshot.paramMap.get('cityId'));
    this.queryParams = this.location.getState() as Params;
    this.cityService.getById(this.cityId).subscribe(
      {
        next: (res) => {
          this.cityForm = this.formBuilder.group(
            {
              name: new FormControl(res.name,
                [Validators.required]
              ),
              photo: new FormControl(res.photo,
                [Validators.required, Validators.pattern(Constant.URL_PATTERN)]),
            }
          );
          this.onFormChanged();
          this.cityUpdatedState = res;
          this.cityInitialState = { ...res};
        },
        error: (err: ApiError) => {
          this.notifyUser(err.message);
          void this.router.navigate(['/not-found']);
        },
        complete: () => this.isComponentLoaded = true
      }
    );
  }

  private onFormChanged() {
    this.cityForm.valueChanges
      .pipe(
        debounceTime(300)
      )
      .subscribe({
        next: (updated: CityResponse) => {
          this.cityUpdatedState.photo = updated.photo;
          this.cityUpdatedState.name = updated.name;
        },
      })
  }


  onSaveCity() {
    this.isSaving = true;
    this.cityService.updateCity(this.cityId, this.cityUpdatedState)
      .subscribe({
        next: (updatedCity: CityResponse) => {
          this.notifyUser(`The '${this.cityInitialState.name}' city has been updated`);
          this.goToCitiesPage();
        },
        error: (err: ApiError) => {
          this.notifyUser(`The '${this.cityInitialState.name}' city update has been failed`);
        },
        complete: () => this.isSaving = false
      });
  }

  onCancel(): void {
    this.goToCitiesPage();
  }

  private goToCitiesPage() {
    void this.router.navigate(['/cities'], {queryParams: this.queryParams});
  }

  private notifyUser(message: string) {
    this.snackBar.open(message);
  }
}
