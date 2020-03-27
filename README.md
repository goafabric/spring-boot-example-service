# spring-boot-example-service
Spring Boot 2 Example Service with Java 11 and Reactive Support
See doc folder for more Details

# Keycloak

# Build
- Just Build the Project with Maven

# Run
## Keycloak
- docker run -d --name=keycloak -p 8080:8080 -e KEYCLOAK_USER=admin -e KEYCLOAK_PASSWORD=admin jboss/keycloak:8.0.1

## Application
- Application can just be Start via running Application class and listen on 50700
- Actuator Endpoints are without Authentication
- All find Endpoints need authentication and will redirect to Keycloak

# Configuration
## Keycloak
- login is admin:admin on localhost:8080
- Basically you can follow the Baeldung link below
- As Realm choose 'goafabric'

- Create a client "login-app" with redirection URLS '*'
- Create a User
- Create a Role 'STANDARD_ROLE' and set it for the User
 
## Application
- see application.yml, needs realm, login name and Keycloak server URL

# Links
- https://www.baeldung.com/spring-boot-keycloak
- https://www.youtube.com/watch?v=6Z490EMcafs
