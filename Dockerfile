# Dockerfile

# 1. Use an official Tomcat base image
FROM tomcat:9.0

# 2. Set environment variables (Optional)
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

# 3. Copy your compiled WAR file to the Tomcat webapps directory
COPY target/CryptoWebService.war /usr/local/tomcat/webapps/

# 4. Expose the default Tomcat port
EXPOSE 8080

# 5. Run Tomcat server
CMD ["catalina.sh", "run"]

