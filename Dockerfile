From tomcat:8-jre8
MAINTAINER "ctallada@osius.com"
EXPOSE 8080
# Copy to images tomcat path
COPY tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
COPY context.xml /usr/local/tomcat/webapps/manager/META-INF/context.xml

CMD ["catalina.sh", "run"]
