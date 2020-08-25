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

#Deployment
kubectl set image deployment example-service example-service=goafabric/spring-boot-exampleservice:1.0.2
kubectl rollout status deployment example-service
kubectl rollout undo deployment example-service