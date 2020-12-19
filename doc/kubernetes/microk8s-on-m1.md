#documentation
https://microk8s.io/docs

#Microk8s Install
sudo snap install microk8s --classic --channel=1.19
sudo usermod -a -G microk8s admin && sudo chown -f -R admin ~/.kube
su - admin

#Microk8s Configure
microk8s start
microk8s enable dashboard

#Microk8s Run
microk8s start
           


#kubectl fuck
echo "alias kubectl='microk8s kubectl'" >> ~/.bash_profile
sudo nano /usr/local/bin/kubectl
---
#!/bin/bash
microk8s kubectl $1 $2 $3 $4 $5 $6 $7 $8 $9
---
