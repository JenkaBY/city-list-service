import {Component, VERSION} from '@angular/core';
import {StackItem} from "../core/models/stack-item";

@Component({
  selector: 'home-component',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  protected readonly VERSION = VERSION;

  backendStacks = [
    new StackItem('Java 17'),
    new StackItem('Gradle 8'),
    new StackItem('Spring Boot 3'),
    new StackItem('Spring Web'),
    new StackItem('Spring Data'),
    new StackItem('PostgreSQL 14'),
    new StackItem('Hibernate 6'),
    new StackItem('Liquibase'),
    new StackItem('Docker and Docker Compose'),
    new StackItem('Spring-doc and Swagger UI'),
    new StackItem('Mapstruct'),
    new StackItem('AssertJ'),
  ]
  frontendStacks = [
    new StackItem('Angular 15'),
    new StackItem('Material UI'),
  ]

}
