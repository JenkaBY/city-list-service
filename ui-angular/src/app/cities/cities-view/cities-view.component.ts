import {Component, OnInit} from '@angular/core';
import {LoggingService} from '../../core/services/logging.service';
import {CityResponse} from "../../core/models/city";
import {Page} from "../../core/models/page";
import {CityService} from "../../core/services/city-service";
import {CitySearchRequest, SearchType} from "../../core/models/city-search-request";
import {PageEvent} from "@angular/material/paginator";
import {PageableRequest} from "../../core/models/pageable-request";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'cities-view',
  templateUrl: 'cities-view.component.html',
  styleUrls: ['cities-view.component.css']
})
export class CitiesViewComponent implements OnInit {
  constructor(private cityService: CityService, private loggingService: LoggingService,
              private router: Router, private route: ActivatedRoute) {
  }

  citiesPage!: Page<CityResponse>;
  areCitiesLoaded = false;
  isError = false;
  private citySearchParams = {} as CitySearchRequest;
  pageRequestParams = {
    size: 12,
    page: 0
  } as PageableRequest;

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.citySearchParams.name = params['name'] || '';
      this.citySearchParams.searchType = params['searchType'] || SearchType.LIKE_IGNORE_CASE;
      this.pageRequestParams.page = params['page'] || 0;
      this.pageRequestParams.size = params['size'] || 12;
    });
    this.getCities();
  }

  getCities(): void {
    this.cityService.getCities(this.mergeAndGetRequestParams())
      .subscribe(
        {
          next: (response: Page<CityResponse>) => this.setCitiesPageResponse(response),
          error: () => {
            this.isError = true;
          },
          complete: () => this.areCitiesLoaded = true
        }
      );
  }

  private mergeAndGetRequestParams() {
    return {
      ...this.citySearchParams,
      ...this.pageRequestParams,
    };
  }

  private setCitiesPageResponse(response: Page<CityResponse>): void {
    this.citiesPage = response;
  }

  handleCitySearchEvent($citySearchEvent: CitySearchRequest) {
    this.citySearchParams = $citySearchEvent;
    this.getCities();
    this.router.navigate([],
      {
        relativeTo: this.route,
        queryParams: this.mergeAndGetRequestParams(),
        queryParamsHandling: 'merge',
      }).then()
  }

  handlePageEvent($pageEvent: PageEvent) {
    this.loggingService.put(JSON.stringify($pageEvent));
    this.pageRequestParams.page = $pageEvent.pageIndex;
    this.pageRequestParams.size = $pageEvent.pageSize;
    this.getCities();
    this.router.navigate([],
      {
        relativeTo: this.route,
        queryParams: this.mergeAndGetRequestParams(),
        queryParamsHandling: 'merge',
        // preserve the existing query params in the route
        // skipLocationChange: true
        // do not trigger navigation
      }).then()
  }
}
