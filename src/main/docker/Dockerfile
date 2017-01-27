FROM java:8
VOLUME /tmp/tomcat
COPY verint-spring-boot-0-SNAPSHOT.jar verint.jar
WORKDIR /apps/spring_app
RUN bash -c 'touch /verint.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/verint.jar"]
