# Blogging_Application

* I have developed this REST API for a Blogging Application. This API performs all the fundamental CRUD operations of any Blog Application platform with user validation at every step.

## Tech Stack

* Java
* Spring Framework
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Spring Security using JWT

## Modules

* Login, Logout Module
* Admin Module
* User Module
* Blog Module
* Comment Module

## Features


## Installation & Run

* Before running the API server, you should update the database config inside the [application.properties](https://github.com/Lalitsingh28/Blogging_Application/tree/main/BloggingAppBackend/src/main/resources) file. 
* Update the port number, username and password as per your local database config.

```
    server.port=8888

    spring.datasource.url=jdbc:mysql://localhost:3306/bloggdb
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.username=root
    spring.datasource.password=root

```

## API Root Endpoint

`https://localhost:8888/`

