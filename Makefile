cluster:
	clear
	docker build -t hadoop:sandbox -f hadoop.Dockerfile .
	docker build -t playground:sandbox -f playground.Dockerfile .
	docker build -t hbase:sandbox -f hbase.Dockerfile .

up:
	clear && docker compose up -d --remove-orphans

start:
	clear && docker compose start

run:
	clear && docker compose up --remove-orphans

stop:
	clear && docker compose stop

down:
	clear && docker compose down

reset:
	clear && docker compose down -v