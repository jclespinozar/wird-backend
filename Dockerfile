FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/wird-backend-all.jar /app/wird-backend-all.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/wird-backend-all.jar"]
