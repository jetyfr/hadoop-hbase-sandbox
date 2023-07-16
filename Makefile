cluster:
	clear
	docker image prune -f
	docker build -t hadoop:sandbox -f base/hadoop.Dockerfile base
	docker build -t playground:sandbox -f base/playground.Dockerfile base
	docker build -t hbase:sandbox -f base/hbase.Dockerfile base

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

status:
	clear && docker images && docker volume ls && docker ps -a

connect:
	clear && docker exec -it resourcemanager bashS