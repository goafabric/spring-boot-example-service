#example-service docker
docker run -p50700:50700 --rm goafabric/spring-boot-exampleservice-(arm64v8):1.0.4-SNAPSHOT

#Maven
sudo apt --assume-yes install maven

#Examplservice + Graal
(cd /home/admin ; mkdir projects ; cd projects ; git clone https://github.com/goafabric/spring-boot-example-service ; cd spring-boot-example-service ; mvn package)
(cd /home/admin ; cd projects ; git clone https://github.com/goafabric/spring-boot-example-graal ; cd spring-boot-example-graal ; git checkout console-only ; ./linux-native-build.sh )

#Quemu for Amd64 Software, not recommended
sudo apt --assume-yes install qemu-system-x86 qemu-user qemu-user-static