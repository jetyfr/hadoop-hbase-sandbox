FROM eclipse-temurin:8-jre-alpine

RUN mkdir /output

ENTRYPOINT [ "java", "-jar", "playground.jar" ]