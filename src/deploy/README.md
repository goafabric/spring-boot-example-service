# Kubernetes

# Prerequisites
- Download Minikube
- minikube start --vm-driver="virtualbox"
- minikube addons enable metrics-server (once)

# Run (inside kubernetes folder)
- minikube dashboard
- stack up
- Kubernetes will display the address of the service that can than be accessed

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
docker run --rm -e database.provisioning.goals='-migrate -import-catalog-data -terminate' -e spring.datasource.url='jdbc:h2:mem:countrydb' goafabric/spring-boot-exampleservice:1.0.2

#SSL
#https://www.digitalocean.com/community/tutorials/how-to-create-an-ssl-certificate-on-nginx-for-ubuntu-14-04
openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout nginx.key -out nginx.crt -subj '/CN=www.mydom.com/O=Goafabric Ltd./C=DE'

