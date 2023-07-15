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
make cluster
```

- Start the cluster (in the root directory, add "-d" to run in background)

```bash
docker compose up
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

### mapreduce

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

### hbase

## Clean up

- Stop the sandbox (in the root directory)

```bash
docker compose stop
```

- Delete containers and volumes (in the root director)

```bash
docker compose down -v
```
