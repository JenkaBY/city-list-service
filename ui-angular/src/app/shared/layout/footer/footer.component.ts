import {Component, OnDestroy} from '@angular/core';
import {interval, Subscription} from 'rxjs';
import {HealthStatus} from '../../../core/models/health-status';
import {ApiHealthCheckService} from '../../../core/services/api-health-check.service';

@Component({
  selector: 'app-layout-footer',
  templateUrl: './footer.component.html',
  styleUrls: [
    './footer.component.css'
  ]
})
export class FooterComponent implements OnDestroy {
  constructor(private healthCheckService: ApiHealthCheckService) {
    const healthCheckSubscription = this.healthCheckService.getApiHealthStatus();
    this.healthStatusSub = interval(this.FIVE_SECONDS).subscribe((_) => {
      healthCheckSubscription.subscribe(healthStatus => this.healthStatus = healthStatus);
    });
  }

  private readonly FIVE_SECONDS = 5000;
  healthStatus = HealthStatus.loading;
  today: number = Date.now();
  healthStatusSub: Subscription;

  ngOnDestroy(): void {
    this.healthStatusSub.unsubscribe();
  }
}
