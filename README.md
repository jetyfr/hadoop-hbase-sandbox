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

- Start the cluster (in the root directory, **requires formatting the namenode on the first run**, cf. [compose](./docker-compose.yml))

```bash
docker compose up -d
```

## Test the cluster

- peek at a running container's logs (replace **CONTAINER_NAME**)

```bash
clear && docker container logs CONTAINER_NAME
```

- test in a browser
  - [cluster health](http://localhost:9870) - namenode
  - [yarn](http://localhost:8088) - resourcemanager
  - [filesystem status](http://localhost:3141/status) - playground

- create a file in samples folder containing any text (in the root directory, exemple bellow)

```bash
echo "hello world !" > samples/input.txt
```

- Connect to the cluster (most useful volumes are mounted in the resourcemanager container)

```bash
docker exec -it resourcemanager bash 
```

- copy the created file to hdfs (should display input.txt twice)

```bash
ls -la samples && hdfs dfs -put samples/input.txt / && \
hdfs dfs -ls /
```

- run wordcount on the added file (should display the result)

```bash
yarn jar jars/playground.jar wordcount /input.txt /output && \
hdfs dfs -cat /output/part-r-00000
```

## Clean up

- Stop the sandbox (in the root directory)

```bash
docker compose down
```

- Delete the volumes (in the root directory, **will require formatting the namenode**, cf. [compose](./docker-compose.yml))

```bash
docker compose down -v
```
