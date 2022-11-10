
# HR application

Home assignment for Avinty Interview

# Prerequisites
## Database

Pull Postgres image for Docker  
Run postgres with Docker according to application.properties

    docker run --name avinty_assignment -e POSTGRES_PASSWORD=password -p 5432:5342 -d postgres


- name: postgres
- username: postgres
- password: password

## JDK

Have JDK version 11

## Port

The application is running on port :8085

# Description

## Functionalities

- authentication:
    -  localhost:8085/API/V1/auth/signin
    - POST
    - body:  `{"email": "", "password" : ""}`
- get all employees:
    - localhost:8085/API/V1/employees
    - GET
    - requires Bearer Token
- get departments by name
    - localhost:8085/API/V1/department?name=?
    - GET
    - requires Bearer Token
- get all departments with employees
    - localhost:8085/API/V1/dep-emp
    - GET
    - requires Bearer Token
- update manager for department
    - localhost:8085/API/V1/department/set-manager/{managerId}
    - PATCH
    - body: `{"id": , "name": ""}`
    - in the body provide information from department
    - requires Bearer Token
    - requires ROLE_ADMIN
- delete department
    - localhost:8085/API/V1/department/{departmentId}
    - DELETE
    - requires Bearer Token
    - requires ROLE_ADMIN

## Usage
There are predefined employees and departments in the database for testing purposes.  
Users to use:

- email: john.doe@avinty-test.com password: johndoe  (ADMIN)
- email: mark.smith@avinty-test.com password: marksmith  (MANAGER for Developers)
- email: helen.black@avinty-test.com, password: helen black  (JAVA_DEVELOPER for Developers)
- email: justin.stone@avinty-test.com, password: justinstone (ANGULAR_DEVELOPER for Developers)
- email: james.brown@avinty-test.com, password: jamesbrown (MANAGER for Marketing)
- email: grace.hawk@avinty-test.com, password: gracehawk  (MARKETING_CONSULTANT for Marketing)
- email: lily.james@avinty-test.com, password: lilyjames  (MARKETING_CONSULTANT for Marketing)

Departments to use:
- Marketing
- Developers

# SQL task

find the sql solution under src/resources/task2/sql-task.sql
