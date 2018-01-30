Create a Non-Secured Client Service via Spring Security to access another ResourceServer with OAuth2/OpenId-Connect
---

A demo app to show 
- how to implement a backend service 
  - not secured by itself
  - using OAuth2RestTemplate (from Spring Security) to access remote secured services 
- Only Spring Security Dependencies:
```
	compile('org.springframework.security.oauth:spring-security-oauth2')
	compile('org.springframework.boot:spring-boot-starter-security')
```

#### configuration:
The configuration is done in the `OAuthRestTemplateConfig.java`. The credentials are stored in the application.yml

#### setup of the keycloak server:
<pre>
Realm: demo-realm
|
|-Client: demo-client
|         | 
|         |-access-type: confidential
|         |-Service Account Enabled: true
|         |-authorization enabled: true
|
|-Roles: demo_user_role, ROLE_DEMO_SPRING_SECURITY
|
|-User: demo-user
|       |-Password: 123
|       |-Role Mappings: demo_user_role, ROLE_DEMO_SPRING_SECURITY
</pre>

## Run the Demo

# Prepare Keycloak
Start a local Keycloak Instance. Using Docker is recommended.
```yaml
version: '2'
services:
  keycloak-service:
    image: jboss/keycloak
    ports:
      - 8280:8080
    environment:
      KEYCLOAK_USER: admin
      KEYCLOAK_PASSWORD: admin

```
- Open the Admin Console with http://localhost:8280/ entering admin/admin.
- Create the realm called: 'demo-realm'
- Import the demo-realm.json
- Open the "demo-client" tab "credentials" and generate a new secret
- Paste the new secret into the application.yml

Start the Demo from https://github.com/HaVonTe1/de.dkutzer.demo.oauth2.spring.boot.security.resourceserver
on Port 8081 or use docker to map dynamic ports.

Now run this app and call GET localhost:8080/person. This will redirect the request to the resourceserver and 
the RestTemplate will enrich the request with an access token from Keycloak. The response from the resourcesever 
will be delegated to this app and results will show up.

---
# Exported Endpoints

- GET /person : responds a fixed list of *person* entities delegated from a resource server app FOR authorised access only

