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
    cmake \
    ninja-build \
    && rm -rf /var/lib/apt/lists/*

# MANTÉM Node.js v22.18.0 (igual ao seu projeto)
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

# Aceita licenças e instala platform + NDK
RUN yes | sdkmanager --licenses
RUN sdkmanager \
    "platform-tools" \
    "platforms;android-34" \
    "build-tools;34.0.0" \
    "ndk;27.1.12297006" \
    "cmake;3.22.1"

WORKDIR /getmoneyFrontend

# Copia APENAS arquivos necessários para cache
COPY getmoneyFrontend/package*.json ./
COPY getmoneyFrontend/app.json ./
COPY getmoneyFrontend/eas.json ./
RUN npm ci --silent --no-optional

# Copia o resto do código
COPY getmoneyFrontend/ ./

# Build do APK
RUN npx expo prebuild --platform android

# Configuração do Gradle OTIMIZADA
WORKDIR /getmoneyFrontend/android

# Configurações CRÍTICAS para evitar timeout
RUN echo "org.gradle.jvmargs=-Xmx8g -XX:MaxMetaspaceSize=2g -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8" > gradle.properties && \
    echo "org.gradle.parallel=true" >> gradle.properties && \
    echo "org.gradle.caching=true" >> gradle.properties && \
    echo "org.gradle.daemon=false" >> gradle.properties && \
    echo "org.gradle.workers.max=2" >> gradle.properties && \
    echo "android.jdkVersion=21" >> gradle.properties && \
    echo "android.enableJetifier=true" >> gradle.properties && \
    echo "android.nonFinalResIds=false" >> gradle.properties && \
    echo "android.enableR8=true" >> gradle.properties && \
    echo "sdk.dir=$ANDROID_HOME" >> gradle.properties

# Configura para build APENAS ARM64
RUN sed -i '/defaultConfig {/a\        ndk { abiFilters "arm64-v8a" }' app/build.gradle

RUN chmod +x ./gradlew

# Build em etapas com retry
RUN ./gradlew dependencies --no-daemon --stacktrace

RUN ./gradlew clean

# Build com configurações de performance
RUN ./gradlew assembleRelease \
    -Pandroid.debug.retainAllBuildArtifacts=false \
    -Pandroid.builder.sdkDownload=true \
    --no-daemon \
    --stacktrace \
    --info \
    --max-workers=2

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