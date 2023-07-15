cluster:
	docker build -t hadoop:sandbox -f hadoop.Dockerfile .
	docker build -t playground:sandbox -f playground.Dockerfile .
	docker build -t hbase:sandbox -f hbase.Dockerfile .
	docker build -t zookeeper:sandbox -f zookeeper.Dockerfile .