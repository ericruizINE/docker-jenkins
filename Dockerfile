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

# Copiar el script Groovy que crea el pipeline en la carpeta de scripts de inicio
COPY create-pipeline.groovy /usr/share/jenkins/ref/init.groovy.d/create-pipeline.groovy

# Copiar el script Groovy que construye el pipeline automáticamente
COPY auto-build.groovy /usr/share/jenkins/ref/init.groovy.d/auto-build.groovy

# Asegurarte de que Jenkins tenga acceso al workspace
RUN chown -R jenkins:jenkins /var/jenkins_home

# Desactivar el Setup Wizard de Jenkins
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

USER root