# CLAUDE.md

This file provides guidance to Claude Code when working with this project.

## Project Overview

MSA (Microservice Architecture) 기반 Spring Boot 멀티 모듈 프로젝트입니다.

## Tech Stack

- Java 21
- Spring Boot 3.5.10
- Spring Cloud 2025.0.0
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka
- Spring Kafka
- Gradle (Groovy DSL)
- JUnit 5 for testing

## Build Commands

```bash
# Build all modules
./gradlew build

# Build specific module
./gradlew :service-member:build

# Run specific service
./gradlew :infrastructure-eureka:bootRun
./gradlew :infrastructure-gateway:bootRun
./gradlew :service-member:bootRun
./gradlew :service-payment:bootRun
./gradlew :service-content:bootRun

# Run tests
./gradlew test

# Clean build artifacts
./gradlew clean
```

## Project Structure

```
side-project/
├── common/                          # 공통 모듈
│   ├── common-kafka/                # Kafka 공통 설정 및 유틸
│   │   └── src/main/java/com/study/common/kafka/
│   └── common-core/                 # 공통 코드 (DTO, Utils 등)
│       └── src/main/java/com/study/common/core/
├── service-member/                  # 회원 서비스 (port: 8081) - Hexagonal Architecture
│   └── src/main/java/com/study/member/
├── service-payment/                 # 결제 서비스 (port: 8082) - Hexagonal Architecture
│   └── src/main/java/com/study/payment/
├── service-content/                 # 콘텐츠 서비스 (port: 8083) - Hexagonal Architecture
│   └── src/main/java/com/study/content/
├── infrastructure-gateway/          # API Gateway (port: 8080)
│   └── src/main/java/com/study/gateway/
└── infrastructure-eureka/           # Eureka Server (port: 8761)
    └── src/main/java/com/study/eureka/
```

## Module Dependencies

- `common-kafka`: Spring Kafka 공통 설정
- `common-core`: 공통 유틸, DTO, Exception 등
- `service-*`: common-core, common-kafka 의존
- `infrastructure-gateway`: Eureka Client
- `infrastructure-eureka`: Eureka Server (독립)

## Service Ports

| Service | Port |
|---------|------|
| Eureka Server | 8761 |
| API Gateway | 8080 |
| Member Service | 8081 |
| Payment Service | 8082 |
| Content Service | 8083 |

## Startup Order

1. `infrastructure-eureka` (Eureka Server)
2. `infrastructure-gateway` (API Gateway)
3. `service-member`, `service-payment`, `service-content` (순서 무관)

## Code Conventions

- Follow standard Java naming conventions
- Use constructor injection for Spring dependencies
- Place configuration in `application.yml`
- Write unit tests for service layer, integration tests for controllers
- 서비스 간 통신: Kafka (비동기), Feign Client (동기)

## API Gateway Routes

| Path | Service |
|------|---------|
| `/api/members/**` | service-member |
| `/api/payments/**` | service-payment |
| `/api/contents/**` | service-content |

## Hexagonal Architecture (서비스 모듈)

service-member, service-payment, service-content 모듈은 헥사고날 아키텍처를 적용합니다.

```
com.study.{service}/
├── {Service}Application.java        # Spring Boot Application
├── HealthCheckController.java       # Health Check API (/health)
├── domain/                          # 도메인 모델 (Entity, VO, Domain Service)
├── application/
│   ├── port/
│   │   ├── in/                      # Inbound Port (Use Case Interface)
│   │   └── out/                     # Outbound Port (Repository Interface)
│   └── service/                     # Use Case 구현체
└── adapter/
    ├── in/
    │   └── web/                     # REST Controller (Inbound Adapter)
    └── out/
        ├── persistence/             # JPA Repository (Outbound Adapter)
        └── messaging/               # Kafka Producer/Consumer
```

### 패키지 역할

| 패키지 | 역할 |
|--------|------|
| `domain` | 비즈니스 로직의 핵심, 외부 의존성 없음 |
| `application/port/in` | Use Case 인터페이스 (외부 → 애플리케이션) |
| `application/port/out` | Repository 인터페이스 (애플리케이션 → 외부) |
| `application/service` | Use Case 구현체 |
| `adapter/in/web` | REST API Controller |
| `adapter/out/persistence` | DB 접근 구현체 |
| `adapter/out/messaging` | Kafka 메시징 구현체 |

### Health Check API

각 서비스는 `/health` 엔드포인트를 제공합니다:
- `GET /health` → `{"status": "UP", "service": "{service-name}", "timestamp": "..."}`

## Dependencies

각 모듈의 `build.gradle`에서 의존성을 관리합니다.
공통 의존성은 루트 `build.gradle`의 `subprojects` 블록에 추가합니다.
