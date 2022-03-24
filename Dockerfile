FROM openjdk:11-jre
COPY target/blog-*SNAPSHOT.jar /opt/app.jar
EXPOSE 1603
ENTRYPOINT ["java","-jar","/opt/app.jar"]