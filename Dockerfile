# First Stage: Build the application
FROM  maven:3.9.9-amazoncorretto-17-alpine AS build


RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn package

# Second Stage: Run the application
FROM openjdk:24-ea-17-slim-bullseye

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
# Copy the jar file from the build stage
COPY --from=build /usr/src/app/target/LoginAuthApplication.jar app.jar
CMD ["java", "-jar", "app.jar"]
