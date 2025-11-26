############################
# 1) Build do BACKEND Java #
############################
FROM maven:3.9.9-amazoncorretto-21-alpine AS backend-build

WORKDIR /getmoneyBackend

COPY getmoneyBackend/pom.xml .
RUN mvn dependency:go-offline -q

COPY getmoneyBackend/src ./src
RUN mvn clean package -DskipTests -q

#########################################
# 2) Build do APK simplificado #
#########################################
FROM node:18-alpine AS mobile-build

# Instala Java e dependências básicas (IMPORTANTE para Android)
RUN apk add --no-cache openjdk11-jre bash

WORKDIR /getmoneyFrontend

# Copia apenas o necessário para cache
COPY getmoneyFrontend/package*.json ./
COPY getmoneyFrontend/app.json ./
COPY getmoneyFrontend/eas.json ./
RUN npm ci --silent --no-optional

# Copia source (evita copiar node_modules)
COPY getmoneyFrontend/src ./src
COPY getmoneyFrontend/assets ./assets
COPY getmoneyFrontend/*.js ./
COPY getmoneyFrontend/*.json ./

# Build CORRETO
RUN npx expo prebuild --platform android

WORKDIR /getmoneyFrontend/android
RUN chmod +x ./gradlew && ./gradlew assembleRelease --no-daemon --stacktrace

#########################################
# 3) Imagem final #
#########################################
FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=backend-build /getmoneyBackend/target/*.jar app.jar
COPY --from=mobile-build /getmoneyFrontend/android/app/build/outputs/apk/release/app-release.apk /app/apk/app-release.apk

EXPOSE 8401

CMD ["java", "-jar", "/app/app.jar"]