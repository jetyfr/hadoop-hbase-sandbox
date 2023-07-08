cluster:
	docker build -t sandbox/hadoop:latest -f ./Dockerfile.hadoop .
	docker build -t playground/hadoop:latest -f ./Dockerfile.playground .
	docker build -t sandbox/hbase:latest -f ./Dockerfile.hbase .