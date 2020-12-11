#resize images for vms if needed
dd if=/dev/zero of=./image seek=20000000 obs=1024 count=0

#example-service
sudo docker run --rm goafabric/spring-boot-exampleservice(-arm64v8):1.0.4-SNAPSHOT

#Examplservice + Graal
(cd /home/admin ; mkdir projects ; cd projects ; git clone https://github.com/goafabric/spring-boot-example-service ; cd spring-boot-example-service ; mvn package)
(cd /home/admin ; cd projects ; git clone https://github.com/goafabric/spring-boot-example-graal ; cd spring-boot-example-graal ; git checkout console-only ; ./linux-native-build.sh )
