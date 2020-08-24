# Kubernetes

# Prerequisites
- Download Minikube
- minikube config set cpus 2
- minikube config set memory 4096
- minikube addons enable metrics-server
- minikube config set vm-driver virtualbox (or hyperkit)
- minikube config view

# Run (inside kubernetes folder)
- minikube dashboard
- stack up
- Kubernetes will display the address of the service that can than be accessed

# Remote Dashboard
kubectl proxy --address='0.0.0.0' --disable-filter=true

http://SERVERNAME:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/overview?namespace=default

# Stop
- ./stack down

# Docker
# Prerequisites
- Download and Start Docker

# Run (insode docker folder)
- ./stack up
- Service can than be accessed via http://localhost:50700

# Stop
- ./stack down

#lb test
curl http://192.168.99.102:30100/actuator/info

#Kubernetes Docs:
https://www.cncf.io/wp-content/uploads/2019/07/The-Illustrated-Childrens-Guide-to-Kubernetes.pdf
https://www.cncf.io/wp-content/uploads/2018/12/Phippy-Goes-To-The-Zoo.pdf

#Private registry (like artifactory)
- minikube ssh
- docker login
- sudo cp /root/.docker/config.json /var/lib/kubelet/config.json
=> note "root" does not seem to be the right folder .. can find the post anymore

#Check Sql
select * from country
--
update country set name = 'updated' where id = '1'

#Deployment
kubectl set image deployment example-service example-service=goafabric/spring-boot-exampleservice:1.0.2
kubectl rollout status deployment example-service
kubectl rollout undo deployment example-service

# Database Provisioning
docker run --rm -e database.provisioning.goals='-migrate -import-demo-data -import-catalog-data -terminate' \
-e spring.datasource.url='jdbc:h2:mem:countrydb' \
-v /Users/andreas/Projects/IdeaProjects/myprojects/spring_kubernetes/spring-boot-example-service/src/deploy/demodata:/src/deploy/demodata \
goafabric/spring-boot-exampleservice:1.0.4-SNAPSHOT

#SSL
##direct generation
openssl req -x509 -nodes -days 720 -newkey rsa:2048 -keyout goafabric-endentity.key -out goafabric-endentity.pem -subj '/CN=goafabric/O=Goafabric Ltd./C=DE'
##with root ca
https://www.bytebee.de/certificate-authority-erstellung/

##explanation
- root certificate is the one one that
    - generates the server certificate (end entity)
    - can be valid for 10 years
    - applied at the client side to fulfill chain of trust

- server certificate (end entity)
    - is issued with the help of the root certificate
    - is only valid for 1 or 2 years
    - applied at the server side (here nginx)

- client certificate
    - is something totally different
    - a unique certificate per client
    - can serve as a second factor