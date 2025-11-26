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
# 2) Build do APK COM ANDROID SDK #
#########################################
FROM node:18-bullseye AS mobile-build

# Instala JDK 21 e Android SDK
RUN apt-get update && apt-get install -y \
    openjdk-21-jdk \
    curl \
    unzip \
    git \
    && rm -rf /var/lib/apt/lists/*

# Configura Android SDK
ENV ANDROID_HOME /opt/android-sdk
RUN mkdir -p $ANDROID_HOME
RUN curl -o sdk-tools.zip https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip && \
    unzip sdk-tools.zip -d $ANDROID_HOME/cmdline-tools && \
    mv $ANDROID_HOME/cmdline-tools/cmdline-tools $ANDROID_HOME/cmdline-tools/latest && \
    rm sdk-tools.zip

ENV PATH $PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# Aceita licenças e instala platform
RUN yes | sdkmanager --licenses
RUN sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

WORKDIR /getmoneyFrontend

# Copia arquivos para cache
COPY getmoneyFrontend/package*.json ./
COPY getmoneyFrontend/app.json ./
COPY getmoneyFrontend/eas.json ./
RUN npm ci --silent

# Copia source
COPY getmoneyFrontend/src ./src
COPY getmoneyFrontend/assets ./assets
COPY getmoneyFrontend/*.js ./
COPY getmoneyFrontend/*.json ./

# Build do APK
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