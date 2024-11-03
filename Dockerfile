FROM maven:3.8.4-openjdk-17

WORKDIR /app
COPY . .

# Maven build
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Run stage
FROM openjdk:17-slim
COPY --from=0 /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]