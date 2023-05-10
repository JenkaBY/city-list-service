import {Component, OnDestroy} from '@angular/core';
import {interval, Subscription} from 'rxjs';
import {ApiHealthCheckService} from '../../../core/services/api-health-check.service';
import {Constant} from "../../../core/configs/constant";
import {HealthStatus} from "../../../core/models/health-status";

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
    this.healthStatusSub = interval(Constant.FIVE_SECONDS).subscribe((_) => {
      healthCheckSubscription.subscribe(healthStatus => this.healthStatus = healthStatus);
    });
  }

  healthStatus = HealthStatus.loading;
  today: number = Date.now();
  healthStatusSub: Subscription;

  ngOnDestroy(): void {
    this.healthStatusSub.unsubscribe();
  }
}
