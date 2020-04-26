#SSL

##NGinx
https://docs.nginx.com/nginx/admin-guide/web-server/web-server/#

##Cert
https://www.digitalocean.com/community/tutorials/how-to-create-an-ssl-certificate-on-nginx-for-ubuntu-14-04
https://www.digitalocean.com/community/tutorials/how-to-configure-nginx-with-ssl-as-a-reverse-proxy-for-jenkins
https://geekflare.com/san-ssl-certificate/

##RootCa
https://gist.github.com/fntlnz/cf14feb5a46b2eda428e000157447309
https://www.bytebee.de/certificate-authority-erstellung/

##Gen
openssl req -x509 -nodes -days 3650 -newkey rsa:2048 -keyout goafabric-endentity.key -out goafabric-endentity.crt -subj '/CN=Goafabric End Entity/O=Goafabric Ltd./C=DE'
#openssl req -x509 -nodes -days 3650 -newkey rsa:2048 -keyout goafabric-endentity.key -out goafabric-endentity.crt -subj '/CN=Goafabric End Entity/O=Goafabric Ltd./C=DE' -reqexts SAN -config <(cat /etc/ssl/openssl.cnf <(printf "\n[SAN]\nsubjectAltName=DNS:localhost"))

..
cd ssl
## Root CA
## create key, asks for password because of ae256
openssl genrsa -aes256 -out root/goafabric-root.key 4096
## create root certificate
openssl req -x509 -new -nodes -sha512 -days 3650 -key root/goafabric-root.key -out root/goafabric-root.pem -subj '/CN=Goafabric Root/O=Goafabric Ltd./C=DE'

# End Entity
## create key, won't ask for password because no encryption applied 
openssl genrsa -out goafabric-endentity.key 4096
## create signing request (csr)
openssl req -new -sha512 -key goafabric-endentity.key -out goafabric-endentity.csr -subj '/CN=goafabric.org/O=Goafabric Ltd./C=DE'
## create end entity certificate
openssl x509 -req -sha512 -days 3650 -in goafabric-endentity.csr -CA root/goafabric-root.pem -CAkey root/goafabric-root.key -CAcreateserial -out goafabric-endentity.crt