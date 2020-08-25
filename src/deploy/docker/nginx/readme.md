HTTPS preparations
- a dns entry needs to be added to /etc/hosts
=> 127.0.0.1 goafabric.org
- the goafabric-root.pem needs to be added to your trusted certificates inside your OS

HTTPS Test Drive
- "stack up" inside src/deploy/docker to start the service
- "stack up" inside nginx subfolder
- http://localhost:50700 => access to the barebone service
- http://localhost:8080  => access to the service through NGINX
- httpS://goafabric.org:443  => access to the service through NGINX via HTTPS
