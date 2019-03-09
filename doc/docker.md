#compose
docker-compose -f doc/docker-compose.yml -p exampleservice_stack up 
docker-compose -f doc/docker-compose.yml -p exampleservice_stack down --volumes

#CREATE EXTENSION pg_stat_statements;

#prune
docker system prune -a && docker volume prune -f
