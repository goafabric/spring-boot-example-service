#deployment
docker tag goafabric/spring-boot-exampleservice goafabric/spring-boot-exampleservice:1.0.0-SNAPSHOT
docker push goafabric/spring-boot-exampleservice:1.0.0-SNAPSHOT
docker pull goafabric/spring-boot-exampleservice:1.0.0-SNAPSHOT

#run
docker run -p 50700:50700 --name exampleservice -t goafabric/spring-boot-exampleservice:1.0.0-SNAPSHOT

#create
docker rm exampleservice
docker create -p 50700:50700 --name exampleservice -t goafabric/spring-boot-exampleservice:1.0.0-SNAPSHOT
docker start exampleservice
docker logs -f exampleservice

#prune
docker system prune -a && docker volume prune -f

#swarm
docker swarm init
docker stack deploy -c docker-compose.yml example_stack
docker stack rm example_stack

docker service logs -f exampleservice_example_net


#compose
docker-compose -f doc/docker-compose.yml -p exampleservice_stack up -d
docker-compose -f doc/docker-compose.yml -p exampleservice_stack down --volumes



--------------



---

