export interface ApiError {
  message: string;
  timestamp: number;
  violations?: Violation[];

}

export interface Violation {
  field: string;
  message: string;
}
