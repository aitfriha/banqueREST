# Use an appropriate base image
FROM openjdk:17
EXPOSE 8080
WORKDIR /usr/app
COPY /__w/banqueREST/banqueREST/target/banque-0.0.1-SNAPSHOT.jar.original /usr/app/
     
ENTRYPOINT ["java", "-jar", "banque-0.0.1-SNAPSHOT.jar"]
