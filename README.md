# air-project

## Description

This project is a technical assessment made for Leroy Merlin.

Use case: [read here](./Air-Project.pdf).

## Requirements

- JDK 16.
- Gradle 7.2 (optional).
- Docker and `docker-compose`.

## Build

In order to build project and run all tests:

```shell
$ ./gradlew build

```

## Run

First you will need a database.

```shell
$ docker-compose up db
```

Then, run the app.

```shell
$ ./gradlew bootRun

```

If you don't have JDK 16 installed, you can use the dockerized application image.

```shell
$ ./gradlew jibDockerBuild
$ docker-compose up
```

## Try

Once the application and database are both running and ready:

```shell
$ http http://localhost:8080/api/flightplan
```

## Technologies

### Tools

- IntelliJ: https://www.jetbrains.com/
- Gradle: https://gradle.org/
- Jib: https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin
- Git: https://git-scm.com/
- Docker: https://www.docker.com/
- Docker Compose: https://docs.docker.com/compose/

### Frameworks and libraries

- SpringBoot: https://spring.io/projects/spring-boot
- Flyway: https://flywaydb.org/
- Hibernate: https://hibernate.org/
- Jackson: https://github.com/FasterXML/jackson
- Lombok: https://projectlombok.org/
- Junit: https://junit.org/junit5/
- AssertJ: https://assertj.github.io/doc/
- Testcontainers: https://www.testcontainers.org/

### Database

- PostgreSQL: https://www.postgresql.org/


## Improvements

- Better error handling (currently stores can have negative stocks).
- Better JPA entities and avoid repetitive requests to database.
- Better naming (functions, parameters and variables).
- More tests.
- Better documentation.
- Option to reset database without manually deleting table rows in database.
