import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable, throwError} from 'rxjs';

import {catchError} from 'rxjs/operators';
import {environment} from '../../../environments/environment';
import {LoggingService} from './logging.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  constructor(private http: HttpClient, private loggingService: LoggingService) {
  }

  private handleError<T>(url: string, http: string, result?: T) {
    return (error: any): Observable<T> => {

      this.loggingService.error(`Requesting ${http}:${url} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      // return of(result as T);
      return throwError(error.error);
    };
  }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    const url = `${environment.apiUrl}${path}`;
    return this.http.get(url, {params: params})
      .pipe(catchError(this.handleError(url, 'GET')));
  }

  put(path: string, body: Object = {}): Observable<any> {
    const url = `${environment.apiUrl}${path}`;
    return this.http.put(
      url,
      JSON.stringify(body),
      {headers: this.headers}
    ).pipe(catchError(this.handleError(url, 'PUT')));
  }

  post(path: string, body: Object = {}): Observable<any> {
    const url = `${environment.apiUrl}${path}`;
    return this.http.post(
      url,
      JSON.stringify(body),
      {headers: this.headers}
    ).pipe(catchError(this.handleError(url, 'POST')));
  }

  delete(path: string): Observable<any> {
    const url = `${environment.apiUrl}${path}`;
    return this.http.delete(`${environment.apiUrl}${path}`)
      .pipe(catchError(this.handleError(url, 'DELETE')));
  }

  private get headers(): HttpHeaders {
    return new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
  }
}
