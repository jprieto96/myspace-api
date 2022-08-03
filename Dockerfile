FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG PROFILE
ENV PROFILE_VAR=$PROFILE
VOLUME /tmp
COPY target/myspace-0.0.1-SNAPSHOT.jar myspace-api.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=$PROFILE_VAR", "-jar", "/myspace-api.jar"]