FROM openjdk:8
EXPOSE 3434
ADD target/spring-docker-mysql.jar spring-docker-mysql.jar

ENTRYPOINT ["java", "-jar", "spring-docker-mysql.jar"]