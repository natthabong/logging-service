FROM openjdk:8-jdk-slim
RUN echo "Asia/Bangkok" >  /etc/timezone
VOLUME ["/tmp", "/upload"]
ADD target/${project.artifactId}-${project.version}.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]