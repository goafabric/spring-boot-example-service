# Database Provisioning
docker run --rm -e database.provisioning.goals='-migrate -import-demo-data -import-catalog-data -terminate' \
-e spring.datasource.url='jdbc:h2:mem:countrydb' \
-v /Users/andreas/Projects/IdeaProjects/myprojects/spring_kubernetes/spring-boot-example-service/src/deploy/demodata:/src/deploy/demodata \
goafabric/spring-boot-exampleservice:1.0.4

#SSL
##direct generation
openssl req -x509 -nodes -days 720 -newkey rsa:2048 -keyout goafabric-endentity.key -out goafabric-endentity.pem -subj '/CN=goafabric/O=Goafabric Ltd./C=DE'
