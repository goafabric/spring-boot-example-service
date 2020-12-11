#Minikube Install
sudo curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-arm64
sudo curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/arm64/kubectl
sudo chmod +x minikube && sudo mv minikube /usr/local/bin && sudo chmod +x ./kubectl && sudo mv ./kubectl /usr/local/bin/kubectl

#Minikube Configure
sudo sysctl fs.protected_regular=0 && sudo minikube start --driver=none && sudo kubectl proxy --address='0.0.0.0' --disable-filter=true &
sudo minikube addons enable metrics-server && minikube addons enable ingress

#Minikube Run
sudo sysctl fs.protected_regular=0 && sudo minikube start --driver=none
sudo minikube dashboard


