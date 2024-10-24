# docker-jenkins
[Jenkins](https://jenkins.io/) sobre [Docker](https://www.docker.com/). Se incluye la instalación de Python y de plugins.

## Contenido

- ⚙️ | [Pre requisitos](#pre-requisitos)
- 📄 | [Archivos a tener en cuenta](#archivos-a-tener-en-cuenta)
- 🏃 | [Correr el ambiente](#correr-el-ambiente)
- 🔨 | [Como usar el ambiente](#como-usar-el-ambiente)
- 📋 | [Notas](#notas)

## Pre requisitos

- Docker instalado
- Git instalado
- Cuenta de Github 

Este proyecto contiene los archivos de configuración necesarios para poder arrancar un contenedor de Docker con Jenkins en su versión estable, la cual contiene un usuario administrador y los plugins necesarios para el pipeline del proyecto de automatización. 

Nuestro directorio de trabajo por defecto será el descargado, y por consecuente será nuestra referencia para la execución de los comando necesarios.

## Archivos a tener en cuenta
- Dockerfile - Es nuestra imagen de referencia y la configuración inicial de nuestra imagen base.
- docker-compose.yml - Es una herramienta que nos permite definir y correr nuestros contenedores.
```
sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io
sudo apt install docker-compose
sudo systemctl enable docker.service
sudo systemctl enable containerd.service
```

**Referencias:** 📚
- [Docker](https://www.docker.com/)
- [Docker compose](https://docs.docker.com/compose/)

## Correr el ambiente
1. Clonar el repositorio:
```
$ git clone git@github.com:SAEST/docker-jenkins.git
```

2. Posicionados en la carpeta del proyecto corremos el siguiente comando, para construir la imagen:
```
$ docker-compose build
```

3. Esperamos a que termine de correr el proceso, nos mostrará el siguiente output
```
root@:docker-jenkins#/docker-compose build
Building master
[+] Building 77.6s (13/13) FINISHED                                                                                                          docker:default
 => [internal] load build definition from Dockerfile                                                                                                   0.0s
 => => transferring dockerfile: 972B                                                                                                                   0.0s
 => [internal] load metadata for docker.io/jenkins/jenkins:lts                                                                                         1.3s
 => [internal] load .dockerignore                                                                                                                      0.0s
 => => transferring context: 56B                                                                                                                       0.0s
 => [1/8] FROM docker.io/jenkins/jenkins:lts@sha256:429647d4688daa3ca2520fb771a391bae8efa1e4def824b32345f13dde223227                                  14.5s
 => [internal] load build context                                                                                                                      0.1s
 => => transferring context: 2.82kB                                                                                                                    0.0s
 => [2/8] RUN apt-get -y update && apt-get install -y     python3     python3-pip                                                                     32.6s
 => [3/8] COPY plugins.txt /usr/share/jenkins/ref/plugins.txt                                                                                          0.0s
 => [4/8] RUN jenkins-plugin-cli -f /usr/share/jenkins/ref/plugins.txt                                                                                27.0s
 => [5/8] COPY basic-security.groovy /usr/share/jenkins/ref/init.groovy.d/basic-security.groovy                                                        0.0s
 => [6/8] COPY create-pipeline.groovy /usr/share/jenkins/ref/init.groovy.d/create-pipeline.groovy                                                      0.0s
 => [7/8] COPY auto-build.groovy /usr/share/jenkins/ref/init.groovy.d/auto-build.groovy                                                                0.0s
 => [8/8] RUN chown -R jenkins:jenkins /var/jenkins_home                                                                                               0.4s
 => exporting to image                                                                                                                                 1.4s
 => => exporting layers                                                                                                                                1.4s
 => => writing image sha256:2ea8c447fa4262578d5dffec26eefbafd75cc203c37ca7a6e644c015fe90a00b                                                           0.0s
 => => naming to docker.io/library/jenkins2                                                                                                            0.0s
```

4. Para arrancar Jenkins, ejecutar el siguiente comando:
```
$ docker-compose up -d
```
-output:
```
root@P24-8KQMDY3:/docker-jenkins# docker-compose up -d
Creating network "docker-jenkins_default" with the default driver
Creating volume "docker-jenkins_jenkins_home2" with default driver
Creating docker-jenkins_master_1 ... done
```
5. Acceder a Jenkins con un navegador a [Localhost](http://localhost:8080/login?from=%2F)

La contraseña del usuario `admin` de Jenkins es: 
```
$ password
```
## Como usar el ambiente

Al construir la imagen del `docker-compose.yml` contiene los plugins necesarios para el proyecto ademas de `Blueocean` y `Allure Reports` 

Al arrancar el docker-jenkins, se crea el Pipeline `Publicacion` en automático asi como se construye por primera vez para validar las configuraciones.

En caso de que no se inicie, se puede construir el Pipeline sin problemas desde `Blue Ocean` y validar los resultados en el reporte de `Allure`.

Arrancar ambiente
```
$ docker compose up --detach 
```

Detener ambiete
```
$ docker compose down
```

## Notas
- Si se necesita instalar algo adicional en el contenedor, habrá que ponerlo en `docker-compose.yml` para mantener la consistencia en el despliegue y mantenimiento.
- Los siguientes comandos son de ayuda para el manejo de contenedores:

### Muestra una lista de los contenedores y tamaño en disco
```
docker ps -as
```
### Iniciar o parar contenedor
```
docker stop docker-jenkins_master_1
docker stop docker-jenkins_master_1
```
### 
``` Borrar contenedor
docker rm container ID_CONTENEDOR
```
### Listar imagenes de dockers
```
docker image ls
```
### borrar imagen creada
```
docker image rm jenkins
```
### Listar volumenes (data)
```
docker volume ls
```
### Borrar volumen creado
```
docker volume rm docker-jenkins_jenkins_home
```
### Elimina todos los contenedores detenidos, todas las redes no utilizadas por los contenedores, todas las imágenes colgadas y toda la caché de construcción.
```
docker system prune
```
### Resumen de contenedores, volumenes, buils y cache
```
docker system df
```

---

Tags: devops, docker, jenkins, python
