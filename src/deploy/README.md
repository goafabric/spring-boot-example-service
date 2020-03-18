# Kubernetes

# Prerequisites
- Download Minikube
- minikube start --vm-driver="virtualbox"
- minikube addons enable metrics-server (once)

# Run (inside kubernetes folder)
- minikube dashboard
- stack up
- Kubernetes will display the Adress of the service that can than be accessed

# Stop
- ./stack down

# Docker
# Prerequisites
- Download and Start Docker

# Run (insode docker folder)
- ./stack up
- Service can than be accessed via http://localhost:50700

# Stop
- ./stack down

#lb test
curl http://192.168.99.102:30100/actuator/info

Kubernetes Docs:
https://www.cncf.io/wp-content/uploads/2019/07/The-Illustrated-Childrens-Guide-to-Kubernetes.pdf
https://www.cncf.io/wp-content/uploads/2018/12/Phippy-Goes-To-The-Zoo.pdf