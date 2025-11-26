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
# 2) Build APK - ULTRA SIMPLES #
#########################################
FROM node:22-alpine AS mobile-build

# Instala Android SDK compacto
RUN apk add --no-cache curl unzip openjdk21
ENV ANDROID_HOME=/opt/android-sdk
RUN mkdir -p $ANDROID_HOME

WORKDIR /tmp
RUN curl -o sdk-tools.zip https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip && \
    unzip -q sdk-tools.zip -d $ANDROID_HOME/cmdline-tools && \
    mv $ANDROID_HOME/cmdline-tools/cmdline-tools $ANDROID_HOME/cmdline-tools/latest && \
    rm sdk-tools.zip

ENV PATH=$PATH:$ANDROID_HOME/cmdline-tools/latest/bin

# **ADICIONA ESTA LINHA - Aceitar licenças ANTES de usar sdkmanager**
RUN yes | $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --licenses

# **ADICIONA ESTAS LINHAS - Instalar componentes Android**
RUN $ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager \
    "platform-tools" \
    "platforms;android-34" \
    "build-tools;34.0.0"

WORKDIR /getmoneyFrontend
COPY getmoneyFrontend/ ./

RUN npm ci --silent
RUN npx expo prebuild --platform android

WORKDIR /getmoneyFrontend/android
RUN echo "sdk.dir=$ANDROID_HOME" > local.properties
RUN chmod +x ./gradlew
RUN ./gradlew clean assembleDebug

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