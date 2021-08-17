# Stage that builds the application, a prerequisite for the running stage
FROM maven:3-openjdk-16-slim as build

# Stop running as root at this point
RUN useradd -m usuario
WORKDIR /usr/src/app/
RUN chown usuario:usuario /usr/src/app/
USER usuario

# Copy all needed project files to a folder
COPY --chown=usuario pom.xml ./
COPY --chown=usuario:usuario src src

# Build the production package, assuming that we validated the version before so no need for running tests again
#RUN mvn clean package -DskipTests -Pproduction
RUN mvn clean package

# Running stage: the part that is used for running the application
FROM openjdk:16-jdk-slim
COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar
RUN useradd -m usuario
USER usuario
EXPOSE 8080
CMD java -jar /usr/app/app.jar
