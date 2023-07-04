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

- test the cluster (on a browser)
  - [cluster health](http://localhost:9870) - namenode
  - [yarn](http://localhost:8088) - resourcemanager
  - [filesystem status](http://localhost:3141/status) - playground

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
