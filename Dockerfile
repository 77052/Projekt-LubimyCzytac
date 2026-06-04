# --- Budowanie JAR ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Pobierz zależności osobno
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn clean package -DskipTests -q

# --- Obraz produkcyjny ---
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/lubimyczytac-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]
