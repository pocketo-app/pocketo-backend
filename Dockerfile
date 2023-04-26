# syntax=docker/dockerfile:1
# https://github.com/jonashackt/spring-boot-buildpack#using-layered-jars-inside-dockerfiles

# FROM must be the first statement
FROM eclipse-temurin:17-jre-alpine AS builder
WORKDIR /workspace/
COPY ./build/libs/app.jar ./app.jar
RUN java -Djarmode=layertools -jar ./app.jar extract

FROM eclipse-temurin:17-jre-alpine
WORKDIR /workspace/
COPY --from=builder /workspace/dependencies/ ./
COPY --from=builder /workspace/spring-boot-loader/ ./
COPY --from=builder /workspace/snapshot-dependencies/ ./
COPY --from=builder /workspace/application/ ./
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "org.springframework.boot.loader.JarLauncher"]

EXPOSE 8080
EXPOSE 5005
LABEL author.name="Vu Tong"
LABEL author.email="tonghoangvu@outlook.com"
