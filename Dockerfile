# Use an appropriate base image
FROM openjdk:17

# Set the working directory
WORKDIR /app

# Copy the application JAR file into the container
COPY target/your-app.jar .

# Specify the command to run your application
CMD ["java", "-jar", "your-app.jar"]
