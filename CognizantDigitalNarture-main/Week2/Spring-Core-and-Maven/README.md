# Spring Core / Maven Exercises — Solutions

Nine separate, self-contained Maven projects — one per exercise in `Spring_Core_Maven.docx`.
Each can be built and run independently.

To run any of exercises 1–8:
```
cd exerciseN
mvn compile exec:java -Dexec.mainClass=com.library.LibraryManagementApplication
```

To run exercise 9 (Spring Boot):
```
cd exercise9
mvn spring-boot:run
```

## exercise1 — Configuring a Basic Spring Application
Maven project, `pom.xml` with Spring Core, `applicationContext.xml` defining `bookRepository`/`bookService`
beans, main class that loads the context and confirms both beans exist. No wiring between them yet —
that's introduced in Exercise 2.

## exercise2 — Implementing Dependency Injection
Same as Exercise 1, plus `BookRepository` is wired into `BookService` via setter injection in
`applicationContext.xml`. Main class exercises the wired service (adds books, lists them).

## exercise3 — Implementing Logging with Spring AOP
Adds the Spring AOP + AspectJ dependency, a `LoggingAspect` (`@Around` advice) that logs each
`BookService` method's execution time, and registers/enables it in `applicationContext.xml`
via `<aop:aspectj-autoproxy/>`.

## exercise4 — Creating and Configuring a Maven Project
Focuses on `pom.xml`: dependencies for Spring Context, Spring AOP, and Spring WebMVC, plus the
Maven Compiler Plugin configured for Java 1.8. App logic mirrors Exercise 2 to prove the build works.

## exercise5 — Configuring the Spring IoC Container
Central `applicationContext.xml` defining the `BookService`/`BookRepository` beans and their
dependency (same shape as Exercise 2, presented as the app's central IoC configuration).

## exercise6 — Configuring Beans with Annotations
Replaces explicit `<bean>` XML with `<context:component-scan base-package="com.library"/>`, and
annotates `BookService` with `@Service` / `BookRepository` with `@Repository` (injected via `@Autowired`).

## exercise7 — Implementing Constructor and Setter Injection
`BookService` has both a constructor and a setter taking `BookRepository`; `applicationContext.xml`
configures both `<constructor-arg>` and `<property>` on the `bookService` bean, as the exercise asks.

## exercise8 — Implementing Basic AOP with Spring
`LoggingAspect` with separate `@Before` and `@After` advice methods (rather than Exercise 3's
`@Around` timing), registered the same way via an XML bean + `<aop:aspectj-autoproxy/>`.

## exercise9 — Creating a Spring Boot Application
A Spring Initializr-style Spring Boot project: Spring Web + Spring Data JPA + H2 dependencies, a
`Book` JPA entity, a `BookRepository` extending `JpaRepository`, and a `BookController` REST
controller with full CRUD endpoints under `/api/books`.

Test it once running on port 8080:
```
curl -X POST http://localhost:8080/api/books \
     -H "Content-Type: application/json" \
     -d '{"title":"Effective Java","author":"Joshua Bloch","isbn":"9780134685991"}'

curl http://localhost:8080/api/books
```

---
