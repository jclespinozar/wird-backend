# Weather API

## Local Deployment

To deploy the application locally, follow these steps:

### Prerequisites

Make sure you have the following programs installed:
- [Docker](https://www.docker.com/products/docker-desktop)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Gradle](https://gradle.org/install/)

### Steps

1. **Build the Docker image**:

   Run the following command in the root directory of the project to build the Docker image
   ```shell
   docker-compose build
   ```
2. **Start the application**:

   Run the following command in the root directory of the project to start the application
    ```shell
   docker-compose up
   ```
3. **Access the application**:

   Access the application through the following URL:
   http://localhost:8080/weather/{location}
4. **API documentation**:

   The API documentation is available through Swagger UI at the following URL:
   http://localhost:8080/swagger

## Note:
- Ensure that the ports configured in the configuration files are not being used by other services on your machine.	
- You can modify the Redis configuration in the application.conf file if necessary.
- You can also modify the *key* in the application.conf file if needed. To obtain your own key, you can sign up at Tomorrow.io to receive one.
