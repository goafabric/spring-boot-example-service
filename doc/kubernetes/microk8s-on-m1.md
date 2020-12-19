#documentation
https://microk8s.io/docs

#Microk8s Install
sudo snap install microk8s --classic --channel=1.19
sudo usermod -a -G microk8s admin && sudo chown -f -R admin ~/.kube
su - admin

#Microk8s Configure
microk8s start
microk8s enable dns dashboard ingress

#Microk8s Run
microk8s start
microk8s status --wait-ready
kubectl proxy --address='0.0.0.0' --disable-filter=true &

cd ~/projects/spring-boot-example-service/src/deploy/kubernetes/example/
         
#Hack the dashboard
microk8s.kubectl edit deployment/kubernetes-dashboard --namespace=kube-system
- args:
- --enable-skip-login

#kubectl fuck
echo "alias kubectl='microk8s kubectl'" >> ~/.bash_profile
sudo nano /usr/local/bin/kubectl
---
#!/bin/bash
microk8s kubectl $1 $2 $3 $4 $5 $6 $7 $8 $9
---
