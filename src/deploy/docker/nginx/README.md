#HTTPS via NGINX

##HTTPS Preparations
- a dns entry needs to be added to /etc/hosts
=> 127.0.0.1 goafabric.org
- the goafabric-root.pem needs to be added to your trusted certificates inside your OS

##HTTPS Test Drive
- "stack up" inside src/deploy/docker to start the service
- "stack up" inside nginx subfolder
- http://localhost:50700 => access to the barebone service
- http://localhost:8080  => access to the service through NGINX
- httpS://goafabric.org:443  => access to the service through NGINX via HTTPS
=> The preperations step has to be fulfilled otherwise you will get Certifacte Errors inside Chrome

##Certificate Generation
- This is basically a little bit more complicated
- The folder /ssl has to scripts generate a root certifacte and a user certifacte (end entity)
- The first step is to generate the root certifacte
- The second step to generate the user certifacte, based on the root certificate
- The Root certificate is valid for 10 years, while the user certificate is only valid for 1 year
- This means that we have to regenerate the later periodically during update/innstallation
because it will expire very fast
- Additionally a unique domain name is set inside "san.cnf", in this case just goafabric.org
- This is used as a basis for the generation and MUST Match the URL inside the browser, localhost will not work

##Further Explanation
- https://www.bytebee.de/certificate-authority-erstellung/
- root certificate is the one one that
    - generates the server certificate (end entity)
    - can be valid for 10 years
    - applied at the client side to fulfill chain of trust

- server certificate (end entity)
    - is issued with the help of the root certificate
    - is only valid for 1 or 2 years
    - applied at the server side (here nginx)

- client certificate
    - is something totally different
    - a unique certificate per client
    - can serve as a second factor
    
##HTTP2
https://www.digitalocean.com/community/tutorials/how-to-set-up-nginx-with-http-2-support-on-ubuntu-18-04	
https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-configure-http2    