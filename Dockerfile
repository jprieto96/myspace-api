FROM openjdk:8-jdk-alpine
EXPOSE 8080
VOLUME /tmp
COPY target/myspace-0.0.1-SNAPSHOT.jar myspace-api.jar
ENTRYPOINT ["java", "-jar", "/myspace-api.jar"]