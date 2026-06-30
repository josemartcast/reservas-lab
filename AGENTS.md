# AGENTS.md

## Project Layout

- The Git root contains the real Spring Boot project in `reservas-lab/`; run Maven commands from that subdirectory.
- Main entrypoint: `reservas-lab/src/main/java/com/joseangel/reservas/ReservasLabApplication.java`.
- Keep new Spring components under package `com.joseangel.reservas` unless you also adjust component scanning.
- `HELP.md` is Spring Initializr reference text and is ignored by Git; do not treat it as project-specific requirements.
- `target/` may exist locally but is build output and is ignored.

## Toolchain

- Java version is `21` from `pom.xml`.
- Use the Maven wrapper: Windows `./mvnw.cmd`, Unix `./mvnw`.
- Wrapper config uses Maven `3.9.16` with `distributionType=only-script`; `.mvn/wrapper/maven-wrapper.jar` is intentionally ignored.

## Commands

- From `reservas-lab/`, run all tests: `./mvnw.cmd test` on Windows, `./mvnw test` on Unix.
- Run one test class: `./mvnw.cmd -Dtest=ReservasLabApplicationTests test`.
- Start the app locally: `./mvnw.cmd spring-boot:run`.
- Package the app: `./mvnw.cmd package`.
- No repo-specific lint, formatter, typecheck, CI, or pre-commit config exists currently; do not invent npm/Gradle/task-runner commands.

## Current Stack Facts

- Spring Boot parent is `4.1.0`; dependencies are WebMVC, Validation, Spring Data JPA, H2 runtime, and matching Spring Boot test starters.
- This is Servlet MVC, not WebFlux.
- `application.properties` only sets `spring.application.name=reservas-lab`; tests currently use Spring Boot's auto-configured in-memory H2 datasource.
- Existing test coverage is a single `@SpringBootTest` context load in `ReservasLabApplicationTests`.
