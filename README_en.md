# Book Store Web Application

Hello and welcome to the online book store. This project is a web application designed to provide users with a convenient and accessible way to purchase and receive the books they desire.

## Overview

### Functionality

- **User**: Have access to authentication, browsing the catalog of books, viewing details of each book, exploring book categories, sorting book lists, adding books to the cart, and placing orders with specified delivery addresses.

- **Admin**: Have access to adding new books and categories, updating books and categories, managing order status, and handling other administrative tasks. Our admins have full control over the store's inventory and features.

- You can also watch a short video presentation of the project: [Store Overview](https://www.loom.com/share/28eb2fac38354307b9b1f1358581d13c)

### Technical Stack and Tools

- **Authentication and Authorization**: The web application uses JWT tokens to ensure user data security and authentication. We place a strong emphasis on securing our users' data and ensuring their confidentiality.

- **Pagination System**: Pagination is used for convenience in distributing a large amount of data across pages. We make every effort to provide fast and efficient access to the book catalog, even with a substantial amount of information.

- **Data Validation**: The application uses Hibernate Validator and Jakarta Validation annotations to validate user input data. Our system ensures that all user-entered data is valid and meets requirements.

- **Programming Language and Frameworks**: The project is written in Java and utilizes the Spring Boot framework. This technology stack is chosen to ensure high performance and scalability of the web application.

- **Database Management**: MySQL is used as the database with the capability of extending to other SQL databases. Our database stores a vast amount of information about books and orders.

- **Libraries and Technologies**: The project utilizes libraries such as MapStruct and Lombok, along with the Liquibase technology for convenient and rapid data development and management.

- **Security and Roles**: Spring Security is employed to ensure user security, authentication, and authorization. We closely monitor all updates and vulnerabilities to guarantee the highest level of security.

- **Built with Docker**: The application is containerized with Docker for easy deployment and execution on various devices. Docker allows us to quickly and conveniently deploy the application on new servers and environments.

## Usage

To get started with the "Book Store Web Application," you need to follow a few simple steps.

### Using Docker

1. **Ensure Docker is installed on your computer**: If not already installed, you can download it from the official Docker website: [Get Docker](https://www.docker.com/get-started)
2. **Clone the project**: Clone the repository and navigate to the project folder: `git clone https://github.com/IhorKalaur/BookStore.git` and `cd bookstore`.
3. **Create a Docker image**: Run the following command to build the Docker image: `docker-compose build`.
4. **Run the container with your application**: After successfully building the image, execute the command to run the containers: `docker-compose up`. This command will start two containers, one for the MySQL database (mysqldb) and the other for your application (app). The application will automatically connect to the database.
5. **URL**: The application will be accessible at [URL](http://localhost:8081/api).
6. **Add initial data**: Add initial data and an administrator through the console or API. By default, there is already an administrator in the database with the username "admin@admin.ua" and password "12345678".
7. **Testing with Postman**: You can use the prepared collection of JSON data for testing the API with Postman: [Collection](BookStoreApp.postman_collection.json).
8. **Swagger**: You can check the API documentation using Swagger. It provides a simple and intuitive interface for verifying API functions: [Swagger](http://localhost:8081/api/swagger-ui/index.html).

### Using without Docker

1. **Clone the project**: Clone the repository and navigate to the project folder: `git clone https://github.com/IhorKalaur/BookStore.git` and `cd bookstore`.
2. **Configure the database**: Set the necessary configuration for connecting to your database in the application.properties file.
3. **Install Maven**: Run the command for building and configuring the project: `mvn clean install`.
4. **Run the project**: Start the application using Maven: `mvn spring-boot:run`.
5. **URL**: After these steps, the API will be accessible at [URL](http://localhost:8080/api).
6. **Add initial data**: Add initial data and an administrator through the console or API. By default, there is already an administrator in the database with the username "admin@admin.ua" and password "12345678".
7. **Testing with Postman**: You can use the prepared collection of JSON data for testing the API with Postman: [Collection](BookStoreApp.postman_collection.json).
8. **Swagger**: You can check the API documentation using Swagger. It provides a simple and intuitive interface for verifying API functions: [Swagger](http://localhost:8080/api/swagger-ui/index.html).

## Community Contribution

I appreciate the assistance and contributions of users and community members. You can also contribute to the project, even if you are not a developer. This can include:

1. Reporting bugs and issues.
2. Participating in testing and reporting results.
3. Translating documentation or the interface.
4. Proposing new features and capabilities.

## Contact

If you have questions, suggestions, or need assistance, please feel free to reach out to us via email at `ihorkalaur@gmail.com`.
