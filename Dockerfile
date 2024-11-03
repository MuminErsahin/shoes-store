FROM maven:3.8.4-openjdk-17 as build
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM openjdk:17-slim
COPY --from=build /app/target/shoes-store.jar /app/shoes-store.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shoes-store.jar"]