import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root',
})
export class LoggingService {

  put(message: string) {
    if (environment.logLevel === "DEBUG") {
      console.log(message);
    }
  }

  error(message: string) {
    console.error(message);
  }
}
