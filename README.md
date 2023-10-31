# Stock Market Information REST Application

# Overview
This is a RESTful web application built with Java 17, Spring Boot, PostgreSQL, and other technologies. 
The application collects and provides information about stock market shares. It allows users to perform CRUD operations
on various models, register new users with JWT token-based security, subscribe to daily email notifications, and more. 
The project aims to provide real-time stock market data from third-party APIs.


## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Installation](#installation)
  - [Configuration](#configuration)
- [Authentication](#authentication)
- [Database](#database)
- [Third-Party APIs](#third-party-apis)
- [License](#license)
- [Acknowledgments](#acknowledgments)

## Features

- **User Registration**: Users can register for the application with email and password, and receives after a successful login a JWT token for authentication.
- **CRUD Operations**: Perform Create, Read, Update, and Delete operations on various stock market data models.
- **Email Notifications**: Users recieve to daily email notifications about their favourite stock companies.
- **Real-Time Data**: The application fetches all stock data from a third-party API once a day, and when user wants more info on one stock, it gives real-time stock market data.
- **Security**: JWT-based security to protect user accounts and data.
- **Database**: Data is stored in a PostgreSQL database.

## Prerequisites

- Java 17
- Spring Boot
- PostgreSQL
- Third-party API access credentials
- Maven 3

## Getting Started

### Installation

1. Clone the repository:

   ```bash
   https://github.com/Abbos121/stock-market.git
   ```
2. Build the project
    ```bash
   mvn clean install
   ```
3. Run the applciation
    ```bash
   java -jar target/stock-market.jar 
    ```
### Configuration

- Configure your database connection in application.yml.
- Obtain third-party API access credentials and add them to your configuration.
- Add your email credentials to email config (email and password)

## Authentication

- Inside AuthenticationController, there is an endpoint where you can register and create an account
- After successfully registration, login to your account (also in AuthenticationController) which returns a jwt token
- Then you should add this jwt token to all your request header to test other endpoints

## Database

To create tables run ddl sql commands inside resources.ddl.create-table.sql, and also there are corresponding sequences in create-sequences.sql in the same directory

## Third-Party APIs
