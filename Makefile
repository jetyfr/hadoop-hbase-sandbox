cluster:
	docker build -t sandbox/hadoop:latest -f hadoop.Dockerfile .
	docker build -t sandbox/playground:latest -f playground.Dockerfile .
	docker build -t sandbox/hbase:latest -f hbase.Dockerfile .
	docker build -t sandbox/zookeeper:latest -f zookeeper.Dockerfile .