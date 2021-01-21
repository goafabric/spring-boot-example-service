# spring-boot-example-service
- Spring Boot 2 Example Service with Java 11 and Reactive Support
- See doc folder for more Details
             
#Kubernetes
- Do "./stack init" once in uppermost folder of "src/deploy/kubernetes" to create namespaces and secrets
- Afterwards you can just do ./stack up/down in the example namespace
- under <yourip>/welcome you get a nice welcome page (for microk8s just localhost)
- To delete everything "stack prune" in the uppermost folder


# Kubernetes and Docker
- See Readme inside src/deploy sub folder 

# Release notes
- See release-notes file in doc folder for more infos