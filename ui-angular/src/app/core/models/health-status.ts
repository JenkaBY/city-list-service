export class HealthStatus {
  constructor(public status: string) {
  }

  public static UP = 'UP';
  public static DOWN = 'DOWN';
  public static NOT_STARTED = 'NOT_STARTED';
  public static LOADING = 'LOADING';


  get isUp(): boolean {
    return HealthStatus.UP === this.status;
  }

  static get loading(): HealthStatus {
    return new HealthStatus(this.LOADING);
  }

  static get notStarted(): HealthStatus {
    return new HealthStatus(this.NOT_STARTED);
  }
}
