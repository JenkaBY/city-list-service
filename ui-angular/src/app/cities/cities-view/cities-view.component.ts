import {Component, OnInit} from '@angular/core';
import {LoggingService} from '../../core/services/logging.service';
import {CityResponse} from "../../core/models/city";
import {Page} from "../../core/models/page";
import {CityService} from "../../core/services/city-service";
import {CitySearchRequest, SearchType} from "../../core/models/city-search-request";
import {PageEvent} from "@angular/material/paginator";
import {PageableRequest} from "../../core/models/pageable-request";
import {ActivatedRoute, Router} from "@angular/router";
import {Subject, switchMap} from "rxjs";

@Component({
  selector: 'cities-view',
  templateUrl: 'cities-view.component.html',
  styleUrls: ['cities-view.component.css']
})
export class CitiesViewComponent implements OnInit {
  constructor(private cityService: CityService, private loggingService: LoggingService,
              private router: Router, private route: ActivatedRoute) {
  }
  private readonly emptyEvent = undefined;
  citiesPage!: Page<CityResponse>;
  areCitiesLoaded = false;
  isError = false;

  private searchCityEvent$ = new Subject<void>();
  private citySearchParams = {} as CitySearchRequest;
  pageRequestParams = {
    size: 12,
    page: 0
  } as PageableRequest;

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.citySearchParams.name = params['name'] || '';
      this.citySearchParams.searchType = params['searchType'] || SearchType.LIKE_IGNORE_CASE;
      this.pageRequestParams.page = params['page'] || this.pageRequestParams.page;
      this.pageRequestParams.size = params['size'] || this.pageRequestParams.size;
    });
    this.initSearchCity();
    this.triggerSearchCityEvent();
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
    this.pageRequestParams.page = 0;
    this.triggerSearchCityEvent();
  }

  handlePageEvent($pageEvent: PageEvent) {
    this.pageRequestParams.page = $pageEvent.pageIndex;
    this.pageRequestParams.size = $pageEvent.pageSize;
    this.triggerSearchCityEvent()
  }

  private triggerSearchCityEvent() {
    this.searchCityEvent$.next(this.emptyEvent);
  }

  private initSearchCity(): void {
    this.searchCityEvent$.pipe(
      switchMap(() => this.cityService.getCities(this.mergeAndGetRequestParams()))
    )
      .subscribe(
        {
          next: (response: Page<CityResponse>) => {
            this.setCitiesPageResponse(response);
            this.updateRouteQueryParams();
            this.areCitiesLoaded = true;
            },
          error: () => {
            this.loggingService.error("Error occurred during fetching cities");
            this.isError = true;
          }
        }
      );
  }

  private updateRouteQueryParams() {
    void this.router.navigate([],
      {
        relativeTo: this.route,
        queryParams: this.mergeAndGetRequestParams(),
        queryParamsHandling: 'merge',
      });
  }
}
