# Hadoop/Hbase Cluster

Project deploying cluster with docker (fully distributed mode)
- hadoop
- hbase
- zookeeper

## Getting started

- build required images (in the root directory)

```bash
docker build -t hadoop:sandbox -f base/hadoop.Dockerfile base	
docker build -t hbase:sandbox -f base/hbase.Dockerfile base
```

- Start the cluster (in the root directory)

```bash
docker compose up -d --remove-orphans
```

## Test the cluster (requires the cluster to be up and running)

- in the root directory

```bash
docker images && docker volume ls && docker ps -a
```

- in a browser
  - [cluster health](http://localhost:9870) - namenode
  - [yarn](http://localhost:8088) - resourcemanager
  - [swagger](http://localhost:7080/swagger) - playground

### Connect

- Connect to the cluster

```bash
docker exec -it resourcemanager /bin/bash
```

### HBase
- Start HBase Rest Server

```bash
docker exec hbase-master hbase rest start
```

## Clean up

- Stop the cluster (in the root directory)

```bash
docker compose stop
```

- reset the cluster (in the root director)

```bash
docker compose down -v
```
