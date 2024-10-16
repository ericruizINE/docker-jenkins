FROM jenkins/jenkins:lts

USER root

RUN apt-get -y update && apt-get install -y \
    python3 \
    python3-pip

USER jenkins

# Instalar plugins de Jenkins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

# Copiar script Groovy para crear usuario admin
COPY basic-security.groovy /usr/share/jenkins/ref/init.groovy.d/basic-security.groovy