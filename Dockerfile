FROM openjdk:17-jdk-slim

WORKDIR /app

COPY gradle.properties settings.gradle.kts build.gradle.kts gradlew ./
COPY gradle ./gradle

COPY src ./src

RUN ./gradlew clean build

RUN ls -la build/libs/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/build/libs/wird-backend-all.jar"]
