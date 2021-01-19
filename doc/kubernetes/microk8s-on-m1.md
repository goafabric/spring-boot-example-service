##Step 1

#documentation
https://microk8s.io/docs

#Microk8s Install (replace admin with your user)
sudo snap install microk8s --classic --channel=1.19
sudo usermod -a -G microk8s admin && sudo chown -f -R admin ~/.kube
su - admin

#Kubectl Alias
sudo sh -c 'echo "#!/bin/bash \n microk8s kubectl "\$1" "\$2" "\$3" "\$4" "\$5" "\$6" "\$7" "\$8" "\$9" " > /usr/local/bin/kubectl' && sudo chmod +x /usr/local/bin/kubectl

#Microk8s Configure
microk8s enable dns dashboard ingress storage

## Step 2
#Hack the Kubernetes dashboard (O is for insert line)
microk8s.kubectl edit deployment/kubernetes-dashboard --namespace=kube-system
- args:
- --enable-skip-login

#Microk8s Run
sudo iptables -P FORWARD ACCEPT && microk8s start && microk8s status --wait-ready

#Client Kubectl
microk8s config > config (should be put to ~/.kube on client machine)


#Linkerd
microk8s enable linkerd
linkerd install | kubectl apply -f -
linkerd uninstall | kubectl delete -f -

#Linkerd CLI download
https://github.com/linkerd/linkerd2/releases/

#Linkerd Dashboard (dd remove line, O insert -enforced-host=)
microk8s.kubectl edit deployment/linkerd-web --namespace=linkerd
- -enforced-host=^(localhost|127\.0\.0\.1|linkerd-web\.linkerd\.svc\.cluster\.local|linkerd-web\.linkerd\.svc|\[::1\])(:\d+)?$
