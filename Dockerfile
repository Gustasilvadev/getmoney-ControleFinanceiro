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
FROM eclipse-temurin:21-jdk AS mobile-build

# Instala dependências para Android SDK
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    git \
    && rm -rf /var/lib/apt/lists/*

# INSTALA NODE.JS v22.18.0
RUN curl -fsSL https://deb.nodesource.com/setup_22.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g yarn

# Configura Android SDK
ENV ANDROID_HOME=/opt/android-sdk
RUN mkdir -p $ANDROID_HOME
RUN curl -o sdk-tools.zip https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip && \
    unzip sdk-tools.zip -d $ANDROID_HOME/cmdline-tools && \
    mv $ANDROID_HOME/cmdline-tools/cmdline-tools $ANDROID_HOME/cmdline-tools/latest && \
    rm sdk-tools.zip

ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# Aceita licenças e instala platform
RUN yes | sdkmanager --licenses
RUN sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

WORKDIR /getmoneyFrontend

# Copia TODOS os arquivos do frontend (incluindo gradlew)
COPY getmoneyFrontend ./

# Instala dependências e faz prebuild
RUN npm ci --silent
RUN npx expo prebuild --platform android

# Configuração do Gradle para JDK 21
WORKDIR /getmoneyFrontend/android
RUN echo "org.gradle.java.home=/usr/lib/jvm/temurin-21-jdk-amd64" >> gradle.properties && \
    echo "android.jdkVersion=21" >> gradle.properties

# Garante que gradlew tem permissões de execução E usa modo offline
RUN chmod +x ./gradlew
RUN ./gradlew assembleRelease --offline --no-daemon

#########################################
# 3) Imagem final #
#########################################
FROM amazoncorretto:21-alpine

WORKDIR /app

RUN mkdir -p /app/apk

COPY --from=backend-build /getmoneyBackend/target/*.jar app.jar
COPY --from=mobile-build /getmoneyFrontend/android/app/build/outputs/apk/release/app-release.apk /app/apk/

EXPOSE 8401

CMD ["java", "-jar", "/app/app.jar"]