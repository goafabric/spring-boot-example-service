#Minikube install
sudo curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl

sudo chmod +x minikube && sudo mv minikube /usr/local/bin && sudo chmod +x ./kubectl && sudo mv ./kubectl /usr/local/bin/kubectl

#Minikube Configure
minikube config set cpus 4
minikube config set memory 4096
minikube config set vm-driver docker

minikube start
minikube addons enable metrics-server && minikube addons enable dashboard && minikube addons enable ingress

#Minikube Run
minikube start
minikube dashboard

