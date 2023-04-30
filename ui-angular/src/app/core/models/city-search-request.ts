export interface CitySearchRequest {
  name: string;
  searchType: string;
}

export class SearchType {
  public static LIKE_IGNORE_CASE = "LIKE_IGNORE_CASE";
  public static STARTS_WITH_IGNORE_CASE = "STARTS_WITH_IGNORE_CASE";
}
