#Download Links
https://www.mactechnews.de/forum/discussion/Ubuntu-auf-Apple-Silicon-342450.html

https://medium.com/swlh/building-x86-64-docker-containers-on-apple-silicon-a6d868a18f37
https://github.com/evansm7/vftool

#resize
dd if=/dev/zero of=./image seek=20000000 obs=1024 count=0

#system
sudo apt --assume-yes update  
sudo apt --assume-yes install mc && sudo apt --assume-yes install net-tools

#Docker
sudo apt --assume-yes install docker.io && sudo apt --assume-yes install docker-compose  
sudo docker run hello-world
sudo docker volume create portainer_data && sudo docker run -d -p 8000:8000 -p 9000:9000 --name=portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer

#ssh
sudo sed -i '/PasswordAuthentication no/c PasswordAuthentication yes' /etc/ssh/sshd_config && sudo service ssh restart

#user
sudo adduser --ingroup admin admin
sudo usermod -aG sudo admin && sudo usermod -aG docker admin

#kubernetes install
sudo curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-arm64
sudo curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/arm64/kubectl
sudo chmod +x minikube && sudo mv minikube /usr/local/bin && sudo chmod +x ./kubectl && sudo mv ./kubectl /usr/local/bin/kubectl
                   
#kubernetes run
#sudo docker run goafabric/spring-boot-exampleservice-aarch64:1.0.4-SNAPSHOT
sudo sysctl fs.protected_regular=0
sudo minikube start --driver=none

sudo kubectl proxy --address='0.0.0.0' --disable-filter=true
http://SERVERNAME:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/overview?namespace=default

sudo kubectl run -i --tty example-service --image=goafabric/spring-boot-exampleservice-aarch64:1.0.4-SNAPSHOT