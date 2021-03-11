# Security
- Real Certificates / Certmanager (https://tinyurl.com/1wjhxz9m)
- Outbound SSL
- Multi Tenancy
- Networks Seperation (https://gardener.cloud/documentation/guides/applications/network-isolation/)
- Keycloak

# Ops 
- Jaeger
- Statefulsets
- Linkerd Upgrade to 2.10 on Openstack

- Monitoring (Prometheus, Graphana ...)
- Helm 

- JProfiler Test for Docker + Kubi
- NGinx Rewrites, Mod Security, Websockets (https://tinyurl.com/4zyhd2hf)

---
## Ingress
https://kubernetes.io/docs/concepts/services-networking/ingress/#path-types

##External Oauth
https://kubernetes.github.io/ingress-nginx/examples/auth/oauth-external-auth/

##Postgres Kubernetes Links
https://contentlab.io/postgresql-on-kubernetes/
https://medium.com/@suyashmohan/setting-up-postgresql-database-on-kubernetes-24a2a192e962

#hostname from kubectl
kubectl get node -o wide

#Keycloak
##Authentication via Keycloak
#nginx.ingress.kubernetes.io/auth-url: "https://$host/oauth2/auth"
#nginx.ingress.kubernetes.io/auth-signin: "https://$host/oauth2/start?rd=$escaped_request_uri"

