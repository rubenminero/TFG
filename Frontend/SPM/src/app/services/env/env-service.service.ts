import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class EnvService {
  environment = {
    production: false,
    env: 'development',
    api_url: 'http://localhost:8080',
  };

  constructor() {}

  public getApiUrl() {
    return this.environment.api_url;
  }
}
