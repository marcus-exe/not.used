# Use the official Tomcat image from Docker Hub
FROM docker.io/tomcat:latest

# Copy your JSP project into the Tomcat webapps directory
COPY target/servlet-demo-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expose the default Tomcat port
EXPOSE 8080

CMD ["catalina.sh", "run"]