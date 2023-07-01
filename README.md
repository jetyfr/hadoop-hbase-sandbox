# hadoop-sandbox

sandbox project for diginamic classes on hadoop

## Prerequisites

- [docker](https://docs.docker.com/install/)

## Getting started

- build the image

```bash
docker build -t sandbox/hadoop:latest .
```

- Start the sandbox

```bash
docker-compose up -d
```

- Connect to the sandbox

```bash
# arbitrarly chosen container, did not try interacting with the cluster from elsewhere
docker exec -it resourcemanager bash 
```

- test the cluster (in the playground directory)

```bash
./mvnw clean spring-boot:run -Dspring-boot.run.arguments="--open=/path/to/file/in/hdfs --to=/path/to/destination/file"
```

## Clean up

- Stop the sandbox

```bash
docker-compose down
```

- Delete the volumes

```bash
docker-compose down -v
```
