FROM tomcat:8-jre8
MAINTAINER "ctallada@osius.com"
RUN echo '%sudo ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers && \
        sed -i 's/.*requiretty$/Defaults !requiretty/' /etc/sudoers

# add a user for the application, with sudo permissions
RUN useradd -m jenkins ; echo jenkins: | chpasswd ; usermod -a -G sudo jenkins
RUN chmod +x /usr/local/tomcat/bin/*sh
USER jenkins

CMD chown -R jenkins:jenkins /
# Copy to images tomcat path
ADD target/RESTfulExample2.war /usr/local/tomcat/webapps/
#ADD target/SpringKube.jar SpringKube.jar
#ENTRYPOINT ["java","-jar","SpringKube.jar"]

EXPOSE 8585
CMD ["catalina.sh", "run"]
