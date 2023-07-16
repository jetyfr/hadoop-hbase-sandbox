FROM eclipse-temurin:8-jre-jammy


# configuration
WORKDIR /playground
RUN mkdir -p output target

# access
RUN groupadd --gid 1000 playground
RUN useradd --uid 1000 playground --gid 100 --home /playground
RUN echo "playground ALL=(ALL) NOPASSWD: ALL" >> /etc/sudoers

USER playground