FROM ghcr.io/graalvm/native-image-community:21 AS base
WORKDIR /app

FROM base AS dependencies

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline -B

FROM base AS build
COPY --from=dependencies /root/.m2 /root/.m2

WORKDIR /app

COPY . .

RUN chmod +x mvnw

ARG QUARKUS_PROFILE=prod
ENV QUARKUS_PROFILE=${QUARKUS_PROFILE}

RUN ./mvnw package -D native -D quarkus.profile=${QUARKUS_PROFILE} -D skipTests

FROM quay.io/quarkus/ubi9-quarkus-micro-image:2.0

WORKDIR /work/

COPY --from=build /app/target/*-runner /work/application

RUN chown 1001 /work && chmod "g+rwX" /work && chown 1001:root /work

EXPOSE 8080
USER 1001

ENTRYPOINT ["./application", "-Dquarkus.http.host=0.0.0.0", "-D quarkus.profile=${QUARKUS_PROFILE}"]
