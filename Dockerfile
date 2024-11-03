FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-slim
COPY --from=build /app/target/shoes-store.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]