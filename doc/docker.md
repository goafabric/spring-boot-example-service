#compose
docker-compose -f docker/exampleservice_stack/docker-compose.yml up -d
docker-compose -f docker/exampleservice_stack/docker-compose.yml down --volumes

#CREATE EXTENSION pg_stat_statements;

#prune
docker system prune -a && docker volume prune -f
