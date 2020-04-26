#SSL

##NGinx
https://docs.nginx.com/nginx/admin-guide/web-server/web-server/#

##Cert
https://www.digitalocean.com/community/tutorials/how-to-create-an-ssl-certificate-on-nginx-for-ubuntu-14-04
https://www.digitalocean.com/community/tutorials/how-to-configure-nginx-with-ssl-as-a-reverse-proxy-for-jenkins

##RootCa
https://gist.github.com/fntlnz/cf14feb5a46b2eda428e000157447309
https://www.bytebee.de/certificate-authority-erstellung/

##Gen
openssl req -x509 -nodes -days 3650 -newkey rsa:2048 -keyout nginx.key -out nginx.crt -subj '/CN=www.mydom.com/O=Goafabric Ltd./C=DE'
