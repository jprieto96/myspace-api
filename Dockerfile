FROM openjdk:8-jdk
VOLUME /tmp
COPY target/myspace-0.0.1-SNAPSHOT.jar myspace.jar
ENTRYPOINT ["java", "-jar", "/myspace.jar"]