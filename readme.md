# Task Management Application

A simple task management application with a Spring Boot backend, Node.js/Express frontend, and PostgreSQL database. Supports creating, viewing, and validating tasks, with API documentation and error handling included.

## Features

- Create tasks with title, description, status, and due date.
- View all tasks.
- Validation for required fields (title, status).
- Global exception handling for proper error responses.
- API documentation using OpenAPI/Swagger annotations.
- Frontend interface for task creation and listing.

---

## Tech Stack

**Backend:** Java, Spring Boot, Spring Data JPA, Hibernate Validator  
**Frontend:** Node.js, Express, Nunjucks, Axios  
**Database:** PostgreSQL  
**Testing:** JUnit, RestAssured  
**API Documentation:** OpenAPI / Swagger  

---

## Setup

Repository contains both frontend and backend.

Open backend with Java
Open Frontend with Visual Studio Code
PostgresSQL will be required to setup with local user/password as well as creating database table task (will include create statement below)

### Backend

1. Clone the repository.
2. Navigate to the backend folder.
3. Configure PostgreSQL connection in or `application.yml`
4. Modify these settings to your PostgreSQL login details with your database with task fields
	DB_NAME=taskdb
	DB_USER_NAME=postgres
	DB_PASSWORD=taskpass
5. Make sure to define Poolname too. 
6. Make sure Load / sync the Gradle


### PostgreSQL Database

1. Ensure PostgreSQL is installed.
2. Create database and user or use your existing credentials.
3. Use the SQL below to create the `tasks` table:

```sql
CREATE TABLE public.tasks (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(2000),
    status VARCHAR(20) NOT NULL, -- values: TODO, PENDING, COMPLETED
    due_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### FrontEnd

1) Navigate to Terminal
2) Ensure the directory is correct. Use CD to ensure you are in hmcts-dev-test-frontend-master
3) Install dependencies.
4) Ensure backend is running first before running frontend
5) Use command ‘npm run start:dev’ to run the frontend project

Routes included
1) ‘list-tasks’  –  see a list of all tasks
2)  ‘tasks’ – lets you create a new task
 
	
### Running Tests

The backend includes functional tests to verify API endpoints.

1. Navigate to the backend project folder.
2. Run the functional tests either via accessing Gradle and clicking the run icon text to functional or via command

```bash
./gradlew functional
```