# docker-jenkins
[Jenkins](https://jenkins.io/) sobre [Docker](https://www.docker.com/). Se incluye la instalación de Python y de plugins.

Para construir la imagen, ejecutar el siguiente comando:
```
$ docker-compose build
```

Para arrancar Jenkins, ejecutar el siguiente comando:
```
$ docker-compose up -d
```

Acceder a Jenkins con un navegador a http://localhost:8080

La contraseña del usuario `admin` de Jenkins es: 
```
$ password
```

---

Tags: devops, docker, jenkins, python