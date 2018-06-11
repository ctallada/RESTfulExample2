From tomcat:8-jre8
RUN groupadd -g 999 jenkins $$ \
    useradd -r 999 -g jenkins jenkins
USER jenkins
MAINTAINER "ctallada@osius.com"
EXPOSE 8585
# Copy to images tomcat path
ADD target/RESTfulExample2.war /usr/local/tomcat/webapps/
#ADD target/SpringKube.jar SpringKube.jar
#ENTRYPOINT ["java","-jar","SpringKube.jar"]
CMD ["catalina.sh", "run"]
