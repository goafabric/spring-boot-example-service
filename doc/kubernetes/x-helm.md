#Download
https://github.com/helm/helm/releases

#Repo Add
helm repo add stable https://charts.helm.sh/stable && helm repo update

#Create
helm create mychart
                                           
#Search and install
helm search repo stable
helm install stable/postgresql --generate-name --set image.repository=postgres --set image.tag=11.5

helm ls
helm uninstall postgresql-1609063021
                                
#Doc
https://helm.sh/docs/intro/quickstart/
https://docs.bitnami.com/tutorials/create-your-first-helm-chart/


