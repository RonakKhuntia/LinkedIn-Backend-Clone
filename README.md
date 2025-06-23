# LinkedIn Backend Clone

## Project Overview

This project is a backend clone of LinkedIn, built using a microservices architecture.  It consists of several independent services that communicate with each other.  The services handle user authentication, post management, connection management, and notifications.

## Features

* **User Service:** Manages user accounts, authentication (signup, login), and user profiles.
* **Posts Service:** Allows users to create, read, update, and delete posts. Includes liking functionality.  Uses Kafka for event publishing.
* **Connections Service:** Enables users to send and accept connection requests. Uses Kafka for event publishing.
* **Notification Service:** Sends notifications to users based on events from the Posts and Connections services. Consumes messages from Kafka.
* **API Gateway:** Acts as a single entry point for all requests, handling authentication and routing.
* **Discovery Server:**  Provides service discovery for the microservices.
