FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=builder /app/target/shoes-store.jar shoes-store.jar
EXPOSE 8080
CMD ["java", "-jar", "shoes-store.jar"]