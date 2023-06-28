# hadoop-sandbox

sandbox project for diginamic classes on hadoop

## Prerequisites

- [docker](https://docs.docker.com/install/)

## Getting started

### build the image

```bash
docker build -t sandbox/hadoop:latest .
```

### Start the sandbox

```bash
docker-compose up -d
```

### Stop the sandbox

```bash
docker-compose down -v
```
