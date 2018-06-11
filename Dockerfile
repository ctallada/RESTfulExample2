FROM tomcat:8-jre8
MAINTAINER "ctallada@osius.com"
USER root
ENV CATALINA_HOME /usr/local/tomcat/
RUN echo '%root ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers && \
        sed -i 's/.*requiretty$/Defaults !requiretty/' /etc/sudoers

# add a user for the application, with sudo permissions
RUN useradd -m jenkins ; echo jenkins: | chpasswd ; usermod -a -G root jenkins
#RUN apt-get update -y && apt-get install -y  procps
#COPY tomcat.sh $CATALINA_HOME/bin/tomcat.sh
#RUN chmod +x $CATALINA_HOME/bin/tomcat.sh


RUN groupadd -r jenkins && \
 useradd -g jenkins -d ${CATALINA_HOME} -s /sbin/nologin  -c "Tomcat user" jenkins && \
 chown -R jenkins:jenkins ${CATALINA_HOME} && \
 chmod 400 ${CATALINA_HOME}/conf/* && \
 chmod +x ${CATALINA_HOME}/bin/catalina.sh

#RUN chown -R jenkins:jenkins / && \
#    chmod +x /usr/local/tomcat/bin/catalina.sh
# Copy to images jenkins path
ADD target/RESTfulExample2.war /usr/local/jenkins/webapps/
#ADD target/SpringKube.jar SpringKube.jar
#ENTRYPOINT ["java","-jar","SpringKube.jar"]
WORKDIR ${CATALINA_HOME}/bin
USER jenkins

EXPOSE 8585

ENTRYPOINT ["catalina.sh", "run"]
