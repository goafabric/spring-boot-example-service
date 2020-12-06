#Kubernetes

#init
- configure minikube yourself or use provision/minikube-download-mac script
=> be sure to configure accordingly to your system
- do "stack prov" once inside kubernetes/infra/global"
- do "stack up/down" inside example or sub folders 
- minikube dashboard brings up the dashboard inside your browser
- that's it, the following sections are only for more details


#Minikube Config

minikube config set cpus 4
minikube config set memory 8192
minikube config set vm-driver virtualbox

minikube start

minikube addons enable metrics-server
minikube addons enable ingress

minikube dashboard

## Minikube Debug
minikube start --alsologtostderr --v=2

#Minikube Remote Dashboard

kubectl proxy --address='0.0.0.0' --disable-filter=true
http://SERVERNAME:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/overview?namespace=default

#Minikube Private Registry
- minikube ssh
- docker login
- sudo cp .docker/config.json /var/lib/kubelet/config.json

#Minikube Profiles
minikube start -p new
minikube profile new
minikube delete -p new

minikube profile default

#Rollout and Update
kubectl set image deployment example-service example-service=goafabric/spring-boot-exampleservice:1.0.2
kubectl rollout status deployment example-service
kubectl rollout undo deployment example-service

#Vbox Portforwarding
vboxmanage controlvm "minikube" natpf1  "country-service,tcp,,30600,,30600“
vboxmanage modifyvm "minikube" --natpf1  delete country-service

#Kubernetes Docs:
https://www.cncf.io/wp-content/uploads/2019/07/The-Illustrated-Childrens-Guide-to-Kubernetes.pdf
https://www.cncf.io/wp-content/uploads/2018/12/Phippy-Goes-To-The-Zoo.pdf
