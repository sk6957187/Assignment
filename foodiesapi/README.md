# FoodiesAPI

FoodiesAPI is a full-stack food ordering application combining a Spring Boot backend and two React frontends (customer-facing `foodies` and admin-facing `adminpanel`). This repository contains the backend server, static builds of the frontends, and source for both frontends under `forntend/`.

## Table of contents

- [Quick start](#quick-start)
- [Contents](#contents)
- [Prerequisites](#prerequisites)
- [Running locally (dev)](#running-the-application-locally-development)
- [Building for production](#building-for-production-one-packaged-artifact)
- [Features](#features)
- [Frontend READMEs](#frontend-readmes)
- [Environment and configuration](#environment-variables-and-configuration)
- [API overview](#api-overview)
- [Troubleshooting](#troubleshooting)

## Quick start

The smallest set of steps to get the app running (PowerShell): build both frontends, package the backend jar and run it. This assumes Node and Java are installed.

```powershell
# Build both frontends then package and run the backend
cd forntend\foodies; npm install; npm run build; cd ..\adminpanel; npm install; npm run build; cd ..\..; .\mvnw.cmd clean package -DskipTests; java -jar target\foodiesapi-0.0.1-SNAPSHOT.jar
```

Run the commands one line at a time in separate terminals if you prefer to develop with live reload (see below).

## Main commands (quick reference)

Use these main commands during development and for packaging. They are written for Windows PowerShell.

Dev (run servers individually)

```powershell
# Start backend (dev)
.\mvnw.cmd spring-boot:run

# Start customer frontend (dev)
cd forntend\foodies
npm install
npm start

# Start admin frontend (dev)
cd forntend\adminpanel
npm install
npm start
```

Build & package

```powershell
# Build both frontends, package backend and run the jar (single-line)
cd forntend\foodies; npm install; npm run build; cd ..\adminpanel; npm install; npm run build; cd ..\..; .\mvnw.cmd clean package -DskipTests; java -jar target\foodiesapi-0.0.1-SNAPSHOT.jar

# Or step-by-step:
cd forntend\foodies; npm install; npm run build
cd forntend\adminpanel; npm install; npm run build
.\mvnw.cmd clean package -DskipTests
java -jar target\foodiesapi-0.0.1-SNAPSHOT.jar
```

Helper script

For convenience there's a small PowerShell helper script at `scripts\main-commands.ps1` which exposes common actions (dev, build, package, build-and-run). Example usage:

```powershell
# List actions
.
\scripts\main-commands.ps1 -Action list

# Run build-and-run
.
\scripts\main-commands.ps1 -Action build-and-run
```

If you prefer not to use the script, copy the commands above into your shell.

## Frontend READMEs

- Customer app: `forntend/foodies/README.md` (dev & build instructions)
- Admin app: `forntend/adminpanel/README.md` (dev & build instructions)
- Full features list: `FEATURES.md`

## Contents

- `src/main/java/in/nayak/foodiesapi` — Spring Boot backend source
- `src/main/resources` — backend resources and embedded frontend static assets
- `forntend/foodies` — React customer app (source + build)
- `forntend/adminpanel` — React admin app (source + build)
- `pom.xml`, `mvnw`, `mvnw.cmd` — Maven wrapper and build
- `rzp-key.csv` — (optional) Razorpay key file used by project (check before sharing)

## Quick summary

- Backend: Java Spring Boot (embedded Tomcat). Controllers include AuthController, FoodController, CartController, OrderController, UserController and an SPA controller for static routing.
- Frontends: React apps (customer and admin) built into `forntend/*/build` and copied into backend `src/main/resources/static` for packaging.

## Goals / What this README helps with

- Run locally (backend + frontends) using Windows PowerShell
- Build production artifacts (jar and static client builds)
- Environment & secret configuration (JWT, DB, AWS, payment keys)
- Find relevant code and endpoints quickly

---

## Prerequisites

- Java 11+ (matching project's configured Java version)
- Maven (or use the included wrapper `mvnw.cmd` on Windows)
- Node.js & npm (for running or building frontends)
- PowerShell (examples below assume Windows PowerShell)

## Running the application locally (development)

1. Backend (run from project root):

```powershell
# Start backend with Maven wrapper (Windows PowerShell)
.\mvnw.cmd spring-boot:run
```

This runs the Spring Boot app and serves API endpoints. By default the server runs on port configured in `src/main/resources/application.properties` (commonly `8080`).

2. Frontend (optional - run dev server for faster UI development):

- Customer app (foodies):

```powershell
cd forntend\foodies
npm install
npm start
```

- Admin panel (adminpanel):

```powershell
cd forntend\adminpanel
npm install
npm start
```

Dev servers run separately (typically on ports like 3000/3001). When using dev servers, configure the frontend to proxy API requests to the backend (check `package.json` or frontend code for `proxy` settings).

## Building for production (one-packaged artifact)

1. Build and copy frontends into backend resources (if you want a single jar that serves UI):

- Build customer app and admin panel (example):

```powershell
cd forntend\foodies
npm install
npm run build

cd ..\adminpanel
npm install
npm run build
```

- Copy the produced `build` folders into `src/main/resources/static` (or ensure the build process already places them there). This repository already contains built static files under `src/main/resources/static` in the source tree and `target/static` in builds.

2. Build backend jar:

```powershell
# From project root
.\mvnw.cmd clean package -DskipTests
# Run the resulting jar
java -jar target\foodiesapi-0.0.1-SNAPSHOT.jar
```

The jar includes static assets so the UI and API are served from the same host.

## Environment variables and configuration

The app reads configuration from `src/main/resources/application.properties`. Common environment variables you may want to set (or provide via properties):

- SPRING_DATASOURCE_URL — JDBC URL for your database
- SPRING_DATASOURCE_USERNAME — DB user
- SPRING_DATASOURCE_PASSWORD — DB password
- JWT_SECRET — secret used by JWT utilities (see `util/JwtUtil.java`)
- AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY, AWS_REGION — if using AWS services (see `config/AWSConfig.java`)
- RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET — payment integration keys (if using Razorpay; `rzp-key.csv` present in root may contain keys)

Tip: do not commit real secrets. Use environment variables or an external secret manager in production.

## API overview

The backend exposes REST controllers under `src/main/java/in/nayak/foodiesapi/controller/`.
Key controllers and expected base endpoints (look at controller sources for exact paths):

- AuthController — authentication & user registration (e.g. `/api/auth`)
- FoodController — food item CRUD & listing (e.g. `/api/food`)
- CartController — cart operations (e.g. `/api/cart`)
- OrderController — place orders & view orders (e.g. `/api/order`)
- UserController — user profile & management (e.g. `/api/user`)
- SpaController — serves the SPA (single-page app) routing for client-side routes

To see full paths and request/response DTOs, inspect `controller` and `io` packages in `src/main/java/in/nayak/foodiesapi`.

## Features

This repository implements a full-stack food ordering application. For a detailed, categorized list of features (backend, frontends, integrations and where to find the related code), see the top-level `FEATURES.md` file.

## Database and persistence

- JPA entities are in `entity/` and JPA repositories in `repository/`.
- Configure a datasource in `application.properties` or via environment variables. For development, the app may default to an in-memory DB if configured; check `application.properties` for the current setup.

## Security

JWT authentication is implemented (see `filters/JwtAuthenticationFilter.java`, `util/JwtUtil.java`, and `config/SecurityConfig.java`). For testing endpoints you may need a valid JWT token produced by the `/api/auth/login` endpoint.

## Useful development notes

- Frontend source is in `forntend/*/src`; those folders contain `package.json` and README files for each app.
- The backend project uses Maven; prefer the included wrappers `mvnw` / `mvnw.cmd` for reproducible builds.
- When packaging the jar, ensure the built frontend static files are available under `src/main/resources/static` or are copied to `target/classes/static` before packaging.

## Tests

- Backend unit tests are under `test/java/in/nayak/foodiesapi` and run via Maven:

```powershell
.\mvnw.cmd test
```

- Frontend tests (if present) can be run from each frontend folder with `npm test`.

## Troubleshooting

- If the backend cannot connect to DB, confirm JDBC URL & credentials and ensure DB allows connections.
- If static UI is not showing after packaging, confirm the frontend build output is copied into backend `resources/static` and included in the jar.
- JWT-related 401 errors: check `JWT_SECRET` and token expiry settings.

## Where to look in the code

- Entry point: `src/main/java/in/nayak/foodiesapi/FoodiesapiApplication.java`
- Security: `config/SecurityConfig.java`
- AWS: `config/AWSConfig.java`
- Authentication: `service/AppUserDetailsService.java`, `io/AuthenticationRequest.java`
- DTOs: `src/main/java/in/nayak/foodiesapi/io` (requests & responses)

## Contributing

- Fork, make a branch, add a clear commit message, and open a PR. Run backend tests and verify UI builds.

## License & contact

This repository does not include a license file. Add a `LICENSE` file if you intend to publish under a specific license.

For questions, look at controller/service implementations or reach out to the project maintainer (project metadata or commit history will help identify maintainers).

---

Last updated: 2025-11-02
