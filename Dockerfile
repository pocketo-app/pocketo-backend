# syntax=docker/dockerfile:1
# https://github.com/jonashackt/spring-boot-buildpack#using-layered-jars-inside-dockerfiles

# Don't build from source code, just copy pre-built JAR
FROM openjdk:17-alpine AS builder
WORKDIR /workspace/
ARG JAR_FILE
COPY ./build/libs/${JAR_FILE} ./app.jar
RUN java -Djarmode=layertools -jar ./app.jar extract

FROM openjdk:17-alpine
WORKDIR /workspace/
COPY --from=builder /workspace/dependencies/ ./
COPY --from=builder /workspace/spring-boot-loader/ ./
COPY --from=builder /workspace/snapshot-dependencies/ ./
COPY --from=builder /workspace/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
