FROM jenkins/jenkins:lts

USER root

RUN apt-get -y update && apt-get install -y \
    python3 \
    python3-pip \
    python3-venv \
    wget \
    unzip 

# Descargar e instalar Allure Commandline
RUN wget -q https://github.com/allure-framework/allure2/releases/download/2.30.0/allure-2.30.0.zip && \
    unzip allure-2.30.0.zip -d /opt/ && \
    ln -s /opt/allure-2.30.0/bin/allure /usr/bin/allure && \
    rm allure-2.30.0.zip

#Instalar la version mas actual de chrome
RUN apt-get update && apt-get install -y wget gnupg
RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add -
RUN sh -c 'echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list'
RUN apt-get update && apt-get install -y google-chrome-stable

USER jenkins

# Instalar plugins de Jenkins
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt

# Copiar script Groovy para crear usuario admin
COPY basic-security.groovy /usr/share/jenkins/ref/init.groovy.d/basic-security.groovy

# Copiar el script Groovy que configura Allure Commandline
COPY configure-allure.groovy /usr/share/jenkins/ref/init.groovy.d/configure-allure.groovy

# Copiar el script Groovy que crea el pipeline en la carpeta de scripts de inicio
COPY create-pipeline.groovy /usr/share/jenkins/ref/init.groovy.d/create-pipeline.groovy

# Copiar el script Groovy que construye el pipeline automáticamente
COPY auto-build.groovy /usr/share/jenkins/ref/init.groovy.d/auto-build.groovy

# Asegurarte de que Jenkins tenga acceso al workspace
RUN chown -R jenkins:jenkins /var/jenkins_home

# Desactivar el Setup Wizard de Jenkins
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

USER root
