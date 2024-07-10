# Use a base image with Java and Maven
FROM maven:3.9.4-amazoncorretto-20 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project descriptor files (pom.xml) to the container
COPY pom.xml .

# Copy the entire project source
COPY src ./src

# Build the application with Maven
RUN mvn clean package -DskipTests

# Stage 2: Use a smaller base image for Java applications
FROM openjdk:8-jdk-alpine3.8

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Specify the command to run your application
CMD ["java", "-jar", "app.jar"]
