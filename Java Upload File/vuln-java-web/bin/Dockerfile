# Base image dùng Tomcat 9 + JDK 8
FROM tomcat:9.0-jdk8

# Tên WAR file (build từ Maven)
ARG WAR_FILE=target/vuln-java-web.war

# Xóa các ứng dụng mặc định (docs, examples, ROOT...)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR vào webapps
COPY target/ROOT.war /usr/local/tomcat/webapps/ROOT.war


# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
# docker build -t vuln-jsp-app .
# docker run -p 8080:8080 vuln-jsp-app
# http://localhost:8080/