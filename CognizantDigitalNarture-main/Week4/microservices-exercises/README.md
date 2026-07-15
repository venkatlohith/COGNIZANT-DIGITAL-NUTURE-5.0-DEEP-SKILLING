# Microservices with Spring Boot 3.0 — Full Solutions

Complete, runnable solutions for all 4 exercises. Each service is an independent
Maven project (own `pom.xml`), built with Spring Boot 3.2.5 / Java 17.

> **Note:** these projects were generated in a sandbox without internet/Maven
> repository access, so they have **not** been compiled here. The code follows
> standard, current Spring Boot 3 / Spring Cloud 2023.0.1 patterns and should
> build cleanly with `mvn clean install` once you have normal internet access
> for dependency resolution. Double-check dependency versions against the
> latest Spring Cloud release train if time has passed since this was written.

---

## Exercise 1 — User & Order Management (`exercise1-user-order/`)

- **user-service** (port 8081): CRUD REST API for users, H2 in-memory DB
  (swap to the commented-out MySQL config in `application.yml` for real use).
- **order-service** (port 8082): CRUD REST API for orders. Calls
  `user-service` two ways, per the exercise's "WebClient or OpenFeign" choice:
  - `UserClient` — declarative **OpenFeign** client (used by default in `OrderService`)
  - `UserWebClientService` — reactive **WebClient** alternative (same call, different style)

Run:
```bash
cd user-service  && mvn spring-boot:run   # port 8081
cd order-service && mvn spring-boot:run   # port 8082
```
Try it:
```bash
curl -X POST localhost:8081/api/users -H "Content-Type: application/json" \
  -d '{"name":"Ada","email":"ada@example.com","phone":"123"}'

curl -X POST localhost:8082/api/orders -H "Content-Type: application/json" \
  -d '{"userId":1,"product":"Laptop","quantity":1,"totalPrice":999.99}'

curl localhost:8082/api/orders/1/details   # enriched with user info
```

---

## Exercise 2 — Inventory Management + Service Discovery (`exercise2-inventory-eureka-config/`)

- **eureka-server** (port 8761): Netflix Eureka discovery server.
- **config-server** (port 8888): Spring Cloud Config Server, `native` profile
  serving YAML from `config-repo/` (swap in a real git URI for production).
- **product-service** (port 8083): registers with Eureka, pulls config from
  config-server via `bootstrap.yml`.
- **inventory-service** (port 8084): registers with Eureka; calls
  `product-service` through a Feign client resolved **by service name**
  (no hardcoded host/port) — this is the payoff of service discovery.

Run in this order (each needs the previous one up):
```bash
cd eureka-server  && mvn spring-boot:run   # 8761 - start first
cd config-server  && mvn spring-boot:run   # 8888 - start second
cd product-service   && mvn spring-boot:run   # 8083
cd inventory-service && mvn spring-boot:run   # 8084
```
Check the Eureka dashboard at http://localhost:8761 to confirm both services registered.

Try it:
```bash
curl -X POST localhost:8083/api/products -H "Content-Type: application/json" \
  -d '{"name":"Widget","description":"A widget","price":9.99,"stock":100}'

curl -X POST localhost:8084/api/inventory -H "Content-Type: application/json" \
  -d '{"productId":1,"warehouseCode":"WH-01","quantityOnHand":100,"reorderThreshold":10}'

curl localhost:8084/api/inventory/product/1        # enriched with live product data
curl -X PATCH "localhost:8084/api/inventory/product/1/adjust?delta=-5"
```

---

## Exercise 3 — API Gateway (`exercise3-api-gateway/`)

- **customer-service** (port 8091) and **billing-service** (port 8092): trivial
  downstream services the gateway routes to.
- **api-gateway** (port 8090): Spring Cloud Gateway with:
  - **Path rewriting**: external `/api/customers/**` → internal `/customers/**`
    (same pattern for billing) via the `RewritePath` filter.
  - **Rate limiting**: Redis-backed `RequestRateLimiter` (token bucket,
    5 req/s refill, burst of 10), keyed by client IP (`RateLimiterConfig`).
    Requires a local Redis instance (`docker run -p 6379:6379 redis`).
  - **Caching**: custom `CachingFilter` (Caffeine, in-memory, 30s TTL) caches
    successful GET responses, adding an `X-Cache: HIT|MISS` header so you can
    see it working.

Run:
```bash
docker run -d -p 6379:6379 redis   # rate limiter needs Redis
cd customer-service && mvn spring-boot:run   # 8091
cd billing-service  && mvn spring-boot:run   # 8092
cd api-gateway       && mvn spring-boot:run  # 8090
```
Try it:
```bash
curl localhost:8090/api/customers/1     # routed + rewritten to customer-service
curl localhost:8090/api/billing/1       # routed + rewritten to billing-service
curl -i localhost:8090/api/customers/1  # check X-Cache header on repeat calls
for i in $(seq 1 20); do curl -s -o /dev/null -w "%{http_code}\n" localhost:8090/api/customers/1; done
# ^ eventually you'll see 429 Too Many Requests once the rate limit is exceeded
```

---

## Exercise 4 — Resilient Payment Service (`exercise4-circuit-breaker/`)

- **payment-service** (port 8095): calls a (deliberately unreachable, to
  simulate "slow") third-party payment API, protected by **Resilience4j**:
  - `@TimeLimiter` gives up after 3s.
  - `@CircuitBreaker` trips OPEN after 50% failure/slow-call rate over a
    10-call sliding window, and short-circuits straight to the fallback
    while OPEN — no more calls hit the flaky API until it half-opens again.
  - `chargeFallback(...)` returns a graceful `PENDING_FALLBACK` response
    instead of failing the caller.
  - `CircuitBreakerEventLogger` subscribes to Resilience4j's event
    publisher and logs every state transition, error, and "call not
    permitted" event — this is the "log and monitor fallback events"
    requirement. Actuator also exposes `/actuator/circuitbreakers` and
    `/actuator/circuitbreakerevents` for live inspection, plus Prometheus
    metrics via `/actuator/metrics`.

Run:
```bash
cd payment-service && mvn spring-boot:run   # 8095
```
Try it (the target URL is unreachable by design, so this will demonstrate
the timeout → fallback path immediately; call it several times in a row to
watch the circuit breaker trip open in the logs):
```bash
curl -X POST localhost:8095/api/payments/charge -H "Content-Type: application/json" \
  -d '{"orderId":"ORD-1","amount":49.99,"cardToken":"tok_test"}'

curl localhost:8095/actuator/circuitbreakers
curl localhost:8095/actuator/circuitbreakerevents
```

---

## General notes

- All services use **Java 17** and **Spring Boot 3.2.5** with **Spring Cloud 2023.0.1**.
- H2 in-memory databases are used for fast local testing; every `application.yml`
  has the equivalent MySQL/PostgreSQL config commented out — just uncomment and
  point at a real database for a production-style setup.
- Lombok is used throughout (`@Data`, `@RequiredArgsConstructor`, etc.) to keep
  boilerplate down — make sure your IDE has the Lombok plugin installed.
