FROM tomcat:10.0-jdk17-openjdk-slim
COPY ./shangri-la-petition-platform.jar /app/shangri-la-petition-platform.jar
RUN chmod -R 777 /app/shangri-la-petition-platform.jar
WORKDIR /java/jdk/bin
CMD echo Starting Docker Container
EXPOSE 8080/tcp
VOLUME /data
ENTRYPOINT ["java","-jar","/app/shangri-la-petition-platform.jar"]