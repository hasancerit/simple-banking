# SIMPLE BANKING

---
## Base Technologies
- Java 17
- Spring Boot 3
- JPA (Hibernate 6.2.0)
- Liquibase
- H2
- MySql
- JUnit5
- Mockito
- Lombok
- Swagger
- Maven
- Sonar
- TDD, DDD
---

## How to Run Application
**1 - Run With Docker Compose**<br>
In the base path run this command:
```
mvn clean install
```
```
docker compose up
```
In this way, application work with h2 Database<br>
If you want to connect another database;<br>Open docker-compose file in the base path and activate the commented line (JAVA_OPTS).


**2 - Run With IDE**<br>
You can set the active profile to "h2", so application works with h2 Database<br>
If you want to connect another database; you can change DATASOURCE_URL, DATASOURCE_USERNAME, DATASOURCE_PASSWORD and DATASOURCE_DRIVER_CLASS_NAME arguments in application.yml.
You can pass this parameters as program arguments too. 

**3 - Run with java -jar**
```
mvn clean install
```

```
java -Dspring.profiles.active=h2 -jar ./target/simplebanking.jar
```
In this way, application work with h2 Database<br>
If you want to connect another database:
```
java -DDATASOURCE_URL={DATASOURCE_URL} -DDATASOURCE_USERNAME={DATASOURCE_USER} -DDATASOURCE_PASSWORD={PASSWORD} -DDATASOURCE_DRIVER_CLASS_NAME={DATASOURCE_DRIVER_CLASS_NAME} -jar ./target/simplebanking.jar
```
<br>

**Note: The application tested with MySql and H2. So problems may occur with another databases.**

---
**Swagger UI**<br>
&nbsp;&nbsp;&nbsp;&nbsp;http://localhost:8080/swagger-ui/index.html#/account-controller/credit

**H2 (If you use)**<br>
&nbsp;&nbsp;&nbsp;&nbsp;http://localhost:8080/h2-console <br>
&nbsp;&nbsp;&nbsp;&nbsp;**Username:** h2<br>
&nbsp;&nbsp;&nbsp;&nbsp;**Password:** password<br>
&nbsp;&nbsp;&nbsp;&nbsp;**JDBC URL:** jdbc:h2:mem:testdb<br>
<br><br>
---

## Database Diagram

![db-diagram.png](db-diagram.png)
#### Default Data
| ACCOUNT_NUMBER | BALANCE | OWNER          | CREATED_DATE |
|----------------|---------|----------------|--------------|
| 111-1111       | 0       | Simple Banking | {generated}  |

---

## Sonar

If you want to review project with sonar, run this command in base path:
```
mvn clean verify sonar:sonar \                                                                                            
  -Dsonar.projectKey={sonar-project-key} \
  -Dsonar.host.url={sonar-url} \
  -Dsonar.login={sonar-token}
```
Also visit the link: https://docs.sonarqube.org/9.6/try-out-sonarqube/
<br>Last Sonar Report:
![sonar.png](sonar.png)