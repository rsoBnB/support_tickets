# RSO: Orders microservice

## Prerequisites

```bash
docker run -d --name rso-reviews -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=review -p 5433:5432 postgres:latest
```

## Run application in Docker

```bash
docker run -p 8081:8081 jmezna/rso-reviews
```

## Metrics

[Prometheus Operator](https://coreos.com/operators/prometheus/docs/latest/user-guides/getting-started.html)