FROM openjdk:11
VOLUME /tmp
MAINTAINER brunotorrao

ADD build/libs/*.jar app.jar

ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT exec java $JAVA_OPTS \
 -Djava.security.egd=file:/dev/./urandom \
 -jar app.jar
