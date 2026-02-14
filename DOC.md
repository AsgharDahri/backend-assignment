# Project Details

## 1. Start the database with Docker Compose

A few changes have been made in the XML (Liquibase) files. Start the stack with:

```bash
docker compose up
```

Leave this running so PostgreSQL is available for the application.

## 2. Configure database credentials


Set DB credentials to match the Postgres service in `docker-compose.yml`:

## 3. Run the application

Run the app in either of these ways:

- **From your IDE:** run the main class `com.bayzdelivery.BayzDeliveryApplication` (main function).
- **FROM Configuration:** Config jdk and main file destination, Apply and run application [ Intellij ]
## 4. Localhost & Swagger UI

After the app is up:

- **Swagger URL:** http://localhost:8081/api/swagger-ui/index.html#/
- **Host URL:** http://localhost:8081
- **GITHUB URL:** https://github.com/AsgharDahri/backend-assignment/
## 5. Assumptions

- **Create delivery:** used for creating a delivery.
- **Update delivery:** used when the delivery is in “delivery” status (or when updating an existing delivery as specified by the API).
- **Date format** for `startDate` and `endDate`: ISO 8601 with time, e.g. `2026-02-12T16:14:25.557Z`.
