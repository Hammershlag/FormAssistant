# Form Assistant

## Spis treści
1. [Cloning the Repository](#1-cloning-the-repository)
2. [Project Structure](#2-project-structure)
3. [Requirements](#3-requirements)
4. [Environment Variables](#4-environment-variables)
5. [Ports](#5-ports)
6. [How to Build and Run with Docker Compose](#6-how-to-build-and-run-with-docker-compose)
7. [Optional: Clean Rebuild](#7-optional-clean-rebuild)
8. [Accessing the App](#8-accessing-the-app)
9. [How to use](#9-how-to-use)
10. [Author](#author)

Form Assistant is a full-stack application that helps users fill out support forms with the help of a Large Language Model (LLM).  
It consists of:

- A **backend** built with Java and Spring Boot that manages form logic, validation, and interaction with Gemini LLM.
- A **frontend** built with JavaScript and React that provides a clean, interactive chat interface for user interaction.

## 1. Cloning the Repository

To get started with the project code, follow these steps:

1. Open your terminal in the desired directory.
2. Clone the repository using the following command:

```bash
  git clone https://github.com/Hammershlag/FormAssistant
```

3. Navigate to the cloned directory:

```bash
  cd FormAssistant
```

You now have the project code on your local machine.

## 2. Project Structure

```
/
├── docker-compose.yml
├── .env                 # Environment variables (not in repository)
├── form-assistant-backend/
└── form-assistant-frontend/
```

---

## 3. Requirements

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

---

## 4. Environment Variables

#### If the `.env` was provided:
- Put the `.env` file in the root directory, that is in the **same directory** as `docker-compose.yml`.
- Ensure that the variable `GEMINI_API_KEY` is set in the `.env` file. This key is required for the backend to communicate with the Gemini LLM API.
- The `GEMINI_API_URL` variable should be set to the URL of the Gemini LLM API.

#### If the `.env` was not provided:
- Create a `.env` file in the **same directory** as `docker-compose.yml` with the following contents:

```
# Example .env
GEMINI_API_KEY=your_api_key_here
GEMINI_API_URL=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=
```

## 5. Ports

Make sure the following ports are **free before running the application**:

- `8080` — for the Spring Boot backend
- `3000` — for the React frontend

---

## 6. How to Build and Run with Docker Compose

In your terminal, navigate to the root of the project (where `docker-compose.yml` is located) and run:

```
docker-compose up --build
```

This command will build the images (if needed) and start the containers.

If you prefer to run the containers in the background (detached mode), you can add the $-d flag:

```
docker-compose up --build -d
```

This will:

- Build the backend and frontend Docker images using `Dockerfile` in each respective directory
- Start both services
- Map `localhost:8080` to the backend and `localhost:3000` to the frontend

To stop the containers:

```
docker-compose stop
```

Or to remove the containers and networks:

```
docker-compose down
```

---

## 7. Optional: Clean Rebuild

If you ever need to rebuild everything from scratch:

```
docker-compose down --volumes --remove-orphans
docker-compose build --no-cache
docker-compose up -d
```
The same as before the `-d` flag is optional

---

## 8. Accessing the App

- Open your browser and go to: [http://localhost:3000](http://localhost:3000)
- The backend will be available at: [http://localhost:8080](http://localhost:8080)

---

## 9. How to use

1. Open the application in your browser.
2. You will see a chat interface and a form on the right side with empty fields. 
You should see a welcome message and under the container you will see an input field and a button to send a message.
3. Enter your question regarding the form or specify which form field you need assistance with in the input box, then click the send button or press Enter to receive help.
4. The assistant will answer your question and fill out the form fields based on your input.
5. You can continue the conversation by asking follow-up questions or requesting further assistance with the form.

##  Author

**Tomasz Zbroszczyk**
