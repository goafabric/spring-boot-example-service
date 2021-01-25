# spring-boot-example-service
- Spring Boot 2 Example Service with Java 11 and Reactive Support
- See doc folder for more Details
             
#Kubernetes
- Do "stack init" once in uppermost folder of "src/deploy/kubernetes" to create namespaces and secrets
- Afterwards you can jus do stack up/down in the example namespace
- under <yourip>/welcome you get a nice welcome page
- To delete everything "stack prune" in the uppermost folder

#Dashboard Access on MicroK8s
- To access the Dashboard on MicroK8S you need a token: https://microk8s.io/docs/addon-dashboard

# Kubernetes and Docker
- See Readme inside src/deploy sub folder 

# Release notes
- See release-notes file in doc folder for more infos