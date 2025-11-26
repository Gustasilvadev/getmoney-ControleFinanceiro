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
# 2) Build APK - COM JDK CORRETO #
#########################################
FROM node:22-alpine AS mobile-build

# Instala dependências + JDK 17 CORRETO
RUN apk add --no-cache curl unzip openjdk17-jdk
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV ANDROID_HOME=/opt/android-sdk
RUN mkdir -p $ANDROID_HOME

WORKDIR /tmp
RUN curl -o sdk-tools.zip https://dl.google.com/android/repository/commandlinetools-linux-8512546_latest.zip && \
    unzip -q sdk-tools.zip -d $ANDROID_HOME/cmdline-tools && \
    mv $ANDROID_HOME/cmdline-tools/cmdline-tools $ANDROID_HOME/cmdline-tools/latest && \
    rm sdk-tools.zip

ENV PATH=$PATH:$JAVA_HOME/bin:$ANDROID_HOME/cmdline-tools/latest/bin

# Aceitar licenças
RUN yes | sdkmanager --licenses
RUN sdkmanager \
    "platform-tools" \
    "platforms;android-34" \
    "build-tools;34.0.0"

WORKDIR /getmoneyFrontend
COPY getmoneyFrontend/ ./

RUN npm ci --silent
RUN npx expo prebuild --platform android

WORKDIR /getmoneyFrontend/android

# Configurações do Gradle
RUN echo "sdk.dir=$ANDROID_HOME" > local.properties

RUN chmod +x ./gradlew

# Verifica versão do Java
RUN java -version

# Build
RUN ./gradlew clean assembleDebug --no-daemon

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