#Minikube install
curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-darwin-amd64
curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/darwin/amd64/kubectl

sudo chmod +x minikube && sudo mv minikube /usr/local/bin && sudo chmod +x ./kubectl && sudo mv ./kubectl /usr/local/bin/kubectl

#Minikube Configure
minikube config set cpus 4
minikube config set memory 8192
minikube config set vm-driver virtual-box

minikube start
minikube addons enable metrics-server && minikube addons enable dashboard && minikube addons enable ingress

#Minikube Run
minikube start
minikube dashboard

