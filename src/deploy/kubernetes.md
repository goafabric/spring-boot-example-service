#Kubernetes

#Minikube Linux Download 
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64

curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl

chmod +x minikube && sudo mv minikube /usr/local/bin

chmod +x ./kubectl && sudo mv ./kubectl /usr/local/bin/kubectl

#Minikube Config

minikube config set cpus 4
minikube config set memory 8192
minikube config set vm-driver virtualbox
minikube addons enable metrics-server
minikube addons enable ingress

minikube dashboard

#Minikube Remote Dashboard

kubectl proxy --address='0.0.0.0' --disable-filter=true

http://SERVERNAME:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/overview?namespace=default

#Minikube Private Registry
- minikube ssh
- docker login
- sudo cp .docker/config.json /var/lib/kubelet/config.json

#Minikube Mac Download
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-darwin-amd64
curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/darwin/amd64/kubectl

chmod +x minikube && sudo mv minikube /usr/local/bin
chmod +x ./kubectl && sudo mv ./kubectl /usr/local/bin/kubectl

#Kubernetes Docs:
https://www.cncf.io/wp-content/uploads/2019/07/The-Illustrated-Childrens-Guide-to-Kubernetes.pdf
https://www.cncf.io/wp-content/uploads/2018/12/Phippy-Goes-To-The-Zoo.pdf

#Deployment
kubectl set image deployment example-service example-service=goafabric/spring-boot-exampleservice:1.0.2
kubectl rollout status deployment example-service
kubectl rollout undo deployment example-service