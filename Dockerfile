FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/target/oncall-prober.jar .
CMD ["java", "-jar", "oncall-prober.jar"]