cluster:
	docker build -t sandbox/hadoop:latest -f ./hadoop.Dockerfile .
	docker build -t playground/hadoop:latest -f ./playground.Dockerfile .
	docker build -t sandbox/hbase:latest -f ./hbase.Dockerfile .