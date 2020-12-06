#Download Links
Vftool and Ready Ubuntu images
https://www.mactechnews.de/forum/discussion/Ubuntu-auf-Apple-Silicon-342450.html

#Boot
./vftool -k vmlinuz -i initrd -d image -p 2 -m 4096 -a "root=/dev/vda console=hvc0"

screen /dev/ttys001
ssh admin@192.168.64.18

#Docker
apt install docker.io
apt install docker-compose

sudo adduser --ingroup admin admin
sudo usermod -aG sudo admin
sudo usermod -aG docker admin

docker run goafabric/spring-boot-exampleservice-aarch64:1.0.4-SNAPSHOT

#ssh
sudo sed -i '/PasswordAuthentication no/c PasswordAuthentication yes' /etc/ssh/sshd_config
sudo service ssh restart

#kubernetes

sudo curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-arm64
sudo curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/arm64/kubectl

sudo chmod +x minikube && sudo mv minikube /usr/local/bin
sudo chmod +x ./kubectl && sudo mv ./kubectl /usr/local/bin/kubectl

sudo sysctl fs.protected_regular=0
sudo minikube start --driver=none

sudo kubectl proxy --address='0.0.0.0' --disable-filter=true
http://SERVERNAME:8001/api/v1/namespaces/kubernetes-dashboard/services/http:kubernetes-dashboard:/proxy/#/overview?namespace=default

sudo kubectl run -i --tty example-service --image=goafabric/spring-boot-exampleservice-aarch64:1.0.4-SNAPSHOT