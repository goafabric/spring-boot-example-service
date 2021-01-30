#1.0.5


#1.0.5-SNAPSHOT
## Updated
CrossFunctional
- Spring 2.4.2
- Swagger updated to 3.0.0
- AdoptOpenJDK Baseimage set to a specific version
- Upgrade to Postgres 13.1
- Prometheus metrics added

## Fix  
- Fix for Openj9 Heapdump
- Fix for Lombok / Mapstruct problem since Spring 2.4.0 (see maven-compiler-plugin section in parent-pom)

##Added
- Jaeger Tracing, currently disabled

#1.0.3

##Added

CrossFunctional
- Postgres Database Monitoring
    - @see InfoActuator
- Fixed Web endpoints inside static\index.html not beeing relative

Security
- Encrypted Searchable Fields in Database (less secure)
    - @See: EncryptionConfiguration + @encryptedSearchableString

Persistence
- DemoData added to DatabaseProvisioning
- Discriminator Based Multi Tenancy added, @see all classes prefixed with "Tenant" and CountryBo
    - Possible Alternatives that do not work
        https://medium.com/@vivareddy/muti-tenant-with-discriminator-column-hibernate-implementation-a363f03b1d10
        https://bytefish.de/blog/spring_boot_multitenancy/
    
Docker
- Nginx Reverse Proxy for HTTP and HTTPS

##Updated

Security
- Upgrade to Spring-Boot-Jasypt 3.0.2 which otherwised clashed badly with jasyppt-hibernate during classloading
- Upgrade to Spring Boot 2.3.0


#1.0.2
##Added

CrossFunctional
- @DurationLog annotation,
- DurationLogAspect now also logs the logged in user

Security
- Database Encryption with Jasypt
    - @see: EncryptionConfiguration and @TypeDef in domain\package-info.java
- Passphrase now gets stored inside Database
    - @see: EncryptionConfiguration 

##Updated

CrossFunctional
- Small refactorings and moving classes around

Persistence
- Fixed a bug that caused all updates to cause inserts (version prop was missing in dto)

Docker
- Timezone added to Compose File and Kubernetes yml

#1.0.1
##Added

API
- Swagger
    - @see SwaggerConfiguration and @ApiOperation inside CountryService
    - Dependencies: io.springfox*
- Bean Validation for API
    - @see @NotNull and @Size inside Country DTO, @Valid inside CountryService, ExceptionHandler
    - Dependencies: spring-boot-starter-validation 

Adapter
- CalleeServiceAdapter to call an external Service
- Resilience4j
    - @see @CircuitBreaker inside CalleServiceAdapter, application.yml
    - Dependencies: resilience4j

Persistence
- Database Provisoning added
    - @see class DatabaseProvisioning

Security
- Password Encryption with Jaspyt, Password Hashing with Bcrypt
    - @see: SecurityConfiguration, EncryptionConfiguration, Application.yml + EncryptionIT

##Updated

POM
- Update to Spring Boot 2.2.6
- Update to Jib 2.1.0
    - seems to fix build Problems with Jenkins inside OpenStack

#1.0.0 
##Initial
- initial release, with all the good stuff inside like REST, Spring DATA JPA, Mapstruct...

