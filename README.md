# Weather API

## Despliegue Local

Para desplegar la aplicación en local, sigue estos pasos:

### Prerrequisitos

Asegúrate de tener instalados los siguientes programas:
- [Docker](https://www.docker.com/products/docker-desktop)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Gradle](https://gradle.org/install/)

### Pasos

1. **Descargar las dependencias de Gradle**:

   Ejecuta el siguiente comando en el directorio raíz del proyecto para descargar todas las dependencias necesarias y generar los archivos .jar:
   ./gradlew build
2. **Construir la imagen de Docker**:

   Ejecuta el siguiente comando en el directorio raíz del proyecto para construir la imagen de Docker:
   docker-compose build
3. **Levantar la aplicación**:

   Ejecuta el siguiente comando en el directorio raíz del proyecto para levantar la aplicación:
   docker-compose up
4. **Acceder a la aplicación**:

   Accede a la aplicación a través de la siguiente URL:
   http://localhost:8080/weather/{location}
5. La documentación de la api esta disponible a traves de Swagger UI en la siguiente URL:
   http://localhost:8080/swagger

Nota:
•	Asegúrate de que los puertos configurados en los archivos de configuración no estén siendo utilizados por otros servicios en tu máquina.
•	Puedes modificar la configuración de Redis en el archivo application.conf si es necesario.
