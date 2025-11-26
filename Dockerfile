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

# Instala dependências básicas
RUN apt-get update && apt-get install -y \
    curl \
    unzip \
    git \
    && rm -rf /var/lib/apt/lists/*

# INSTALA NODE.JS v18
RUN curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs

# Configura Android SDK
ENV ANDROID_HOME=/opt/android-sdk
ENV ANDROID_SDK_ROOT=$ANDROID_HOME
RUN mkdir -p $ANDROID_HOME

# Download e configuração do Android SDK
RUN curl -o sdk-tools.zip https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip && \
    unzip -q sdk-tools.zip -d $ANDROID_HOME/cmdline-tools && \
    mv $ANDROID_HOME/cmdline-tools/cmdline-tools $ANDROID_HOME/cmdline-tools/latest && \
    rm sdk-tools.zip

ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools

# Aceita licenças e instala componentes mínimos
RUN yes | sdkmanager --licenses
RUN sdkmanager \
    "platform-tools" \
    "platforms;android-34" \
    "build-tools;34.0.0"

WORKDIR /getmoneyFrontend

# Copia projeto frontend
COPY getmoneyFrontend/ ./

# Instala dependências e faz prebuild
RUN npm ci --silent
RUN npx expo prebuild --platform android

# Configuração do Android build
WORKDIR /getmoneyFrontend/android

# CRIA local.properties (SOLUÇÃO PRINCIPAL)
RUN echo "sdk.dir=$ANDROID_HOME" > local.properties

# Configuração mínima do Gradle
RUN echo "org.gradle.jvmargs=-Xmx4g" > gradle.properties

RUN chmod +x ./gradlew

# Build DIRETO - seguindo sua sugestão
RUN ./gradlew clean
RUN ./gradlew assembleDebug

#########################################
# 3) Imagem final #
#########################################
FROM amazoncorretto:21-alpine

WORKDIR /app

RUN mkdir -p /app/apk

COPY --from=backend-build /getmoneyBackend/target/*.jar app.jar
COPY --from=mobile-build /getmoneyFrontend/android/app/build/outputs/apk/debug/app-debug.apk /app/apk/

EXPOSE 8401

CMD ["java", "-jar", "/app/app.jar"]