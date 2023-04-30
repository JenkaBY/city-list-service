export interface City {
  name: string;
  photo: string;
}

export interface CityResponse extends City {
  id: number;
}
