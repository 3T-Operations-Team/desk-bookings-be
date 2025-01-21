FROM maven:3.9.9-eclipse-temurin-21-alpine AS be-builder

WORKDIR /app/

COPY src ./src
COPY pom.xml .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-alpine

LABEL org.opencontainers.image.source=https://github.com/3T-Operations-Team/desk-bookings-be

WORKDIR /app
COPY --from=be-builder /app/target/*.jar /app/backend.jar

EXPOSE 8080

# Essential vars to override
ENV SENDGRID_KEY="yourkey"
ENV SENDGRID_HOST="api.sendgrid.com"
ENV SENDGRID_VERSION="v3"
ENV MONGO_URL="mongodb://yourhost:port/"
ENV MONGO_DBNAME="cicd"

ENTRYPOINT ["java", "-jar", "backend.jar"]


