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
# 2) Build do APK COM EAS BUILD #
#########################################
FROM node:22-alpine AS mobile-build

WORKDIR /getmoneyFrontend

# Copia arquivos de configuração do projeto
COPY getmoneyFrontend/package*.json ./
COPY getmoneyFrontend/app.json ./
COPY getmoneyFrontend/eas.json ./

# Instala dependências
RUN npm ci --silent

# Instala o EAS CLI usando o expo (recomendado)
RUN npx expo install eas-cli

# Copia o resto do código
COPY getmoneyFrontend/ ./

# Build com EAS
RUN npx eas-cli build --platform android --local --non-interactive --output=app-release.apk

#########################################
# 3) Imagem final #
#########################################
FROM amazoncorretto:21-alpine

WORKDIR /app

RUN mkdir -p /app/apk

COPY --from=backend-build /getmoneyBackend/target/*.jar app.jar
COPY --from=mobile-build /getmoneyFrontend/app-release.apk /app/apk/

EXPOSE 8401

CMD ["java", "-jar", "/app/app.jar"]