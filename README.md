# clients-java-api

## About the API

An API for client management. It is built with Java, Spring Boot, and Spring Framework. The API main URL `/api-clients/v1`.

## Features

This API provides HTTP endpoint's and tools for the following:

* Create a client: `POST/api-clients/v1/clients`
* Update a client: `PUT/api-clients/v1/clients`
* Delete a client (by id): `DELETE/api-clients/v1/clients/1`
* Find a unique client by id: `GET/api-travelclientss/v1/clients/1`

### Technologies used

This project was developed with:

* **Java 11 (Java Development Kit - JDK: 11.0.9)**
* **Spring Boot 2.3.7**
* **Spring Admin Client 2.3.1**
* **Maven**
* **JUnit 5**
* **Surfire**
* **PostgreSQL 13**
* **Flyway 6.4.4**
* **Swagger 3.0.0**
* **Model Mapper 2.3.9**
* **Heroku**
* **EhCache**
* **Bucket4j 4.10.0**
* **Partialize 20.05**

### Compile and Package

The API also was developed to run with an `jar`. In order to generate this `jar`, you should run:

```bash
mvn package
```

It will clean, compile and generate a `jar` at target directory, e.g. `clients-java-api-1.0.0-SNAPSHOT.jar`

### Execution

You need to have **PostgreSQL 9.6.17 or above** installed on your machine to run the API on `dev` profile. After installed, on the `pgAdmin` create a database named `clients`. If you don't have `pgAdmin` installed you can run on the `psql` console the follow command:

```sql
CREATE database clients;
```

After creating the API database, you need to add your **Postgres** root `username` and `password` in the `application.properties` file on `src/main/resource`. The lines that must be modified are as follows:

```properties
spring.datasource.username=
spring.datasource.password=
```

When the application is running **Flyway** will create the necessary tables for the creation of the words and the execution of the compare between the end-points. In the test profile, the application uses **H2** database (data in memory).

### Test

* For unit test phase, you can run:

```bash
mvn test
```

* To run all tests (including Integration Tests):

```bash
mvn integration-test
```

### Run

In order to run the API, run the jar simply as following:

```bash
java -jar clients-java-api-1.0.0-SNAPSHOT.jar --spring.profiles.active=dev
```
    
or

```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

By default, the API will be available at [http://localhost:8080/api-clients/v1](http://localhost:8080/api-clients/v1)

### Documentation

* Swagger (development environment): [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)


### License

This API is licensed under the MIT License.
