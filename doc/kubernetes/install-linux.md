#system
sudo apt --assume-yes update  
sudo apt --assume-yes install mc && sudo apt --assume-yes install net-tools

#Docker
sudo apt --assume-yes install docker.io && sudo apt --assume-yes install docker-compose  
sudo docker run --rm hello-world
sudo docker volume create portainer_data && sudo docker run -d -p 8000:8000 -p 9000:9000 --name=portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer

#ssh
sudo sed -i '/PasswordAuthentication no/c PasswordAuthentication yes' /etc/ssh/sshd_config && sudo service ssh restart

#user
sudo adduser --ingroup admin admin
sudo usermod -aG sudo admin && sudo usermod -aG docker admin

#Maven
sudo apt --assume-yes install maven