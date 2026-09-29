# ExamCraft – Question Bank with Randomized Test Generation

This project follows the entities shown in the provided design:

- User
- Topic
- Question
- TestPaper
- TestAttempt
- TestPaperQuestion (join/mapping entity shown in the ER/database design)

It also follows feature-based packaging instead of one global controller/service/repository/entity package.

## 1. Software required

- JDK 17+
- IntelliJ IDEA
- MySQL Server + MySQL Workbench
- Maven (IntelliJ can use the Maven wrapper/project importer)
- Postman

## 2. Create the database

Open MySQL Workbench and run:

CREATE DATABASE examcraft;

## 3. Open in IntelliJ

Open the extracted folder as a Maven project.

Wait for Maven dependencies to finish downloading.

## 4. Configure MySQL

Open:

src/main/resources/application.properties

Change:

spring.datasource.password=CHANGE_ME

to your MySQL root password.

The default configuration is:

database: examcraft
username: root
port: 3306

## 5. Run

Run:

src/main/java/com/examcraft/ExamcraftApplication.java

Open:

http://localhost:8080/

JPA will create/update the tables automatically because:

spring.jpa.hibernate.ddl-auto=update

## 6. Demo login

Staff/Admin:
email: admin@examcraft.com
password: admin123
role: ADMIN

Student:
email: student@examcraft.com
password: student123
role: STUDENT

## 7. Recommended demo sequence

1. Login as Staff.
2. Open Manage Topics.
3. Add topics if needed.
4. Open Manage Questions.
5. Add questions with Easy/Medium/Hard difficulty.
6. Open Generate Test Paper.
7. Enter a distribution such as:
   Easy = 2
   Medium = 2
   Hard = 1
8. Generate.
9. Logout.
10. Login as Student.
11. Open the generated test.
12. Enter Student ID 2 for the demo student.
13. Select answers.
14. Submit.
15. View the result page.

## 8. Randomization rule

TestPaperService:
- separates questions by difficulty
- checks that enough questions are available
- shuffles each difficulty list
- selects the requested number
- shuffles the final selected questions
- stores each selected question once in test_paper_questions

The database also has a unique constraint on (paper_id, question_id), preventing duplicate question mapping within the same paper.

## 9. Postman APIs

Questions:
GET    http://localhost:8080/api/questions
GET    http://localhost:8080/api/questions/1
POST   http://localhost:8080/api/questions?topicId=1
PUT    http://localhost:8080/api/questions/1?topicId=1
DELETE http://localhost:8080/api/questions/1

Topics:
GET    http://localhost:8080/api/topics
POST   http://localhost:8080/api/topics
PUT    http://localhost:8080/api/topics/1
DELETE http://localhost:8080/api/topics/1

Test papers:
GET    http://localhost:8080/api/testpapers
GET    http://localhost:8080/api/testpapers/1
POST   http://localhost:8080/api/testpapers/generate?title=Java%20Test&easy=2&medium=2&hard=1
GET    http://localhost:8080/api/testpapers/1/questions

Attempts:
GET    http://localhost:8080/api/attempts
GET    http://localhost:8080/api/attempts/1
GET    http://localhost:8080/api/attempts/user/2

POST attempt:
http://localhost:8080/api/attempts?userId=2&testPaperId=1

Body:
{
  "1": "B",
  "2": "C",
  "3": "A"
}

## 10. Package structure

com.examcraft
├── user
│   ├── controller
│   ├── service
│   ├── repository
│   └── entity
├── student
│   └── controller
├── staff
│   ├── controller
│   └── service
├── topic
│   ├── controller
│   ├── service
│   ├── repository
│   └── entity
├── question
│   ├── controller
│   ├── service
│   ├── repository
│   └── entity
├── testpaper
│   ├── controller
│   ├── service
│   ├── repository
│   └── entity
├── attempt
│   ├── controller
│   ├── service
│   ├── repository
│   └── entity
└── ExamcraftApplication.java

Note: Student/Staff web controllers are kept in their feature packages, while shared domain entities such as Question, TestPaper and TestAttempt are kept in their own feature packages. This avoids duplicate copies of shared entities.

## 11. Important note for college demonstration

This is a student-project/demo implementation. The login uses a simple database password check so that the project is easy to understand and demonstrate. For a production system, passwords should be hashed and authentication/authorization should use Spring Security.

## 12. Main architecture

Browser / Postman
        |
        v
Controller
        |
        v
Service (business logic)
        |
        v
Repository (Spring Data JPA)
        |
        v
MySQL

Thymeleaf is used for the browser webpages.
