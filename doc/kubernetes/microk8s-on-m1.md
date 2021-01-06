#documentation
https://microk8s.io/docs

#Microk8s Install
sudo snap install microk8s --classic --channel=1.19
sudo usermod -a -G microk8s admin && sudo chown -f -R admin ~/.kube
su - admin

#Kubectl Alias
sudo sh -c 'echo "#!/bin/bash \n microk8s kubectl "\$1" "\$2" "\$3" "\$4" "\$5" "\$6" "\$7" "\$8" "\$9" " > /usr/local/bin/kubectl' && sudo chmod +x /usr/local/bin/kubectl

#Microk8s Configure
microk8s enable dns dashboard ingress storage
microk8s enable linkerd

#Hack the dashboard (O is for insert line)
microk8s.kubectl edit deployment/kubernetes-dashboard --namespace=kube-system
- args:
- --enable-skip-login


#Linkerd Dashboard (dd remove line, O insert -enforced-host=)
microk8s.kubectl edit deployment/linkerd-web --namespace=linkerd

- -enforced-host=^(localhost|127\.0\.0\.1|linkerd-web\.linkerd\.svc\.cluster\.local|linkerd-web\.linkerd\.svc|\[::1\])(:\d+)?$

#Microk8s Run
sudo iptables -P FORWARD ACCEPT && microk8s start && microk8s status --wait-ready 
kubectl proxy --address='0.0.0.0' --disable-filter=true &

#Client Kubectl
microk8s config > config (should be put to ~/.kube on client machine) 

#Quemu for Amd64 Software, not recommended
sudo apt --assume-yes install qemu-system-x86 qemu-user qemu-user-static

#Linkerd CLI
https://github.com/linkerd/linkerd2/releases/download/stable-2.9.0/linkerd2-cli-stable-2.9.0-darwin