FROM eclipse-temurin:8-jre-alpine

RUN mkdir output target

# ENTRYPOINT [ "java", "-jar", "playground.jar" ]