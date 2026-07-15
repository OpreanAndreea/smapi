# Project Concept: **SMAPI** (An Enterprise ERP for Electrical Engineers)

**SMAPI** is a specialized, microservice-based Enterprise Resource Planning (ERP) and project management application tailored specifically for independent electrical contractors and engineering teams. It bridges the gap between field operations (job tracking, notes, schematics) and supply chain logistics (inventory control, automated vendor fetching, and project budgeting).

---

## 1. Functional Module Architecture (Microservices Break Down)

To cleanly implement a microservices architecture that plays well with Docker and Kubernetes, we decouple the application’s business domains into autonomous services. Each service owns its database instance to avoid tight coupling.

```
                  ┌──────────────────────────────┐
                  │   Client (Web / Mobile UI)   │
                  └──────────────┬───────────────┘
                                 │ HTTP / HTTPS
                                 ▼
                  ┌──────────────────────────────┐
                  │    API Gateway (Spring)      │
                  └──────────────┬───────────────┘
                                 │
         ┌───────────────────────┼───────────────────────┐
         ▼                       ▼                       ▼
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│  Auth Service   │     │  Job & Project  │     │   Inventory &   │
│ (Spring Security│     │     Service     │     │ Supply Service  │
│    + JWT)       │     │  (JPA / MySQL)  │     │ (JPA / Mongo)   │
└─────────────────┘     └─────────────────┘     └─────────────────┘
         ▲                       ▲                       ▲
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 ▼
                    ┌───────────────────────────┐
                    │     Budgeting Service     │
                    └───────────────────────────┘

```

### 1. Identity & Access Management (IAM) Service

* **Purpose:** Manages user authentication, engineer provisioning, and authorization levels (e.g., `ROLE_FIELD_ENGINEER`, `ROLE_PROJECT_MANAGER`).
* **Tech Highlight:** Implements state-free session control utilizing **Spring Security** and signed **JSON Web Tokens (JWT)**.

### 2. Job & Project Management Service

* **Purpose:** Handles creation and configuration of physical engineering jobs. Tracks job lifecycles (e.g., *Drafting, Inspection, Wiring, Sign-Off, Completed*).
* **Features:** Stores job-site notes, tracks progress checklists, and handles file attachments (PDF schematics, CAD layouts, electrical safety certificates).

### 3. Inventory & Component Service

* **Purpose:** Tracks stock levels of electrical components within local warehouses or service trucks.
* **Catalog Categories:** Specialized fields for wiring types (gauges, insulation), circuit breakers, transformers, switchgears, and conduits.

### 4. Supplier & Procurement Service

* **Purpose:** Connects via REST clients to external third-party electrical distributor APIs (e.g., simulating calls to wholesalers or catalogs) to pull live pricing, fetch component spec sheets, and place orders.

### 5. Budgeting & Financial Service

* **Purpose:** Aggregates data from the *Job Service* (labor hours) and the *Procurement Service* (material costs) to track the live cost of a project against an initial budget ceiling. Trigger alarms if costs near or exceed projections.

---

## 2. Deep-Dive Technical Implementation

By referencing modern Java and Spring 6 practices, the system handles cross-cutting requirements with standard framework patterns.

### A. Core Web API & Data Management

* **RESTful APIs:** Designed around cleanly structured resources following strict HTTP methods (e.g., `GET /jobs/{id}`, `POST /jobs`, `PUT /inventory/{id}`). Data maps to and from the front-end using decoupled Data Transfer Objects (DTOs) parsed natively as JSON strings via `@RequestBody`.
* **Hybrid Persistence Tier:** * **Spring Data JPA:** Handled on highly structured domain datasets like Jobs, Orders, and Budgets to leverage Object-Relational Mapping (ORM) safety.
* **Spring JDBC (`JdbcTemplate`):** Embedded inside the Budget Service for high-speed read operations where complex multi-table SQL aggregations are required.
* **NoSQL / Document Storage:** Integrated into the Job Service specifically for tracking loosely structured diagnostic notes or managing file metadata for schematic attachments.



### B. Enterprise Security, Validation, & Resilience

* **Stateless Microservice Security:** The API Gateway acts as the entry filter. Once a user authenticates with the IAM service, a secure JWT token is passed down along downstream service requests via HTTP headers. Downstream microservices parse the JWT securely to extract user context and operational privileges without needing re-query bottlenecks.
* **Centralized Error Handling:** Every microservice embeds a global exception advisor built with `@RestControllerAdvice`. Domain validation exceptions (e.g., `ResourceNotFoundException`, `BudgetCeilingBreachedException`) map automatically to standardized error responses containing localized messages and timestamp details.
* **Input Declarative Validation:** Inbound JSON structures are evaluated at the controller threshold using declarative annotations (`@Valid`, `@NotNull`, `@Min(1)`), preventing contaminated data from penetrating into downstream layers.

### C. Advanced Optimization using Spring AOP

To maintain separation of concerns, infrastructure behaviors are cleanly extracted into customized aspects using **Spring AOP**:

* **Logging & Tracing Aspect:** Intercepts method executions across corporate service boundaries using `@Before` and `@AfterReturning` advice, creating predictable system logs without manual code injection.
* **Latency Monitoring Aspect:** Wraps external third-party supplier API connections using an `@Around` advice interceptor, reporting slow API connection speeds seamlessly to monitoring backends.

---

## 3. Deployment & DevOps Lifecycle Blueprint

To scale the implementation, the infrastructure maps across a multi-tier continuous integration pipeline:

```
[Source Code] ──► [JUnit/Mockito Tests] ──► [Docker Multi-Stage Build] ──► [Kubernetes Cluster]

```

### 1. Robust Quality Assurance

* **Unit Layer:** Employs **JUnit 5** and **Mockito** to evaluate service logic in isolation, explicitly mocking repository behaviors.
* **Integration Layer:** Leverages `@SpringBootTest` configured against transient, in-memory **H2 databases** to validate integrated slice flows (Controllers through to Persistence).

### 2. Microservice Containerization (Docker)

Every individual Spring Boot application receives a dedicated, optimized multi-stage `Dockerfile`.

* **Stage 1 (Build):** Compiles dependencies and generates a standard application `.jar` using Maven.
* **Stage 2 (Runtime):** Extracts the `.jar` file layer structure inside an ultra-lean runtime environment (e.g., Eclipse Temurin JRE Alpine images) to ensure minimal surface-area footprint and fast image startup times.

### 3. Production Orchestration (Kubernetes)

The architecture drops elegantly into local or managed Kubernetes environments (e.g., Minikube, EKS, GKE) through a set of defined configuration templates:

* **Deployments & Pods:** Configures replicas for horizontal auto-scaling, mapping resource limits (`cpu`, `memory`) to prioritize vital paths like the *Job & Project Management Service*.
* **Networking (Services & Ingress):** Internally bound apps use `ClusterIP` services to enable secure communications. An Ingress Controller sits out front, safely exposing selected API Gateway entry routes to public consumers.
* **Configuration Isolation:** Secret keys, JWT signing passes, and distinct database credentials live safely inside native Kubernetes `ConfigMaps` and `Secrets`, keeping application images completely abstract and environment-agnostic.