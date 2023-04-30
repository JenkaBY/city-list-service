import {Injectable} from '@angular/core';

import {Observable, of} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {HealthStatus} from '../models/health-status';
import {ApiService} from './api.service';
import {LoggingService} from './logging.service';

@Injectable({
  providedIn: 'root',
})
export class ApiHealthCheckService {
  private healthUrl = '/actuator/health';

  constructor(private loggingService: LoggingService,
              private apiService: ApiService) {
  }

  getApiHealthStatus(): Observable<HealthStatus> {
    return this.apiService.get(this.healthUrl)
      .pipe(
        catchError((err: any) => {
          this.loggingService.error(`Catch an error during HealthCheck, msg: ${err.message | err.error}, status: ${err.status}`);
          return of(HealthStatus.notStarted);
        })
      );
  }
}
