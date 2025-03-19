FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/scheduler-service-1.0.0.0.jar scheduler-service-1.0.0.0.jar
EXPOSE 8080
CMD ["java", "-jar", "scheduler-service-1.0.0.0.jar"]