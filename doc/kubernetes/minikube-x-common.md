#Minikube Remote Dashboard
&& sudo kubectl proxy --address='0.0.0.0' --disable-filter=true
http://SERVERNAME:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/overview?namespace=default
#Microk8s Remote Dashboard
http://kubernetes:8001/api/v1/namespaces/kube-system/services/https:kubernetes-dashboard:/proxy/#/login

## Minikube Debug
minikube start --alsologtostderr --v=2

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

#Kubectl run
sudo kubectl run -i --tty example-service --image=goafabric/spring-boot-exampleservice:1.0.4
sudo kubectl run -i --tty example-service --image=goafabric/spring-boot-exampleservice-arm64v8:1.0.4