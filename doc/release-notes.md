#1.0.1-SNAPSHOT

##Added

API
- Swagger
    - @see SwaggerConfiguration and @ApiOperation inside CountryService
    - Dependencies: io.springfox*
- Bean Validation for API
    - @see @NotNull and @Size inside Country DTO, ExceptionHandler
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
    - @see: SecurityConfiguration, Application.yml + EncryptionIT

##Updated

POM
- Update to Spring Boot 2.2.6
- Update to Jib 2.1.0
    - seems to fix build Problems with Jenkins inside OpenStack

#1.0.0 
- initial release, with all the good stuff inside like REST, Spring DATA JPA, Mapstruct...

