import {Injectable} from '@angular/core';

import {Observable} from 'rxjs';
import {ApiService} from './api.service';
import {LoggingService} from './logging.service';
import {City, CityResponse} from "../models/city";
import {Page} from "../models/page";

@Injectable({
  providedIn: 'root',
})
export class CityService {
  private cityUrl = '/api/v1/cities';

  constructor(private loggingService: LoggingService,
              private apiService: ApiService) {
  }

  getCities(params?: any): Observable<Page<CityResponse>> {
    return this.apiService.get(this.cityUrl, params);
  }

  updateCity(cityId: number, city: City): Observable<CityResponse> {
    const path = `${this.cityUrl}/${cityId}`;
    return this.apiService.put(path, city);
  }

  getById(cityId: number): Observable<CityResponse> {
    const path = `${this.cityUrl}/${cityId}`;
    return this.apiService.get(path);
  }
}
