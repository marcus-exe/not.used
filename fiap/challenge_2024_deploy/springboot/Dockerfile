# Use Eclipse Temurin with JDK 11 and Alpine as the base image
FROM eclipse-temurin:17-jdk-alpine

# Install necessary dependencies for running the build
RUN apk add --no-cache bash procps curl tar openssh-client git

# Common labels for the image
LABEL org.opencontainers.image.title="Apache Maven"
LABEL org.opencontainers.image.source=https://github.com/carlossg/docker-maven
LABEL org.opencontainers.image.url=https://github.com/carlossg/docker-maven
LABEL org.opencontainers.image.description="Apache Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information."

# Set Maven environment variables
ENV MAVEN_HOME=/usr/share/maven

# Copy Maven from a Maven base image
COPY --from=maven:3.9.9-eclipse-temurin-17 ${MAVEN_HOME} ${MAVEN_HOME}
COPY --from=maven:3.9.9-eclipse-temurin-17 /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY --from=maven:3.9.9-eclipse-temurin-17 /usr/share/maven/ref/settings-docker.xml /usr/share/maven/ref/settings-docker.xml

# Create a symbolic link to the Maven binary
RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

# Set Maven and user configuration
ARG MAVEN_VERSION=3.9.9
ARG USER_HOME_DIR="/root"
ENV MAVEN_CONFIG="$USER_HOME_DIR/.m2"

# Set the working directory
WORKDIR /app

# Clone your Spring Boot project from GitHub
RUN git clone https://github.com/marcus-exe/challenge_2024_back.git .

# Build the project using Maven
RUN mvn clean package

# Expose the default Spring Boot port
EXPOSE 8080

# Set the entry point to Maven
ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]

# Command to run the application
CMD ["java", "-jar", "target/challenge-0.0.1-SNAPSHOT.jar"]


