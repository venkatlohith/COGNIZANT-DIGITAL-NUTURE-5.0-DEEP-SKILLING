# Microservices with API Gateway — Full Solution

Five independent Spring Boot 3.2.5 / Java 17 Maven projects implementing the
full exercise: two "business" microservices (account, loan), a Eureka
discovery server, a demo greet-service, and a Spring Cloud API Gateway with
a global logging filter.

> **Note:** built without internet/Maven-repo access in this sandbox, so it
> hasn't been compiled here. It follows current Spring Boot 3.2 / Spring
> Cloud 2023.0.1 idioms (`@EnableDiscoveryClient`, `spring-cloud-starter-gateway`,
> `GlobalFilter`) — the older `spring-cloud-starter-netflix-eureka-client` +
> Boot 2.5.5 shown in the PDF's screenshots is what this is a modern
> equivalent of. Run `mvn clean install` on each once you have normal
> internet access to catch any version drift.

## Services & ports

| Service                  | Port | Role |
|---------------------------|------|------|
| `eureka-discovery-server` | 8761 | Service registry |
| `account`                 | 8080 (default) | `GET /accounts/{number}` — dummy data |
| `loan`                    | 8081 | `GET /loans/{number}` — dummy data |
| `greet-service`           | 8080 (default) | `GET /greet` → "Hello World!!" |
| `api-gateway`              | 9090 | Routes to any registered service, logs every request |

`account` and `greet-service` both default to port 8080 — don't run them at
the same time unless you set a different `server.port` for one of them
(the exercise runs `account` + `loan` together first, then separately
`greet-service` + `api-gateway`; all four can coexist as long as only one
sits on 8080 at a time).

## Run order

```bash
cd eureka-discovery-server && mvn spring-boot:run   # 8761 — start first, wait for full startup
cd account                 && mvn spring-boot:run   # 8080
cd loan                    && mvn spring-boot:run   # 8081
cd greet-service           && mvn spring-boot:run   # 8080 — only if account isn't also running
cd api-gateway             && mvn spring-boot:run   # 9090
```

Check registration: open **http://localhost:8761** — "Instances currently
registered with Eureka" should list `ACCOUNT-SERVICE`, `LOAN-SERVICE`,
`GREET-SERVICE`, and `API-GATEWAY` once everything is up.

## Try it

Direct calls (bypassing the gateway):
```bash
curl localhost:8080/accounts/00987987973432
# {"number":"00987987973432","type":"savings","balance":234343}

curl localhost:8081/loans/H00987987972342
# {"number":"H00987987972342","type":"car","loan":400000,"emi":3258,"tenure":18}

curl localhost:8080/greet
# Hello World!!
```

Through the API Gateway (auto-routed by service name via
`spring.cloud.gateway.discovery.locator.enabled=true`):
```bash
curl localhost:9090/greet-service/greet
curl localhost:9090/account-service/accounts/00987987973432
curl localhost:9090/loan-service/loans/H00987987972342
```

Then check the `api-gateway` console — `LogFilter` (a `GlobalFilter`) logs
every request it forwards, e.g.:
```
=====>Request URL http://localhost:9090/greet-service/greet
```

## How the pieces fit together

- **`eureka-discovery-server`**: `@EnableEurekaServer`, with
  `register-with-eureka=false` / `fetch-registry=false` so it doesn't try to
  register with (or discover services from) itself.
- **`account`** / **`loan`** / **`greet-service`**: each has
  `@EnableDiscoveryClient` (implicit once the Eureka client dependency is on
  the classpath, but included explicitly per the exercise) and a
  `spring.application.name` — that name is what shows up in the Eureka
  dashboard and is the path segment the gateway routes on.
- **`api-gateway`**: `spring-cloud-starter-gateway` + the Eureka discovery
  client. `discovery.locator.enabled=true` auto-creates a route per
  registered service (`/{service-id}/**` → that service), so no manual
  route config is needed. `LogFilter implements GlobalFilter` applies to
  every route and logs the exchange's request URI before forwarding.
