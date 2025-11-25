FROM maven:3.9.9-amazoncorretto-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests
FROM amazoncorretto:21-alpine
RUN mkdir -p /app/apk
COPY --from=build getmoney.apk /app/apk/getmoney.apk
COPY --from=build target/*.jar app.jar
EXPOSE 8401
CMD ["java", "-jar", "/app.jar"]