# Use an appropriate base image
FROM openjdk:17
EXPOSE 8080
COPY /__w/banqueREST/banqueREST/target/banque-0.0.1-SNAPSHOT.jar /usr/app/
     
WORKDIR /usr/app
ENTRYPOINT ["java", "-jar", "banque-0.0.1-SNAPSHOT.jar"]
