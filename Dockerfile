# Maven build stage-la Java 21 use pannunga
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Run stage-layum Java 21 use pannunga
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 7860
ENTRYPOINT ["java", "-Dserver.port=7860", "-Xmx512m", "-jar", "app.jar"]