import {Component, OnInit} from "@angular/core";
import {Location} from '@angular/common';

import {CityService} from "../../core/services/city-service";
import {ActivatedRoute, Params, Router} from "@angular/router";
import {CityResponse} from "../../core/models/city";
import {debounceTime} from "rxjs";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatSnackBar, MatSnackBarConfig} from "@angular/material/snack-bar";
import {ApiError} from "../../core/models/api-error";

@Component({
  selector: 'city-edit-component',
  templateUrl: 'city-edit.component.html',
  styleUrls: [
    'city-edit.component.css'
  ]
})
export class CityEditComponent implements OnInit {
  private URL_PATTERN = '(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?';

  constructor(private cityService: CityService,
              private route: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private snackBar: MatSnackBar,
              private location: Location) {
  }

  private readonly _snackBarConfig = {
    horizontalPosition: 'end',
    duration: 3000
  } as MatSnackBarConfig;
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
                [Validators.required, Validators.pattern(this.URL_PATTERN)]),
            }
          );
          this.onFormChanged();
          this.cityUpdatedState = res as CityResponse;
          this.cityInitialState = {...res as CityResponse};
        },
        error: (err: ApiError) => {
          this.notifyUser(err.message);
          this.router.navigate(['/not-found']).then();
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
    this.router.navigate(['/cities'], {queryParams: this.queryParams}).then();
  }

  private notifyUser(message: string) {
    this.snackBar.open(message, '', this._snackBarConfig);
  }
}
