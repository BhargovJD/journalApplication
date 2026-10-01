1. API CRUD
2. ORM: ORM allows Java objects/classes to work with database tables without writing SQL for every operation.
3. JPA: JPA = Java Persistence API
   JPA is a Java specification that provides rules for storing and retrieving Java objects from a relational database.
4. Spring Data MongoDB
   Spring Data MongoDB is a part of the Spring Data project that makes it easier for a Spring Boot application to work with MongoDB.
5. Query Method DSL in Spring Data
   You describe the query through the method name, and Spring Data generates the query for you.
6. Criteria API in Spring Boot
   Criteria API is used to build database queries dynamically using Java code instead of writing SQL/JPQL directly.
7. Add dependency
   https://central.sonatype.com/artifact/org.springframework.boot/spring-boot-starter-mongodb-test
   <dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-data-mongodb</artifactId>
   </dependency>
8. Controller -> Service -> Repository