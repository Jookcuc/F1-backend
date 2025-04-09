# Etapa 1: Construcción
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app


COPY . .


RUN mvn -B clean package -DskipTests


FROM eclipse-temurin:21-jdk

WORKDIR /app


COPY --from=build /app/target/*.jar app.jar


EXPOSE 8080

# Comando de ejecución
ENTRYPOINT ["java", "-jar", "app.jar"]
