# StudyHub

## Student Study Planner and Productivity Application

StudyHub is an Android-based student study planner and productivity application developed to help students organise their academic activities, manage tasks, prepare for examinations and improve their study habits.

The application provides a central platform where students can manage subjects, assignments, examinations, focus sessions and personal progress.

---

## 1. Introduction

Students often have to manage multiple academic responsibilities such as assignments, examinations, study sessions and deadlines. Managing these activities manually can make it difficult to keep track of important academic tasks.

StudyHub was developed as a student-focused productivity application that brings these activities together in one system.

The application consists of an Android frontend and a Spring Boot REST API backend connected to a MySQL database.

---

## 2. Purpose of the Application

The purpose of StudyHub is to provide students with a simple digital platform for managing their academic activities.

The application allows users to:

- Register an account
- Log into the application
- Manage their profile
- Create and manage subjects
- Create and manage study tasks
- Track task completion
- Record upcoming examinations
- Use a focus/Pomodoro timer
- Track study progress
- Earn points
- Track study streaks
- Store academic information using a database

---

## 3. Target Users

StudyHub is primarily designed for:

- College students
- University students
- Learners managing multiple subjects
- Students preparing for examinations
- Students who need assistance organising assignments and study sessions

---

## 4. Main Features

### 4.1 User Registration

New users can create an account by providing:

- Full name
- Email address
- Password
- Password confirmation

Input validation is performed before registration.

### 4.2 User Login

Registered users can log into StudyHub using their email address and password.

The backend verifies the password using BCrypt password hashing.

### 4.3 Dashboard

The dashboard provides an overview of the student's academic progress.

The dashboard displays:

- Completed tasks
- Study time
- Points
- Study streak

The dashboard also provides navigation to the main features of the application.

### 4.4 Task Management

Students can:

- Add tasks
- View tasks
- Add task descriptions
- Set task priority
- Add due dates
- Mark tasks as completed
- Delete tasks

Tasks are stored in the MySQL database through the REST API.

### 4.5 Subject Management

Students can:

- Add subjects
- View subjects
- Add subject descriptions
- Assign subject information
- Delete subjects

### 4.6 Examination Management

Students can record upcoming examinations.

Exam information includes:

- Examination title
- Description
- Examination date
- Venue
- Subject

Students can also view and delete examination records.

### 4.7 Focus Timer

StudyHub includes a focus/Pomodoro timer designed to help students manage focused study sessions.

The timer provides:

- Focus sessions
- Break sessions
- Start
- Pause
- Reset

The default focus session is 25 minutes and the break session is 5 minutes.

### 4.8 Progress Tracking

StudyHub stores student progress information including:

- Points
- Study streak
- Completed tasks
- Study minutes

This information is displayed on the dashboard.

### 4.9 Profile and Settings

Users can manage their profile information.

The profile section allows users to:

- Update their name
- Update their email address
- Change their password
- Log out

Password updates are processed securely by the backend.

---

## 5. Design Considerations

The following design considerations were applied during development.

### Usability

The application uses simple navigation and clearly labelled buttons so that students can access important functions easily.

### Consistency

The application uses a consistent purple-themed interface across the different screens.

### Validation

User input is validated before information is submitted to the backend.

### Error Handling

The application provides error messages when requests fail or when invalid information is submitted.

### Security

Passwords are hashed using BCrypt before being stored in the database.

### Maintainability

The application separates the user interface, API communication and backend functionality into different components.

---

## 6. User Interface

StudyHub contains several screens that provide access to the application's main functionality.

### Login Screen

The login screen allows registered users to enter their email address and password.

**Screenshot**

_Add Login Screen screenshot here._

### Registration Screen

The registration screen allows new users to create an account.

**Screenshot**

_Add Registration Screen screenshot here._

### Dashboard

The dashboard provides an overview of the student's academic progress.

**Screenshot**

_Add Dashboard screenshot here._

### Tasks Screen

The tasks screen allows students to create, view, complete and delete tasks.

**Screenshot**

_Add Tasks Screen screenshot here._

### Subjects Screen

The subjects screen allows students to manage their academic subjects.

**Screenshot**

_Add Subjects Screen screenshot here._

### Exams Screen

The exams screen allows students to record and manage upcoming examinations.

**Screenshot**

_Add Exams Screen screenshot here._

### Focus Screen

The focus screen provides the Pomodoro-style study timer.

**Screenshot**

_Add Focus Timer screenshot here._

### Profile Screen

The profile screen allows users to update their account information and log out.

**Screenshot**

_Add Profile Screen screenshot here._

---

## 7. System Architecture

StudyHub uses a client-server architecture.

The main components are:

1. Android application
2. Spring Boot REST API
3. MySQL database

The Android application communicates with the Spring Boot backend through HTTP REST API requests.

The Spring Boot backend processes requests and communicates with the MySQL database using Spring Data JPA.

### Architecture Flow

```text
+-----------------------------+
|       Android Application   |
|          StudyHub           |
+-------------+---------------+
              |
              | REST API / HTTP
              |
              v
+-----------------------------+
|       Spring Boot API       |
|                             |
| Controllers                 |
| Services / Logic            |
| Repositories                |
+-------------+---------------+
              |
              | JPA / Hibernate
              |
              v
+-----------------------------+
|        MySQL Database       |
|          studyhub           |
+-----------------------------+
```

---

## 8. Technologies Used

### Android Application

- Kotlin
- Android Studio
- Android SDK
- XML layouts
- Retrofit
- Gson
- Kotlin Coroutines
- Material Components

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Spring Validation
- Spring Security Crypto
- Maven

### Database

- MySQL
- Hibernate
- JPA

### Development Tools

- Android Studio
- IntelliJ IDEA
- Visual Studio Code
- Git
- GitHub
- GitHub Actions

---

## 9. REST API

The StudyHub Android application communicates with the backend using REST API endpoints.

### User Endpoints

- POST `/api/users/register`
- POST `/api/users/login`
- PUT `/api/users/{id}`

### Subject Endpoints

- POST `/api/subjects/user/{userId}`
- GET `/api/subjects/user/{userId}`
- DELETE `/api/subjects/{id}`

### Task Endpoints

- POST `/api/tasks/user/{userId}`
- GET `/api/tasks/user/{userId}`
- GET `/api/tasks/user/{userId}/completed/{completed}`
- PUT `/api/tasks/{id}/complete`
- DELETE `/api/tasks/{id}`

### Examination Endpoints

- POST `/api/exams/user/{userId}`
- GET `/api/exams/user/{userId}`
- DELETE `/api/exams/{id}`

### Focus Session Endpoints

- POST `/api/focus-sessions/user/{userId}`
- GET `/api/focus-sessions/user/{userId}`
- DELETE `/api/focus-sessions/{id}`

### Progress Endpoints

- POST `/api/progress/user/{userId}`
- GET `/api/progress/user/{userId}`

---

## 10. Database

StudyHub uses a MySQL database named `studyhub`.

The backend uses Spring Data JPA and Hibernate to communicate with the database.

The main database entities include:

- Users
- Subjects
- Tasks
- Exams
- Focus Sessions
- Progress

### Main Relationships

A user can have:

- Multiple subjects
- Multiple tasks
- Multiple examinations
- Multiple focus sessions
- One progress record

Tasks and examinations can also be associated with subjects.

---

## 11. Security

Security was considered during the development of StudyHub.

### Password Hashing

Passwords are not stored as plain text.

The backend uses BCrypt to hash passwords before they are stored in the database.

When a user logs in, the supplied password is compared against the stored BCrypt hash.

### Password Validation

The application validates password input during registration.

The password must contain at least six characters.

### Authentication

Users must provide valid login credentials before accessing the main application dashboard.

---

## 12. Input Validation

StudyHub uses validation to prevent invalid information from being submitted.

Examples include:

- Required name
- Required email
- Valid email format
- Required password
- Minimum password length
- Password confirmation
- Required task information
- Valid examination dates

Backend validation is implemented using Spring Validation.

---

## 13. Error Handling

The application includes error handling for common situations.

Examples include:

- Invalid registration information
- Existing email address
- Incorrect login credentials
- Invalid input
- Server errors
- Failed API requests
- Network connection errors

The backend includes a global exception handler for validation and unexpected errors.

The Android application displays appropriate messages when API requests fail.

---

## 14. Logging

Logging is included in the backend to assist with monitoring and debugging.

Important operations such as:

- User registration
- Login attempts
- Profile updates
- Errors

can be logged without recording sensitive password information.

Logging assists developers in identifying problems during development and testing.

---

## 15. Code Comments

Comments are included in important parts of the source code to explain functionality and improve code readability.

Comments are used where additional explanation is useful, including:

- API communication
- Authentication
- Validation
- Database operations
- Timer functionality
- Important application logic

The project structure and source code are available in the GitHub repository.

---

## 16. Testing

Testing was performed during the development process to verify that the main application features operate correctly.

### Backend Automated Tests

Automated tests were created for user functionality.

Tests include:

- Successful user registration
- Duplicate email registration
- Successful login using the correct password
- Login using an incorrect password

The backend tests are executed using Spring Boot's testing framework.

### Functional Testing

| Test ID | Functionality | Expected Result |
|--------|---------------|-----------------|
| T01 | User registration | User account is created |
| T02 | Registration validation | Invalid data is rejected |
| T03 | User login | Valid credentials allow login |
| T04 | Incorrect password | Login is rejected |
| T05 | Add task | Task is stored |
| T06 | Complete task | Task status changes to completed |
| T07 | Delete task | Task is removed |
| T08 | Add subject | Subject is stored |
| T09 | Add examination | Examination is stored |
| T10 | Focus timer | Timer starts and can be controlled |
| T11 | Progress | Progress information is retrieved |
| T12 | Profile update | User information is updated |
| T13 | Logout | User returns to login screen |
| T14 | REST API | API requests are processed |
| T15 | Database | Application data is stored |
| T16 | Automated tests | Backend tests execute successfully |

Testing results and screenshots should be included in the final project evidence.

---

## 17. Git and GitHub

Git was used for version control throughout development.

The project source code is hosted on GitHub.

Git was used to:

- Initialise the repository
- Track project changes
- Commit changes
- Create the main branch
- Push source code to GitHub
- Maintain the project history

### GitHub Repository

https://github.com/KhaogeloMatlala/StudyHub

---

## 18. GitHub Actions

GitHub Actions was configured to automatically build and test the Android application.

The workflow performs the following steps:

1. Checks out the project
2. Sets up Java
3. Sets up Gradle
4. Runs automated unit tests
5. Builds the debug APK

The workflow file is located at:

`.github/workflows/android-build.yml`

The workflow helps verify that the project can be built successfully after changes are pushed to GitHub.

---

## 19. Project Structure

The project contains the Android application and supporting configuration files.

A simplified structure is shown below:

```text
StudyHub/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/studyhub/
│           │       ├── api/
│           │       ├── model/
│           │       ├── network/
│           │       ├── MainActivity.kt
│           │       ├── RegisterActivity.kt
│           │       ├── DashboardActivity.kt
│           │       ├── TasksActivity.kt
│           │       ├── SubjectsActivity.kt
│           │       ├── ExamActivity.kt
│           │       ├── FocusActivity.kt
│           │       └── ProfileActivity.kt
│           │
│           └── res/
│               ├── layout/
│               ├── drawable/
│               └── values/
│
├── .github/
│   └── workflows/
│       └── android-build.yml
│
├── README.md
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew
```

The Spring Boot backend is maintained as a separate project and contains:

```text
StudyHub/
│
├── src/main/java/com/example/StudyHub/
│   ├── controller/
│   ├── model/
│   ├── repository/
│   ├── StudyHubApplication.java
│   └── GlobalExceptionHandler.java
│
├── src/main/resources/
│   └── application.properties
│
└── pom.xml
```

---

## 20. Development Process

The StudyHub application was developed using an incremental development approach.

The main stages included:

### Stage 1 - Planning

The project requirements and functionality were identified.

### Stage 2 - System Design

The application's screens, database entities, API endpoints and system architecture were planned.

### Stage 3 - Backend Development

A Spring Boot REST API was created.

The backend was connected to MySQL using Spring Data JPA and Hibernate.

### Stage 4 - Android Development

The Android application was developed using Kotlin and Android Studio.

### Stage 5 - API Integration

Retrofit was used to connect the Android application to the Spring Boot REST API.

### Stage 6 - Security

BCrypt password hashing and input validation were implemented.

### Stage 7 - Testing

Backend automated tests and functional testing were performed.

### Stage 8 - Version Control

Git and GitHub were used to manage the project source code.

### Stage 9 - Continuous Integration

GitHub Actions was configured to automatically test and build the application.

### Stage 10 - Documentation

The project README, testing evidence, screenshots and presentation materials were prepared.

---

## 21. Future Improvements

Future versions of StudyHub could include:

- Push notifications for upcoming deadlines
- Cloud deployment of the backend
- Firebase authentication
- Calendar synchronisation
- More advanced progress analytics
- Study reminders
- Customisable timer durations
- Dark mode
- Improved accessibility
- Offline data support
- Additional automated tests
- More detailed academic reports

---

## 22. Video Demonstration

A video demonstration of the StudyHub application will demonstrate the main features of the system.

The video will include:

- Application launch
- User registration
- Login
- Dashboard
- Task management
- Subject management
- Examination management
- Focus timer
- Progress tracking
- Profile and settings
- Logout

### Video Link

_Add the final video link here._

Example:

https://www.youtube.com/watch?v=YOUR_VIDEO_ID

---

## 23. References

Android Developers. (n.d.). Android Developers Documentation. Available at: https://developer.android.com/ (Accessed: 25 September 2026).

GitHub. (n.d.). GitHub Documentation. Available at: https://docs.github.com/ (Accessed: 25 September 2026).

GitHub. (n.d.). GitHub Actions Documentation. Available at: https://docs.github.com/en/actions (Accessed: 25 September 2026).

Kotlin. (n.d.). Kotlin Documentation. Available at: https://kotlinlang.org/docs/home.html (Accessed: 25 September 2026).

MySQL. (n.d.). MySQL Documentation. Available at: https://dev.mysql.com/doc/ (Accessed: 25 September 2026).

Spring. (n.d.). Spring Boot Documentation. Available at: https://docs.spring.io/spring-boot/ (Accessed: 25 September 2026).

Spring. (n.d.). Spring Data JPA Documentation. Available at: https://spring.io/projects/spring-data-jpa (Accessed: 25 September 2026).

Retrofit. (n.d.). Retrofit Documentation. Available at: https://square.github.io/retrofit/ (Accessed: 25 September 2026).

Google. (n.d.). Gson Documentation. Available at: https://github.com/google/gson (Accessed: 25 September 2026).

---

## 24. Author

**Khaogelo Matlala**

StudyHub - Student Study Planner and Productivity Application