#1.0.3

##Added

CrossFunctional
- Postgres Database Monitoring
    - @see InfoActuator
- Fixed Web endpoints inside static\index.html not beeing relative
-

Security
- Encrypted Searchable Fields in Database (less secure)
    - @See: EncryptionConfiguration + @encryptedSearchableString

Persistence
- DemoData added to DatabaseProvisioning

    
Docker
- Nginx Reverse Proxy for HTTP and HTTPS

##Updated

Security
- Upgrade to Spring-Boot-Jasypt 3.0.2 which otherwised clashed badly with jasyppt-hibernate during classloading

Refactoring

#1.0.2
##Added

CrossFunctional
- @DurationLog annotation,
- DurationLogAspect now also logs the logged in user

Security
- Database Encryption with Jasypt
    - @see: EncryptionConfiguration and @TypeDef in CountryBo
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

