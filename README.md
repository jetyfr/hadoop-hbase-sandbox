# hadoop-sandbox

sandbox project for diginamic classes on hadoop

## Prerequisites

- [docker](https://docs.docker.com/install/)

## Getting started

- build the playground (in the playground directory)

```bash
./mvnw clean package
```

- build required images (in the root directory)

```bash
docker build -t sandbox/hadoop:latest -f ./Dockerfile.hadoop . && \
docker build -t playground/hadoop:latest -f ./Dockerfile.playground .
```

- Start the sandbox (in the root directory)

```bash
docker compose up -d
```

- Connect to the sandbox (aritrarly chosen container)

```bash
docker exec -it resourcemanager bash 
```

- test the cluster (in the playground director, replace **paths**)

```bash
./mvnw clean spring-boot:run -Dspring-boot.run.arguments="--open=/path/to/file/in/hdfs --to=/path/to/destination/file"
```

- peek at a running container (replace **CONTAINER_NAME**)

```bash
clear && docker container logs CONTAINER_NAME
```

## Clean up

- Stop the sandbox

```bash
docker compose down
```

- Delete the volumes (**will require formatting the namenode**, cf. [compose](./docker-compose.yml))

```bash
docker compose down -v
```
