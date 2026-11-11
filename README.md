StudyHub

Student Study Planner and Productivity Application

StudyHub is an Android-based student study planner and productivity application developed to help students organise academic activities, manage tasks, prepare for examinations and improve their study habits.

The application combines an Android frontend, a Spring Boot REST API, a MySQL database and Firebase services.

---

1. Introduction

Students often manage several academic responsibilities, including assignments, examinations, subjects and study sessions. StudyHub provides a central platform where students can organise these activities and monitor their academic progress.

The application was developed using Kotlin and Android Studio, with backend services developed using Spring Boot.

2. Purpose of the Application

The purpose of StudyHub is to provide students with a digital platform for managing academic responsibilities and improving productivity.

The application supports:

- User registration and login.
- Subject management.
- Study task management.
- Examination management.
- Calendar access.
- Focus and Pomodoro study sessions.
- Study progress tracking.
- Points and study streak tracking.
- Cloud-based authentication and data services through Firebase.

3. Target Users

StudyHub is designed for:

- College students.
- University students.
- Learners managing multiple subjects.
- Students preparing for examinations.
- Students who need assistance organising academic responsibilities.

4. Main Features

4.1 User Registration and Login

Users can create an account and access StudyHub using their login credentials.

The application includes input validation and authentication functionality. The Spring Boot backend uses BCrypt password hashing for passwords handled by its user authentication system.

Firebase Authentication is also integrated into the application.

4.2 Dashboard

The dashboard provides an overview of the student's academic activities and progress.

It provides access to the main application features and displays available progress information, including completed tasks, study time, points and study streaks.

4.3 Task Management

Students can:

- Add tasks.
- View their tasks.
- Enter task descriptions.
- Set task priorities.
- Add due dates.
- Mark tasks as completed.
- Delete tasks.

Task information is managed through the application's backend.

4.4 Subject Management

Students can:

- Add subjects.
- View subjects.
- Enter subject descriptions.
- Delete subjects.

4.5 Examination Management

Students can record and manage upcoming examinations.

Exam information includes:

- Examination title.
- Description.
- Examination date.
- Venue.
- Subject information, where applicable.

The examination screen includes a calendar-based date picker. Selecting a date automatically formats it as "YYYY-MM-DD" before submitting it to the backend.

Students can also view and delete examination records.

4.6 Calendar

The calendar feature provides access to the application's calendar functionality, helping students keep track of academic dates.

4.7 Focus Timer

StudyHub includes a focus timer designed to encourage productive study sessions.

The timer supports:

- Focus sessions.
- Break sessions.
- Start.
- Pause.
- Reset.

The default focus session is 25 minutes, and the default break session is 5 minutes.

4.8 Progress Tracking

StudyHub provides progress information to help students monitor their study activities.

Available progress information includes:

- Points.
- Study streaks.
- Completed tasks.
- Study minutes.

4.9 Firebase Integration

Firebase services are integrated into StudyHub.

The application uses:

- Firebase Authentication.
- Cloud Firestore for cloud-based data storage.

Firestore provides a NoSQL database service for storing and retrieving application data.

---

5. Design Considerations

The following principles were considered during development.

Usability: Simple navigation and clearly labelled controls help students access application features.

Consistency: A consistent purple-themed interface is used across the main screens.

Validation: User input is validated before information is submitted.

Error handling: Appropriate messages are displayed when requests fail or invalid information is entered.

Security: BCrypt password hashing is used by the Spring Boot authentication implementation.

Maintainability: The application separates user interface components, data models and API communication.

---

6. Screenshots

Screenshots should be stored in the repository's "screenshots" folder using the filenames below.

Login

"StudyHub Login" (screenshots/login.png)

Registration

"StudyHub Registration" (screenshots/registration.png)

Dashboard

"StudyHub Dashboard" (screenshots/dashboard.png.jpg)

Tasks

"StudyHub Tasks" (screenshots/tasks.png.jpg)

Subjects

"StudyHub Subjects" (screenshots/subjects.png.jpg)

Exams

"StudyHub Exams" (screenshots/exams.png.jpg)

Calendar

"StudyHub Calendar" (screenshots/calendar.png.jpg)

Focus Timer

"StudyHub Focus Timer" (screenshots/focus.png.jpg)

Progress

"StudyHub Progress" (screenshots/progress.png.jpg)


---

7. System Architecture

StudyHub uses a client-server architecture with additional Firebase services.

The main components are:

1. Android application.
2. Spring Boot REST API.
3. MySQL database.
4. Firebase Authentication.
5. Cloud Firestore.

The Android application communicates with the Spring Boot backend through HTTP REST API requests. The backend processes requests and communicates with MySQL using Spring Data JPA and Hibernate. Firebase provides authentication and cloud database services.

Architecture Flow

+-----------------------------+
|      Android Application    |
|           StudyHub          |
|            Kotlin           |
+-------------+---------------+
|
| REST API / HTTP
|
v
+-----------------------------+
|       Spring Boot API       |
|                             |
| Controllers                 |
| Application Logic           |
| Repositories                |
+-------------+---------------+
|
| JPA / Hibernate
|
v
+-----------------------------+
|        MySQL Database       |
+-----------------------------+

              |
              v
+-----------------------------+
|       Firebase Services     |
|                             |
| Firebase Authentication    |
| Cloud Firestore             |
+-----------------------------+

---

8. Technologies Used

Android Application

- Kotlin.
- Android Studio.
- Android SDK.
- XML layouts.
- Retrofit.
- Gson.
- Kotlin Coroutines.
- Material Components.

Backend

- Java.
- Spring Boot.
- Spring Web.
- Spring Data JPA.
- Spring Validation.
- Spring Security Crypto.
- Maven.

Databases and Cloud Services

- MySQL.
- Hibernate.
- JPA.
- Firebase Authentication.
- Cloud Firestore.

Development and Version Control

- Android Studio.
- IntelliJ IDEA.
- Visual Studio Code.
- Git.
- GitHub.
- GitHub Actions.

---

9. REST API

The Android application communicates with the Spring Boot backend through REST API endpoints.

User Endpoints

- POST "/api/users/register"
- POST "/api/users/login"
- PUT "/api/users/{id}"

Subject Endpoints

- POST "/api/subjects/user/{userId}"
- GET "/api/subjects/user/{userId}"
- DELETE "/api/subjects/{id}"

Task Endpoints

- POST "/api/tasks/user/{userId}"
- GET "/api/tasks/user/{userId}"
- GET "/api/tasks/user/{userId}/completed/{completed}"
- PUT "/api/tasks/{id}/complete"
- DELETE "/api/tasks/{id}"

Examination Endpoints

- POST "/api/exams/user/{userId}"
- GET "/api/exams/user/{userId}"
- DELETE "/api/exams/{id}"

Focus Session Endpoints

- POST "/api/focus-sessions/user/{userId}"
- GET "/api/focus-sessions/user/{userId}"
- DELETE "/api/focus-sessions/{id}"

Progress Endpoints

- POST "/api/progress/user/{userId}"
- GET "/api/progress/user/{userId}"

---

10. Database

StudyHub uses MySQL and Cloud Firestore for its data services.

The Spring Boot backend uses Spring Data JPA and Hibernate to communicate with MySQL.

The main relational database entities include:

- Users.
- Subjects.
- Tasks.
- Exams.
- Focus Sessions.
- Progress.

Cloud Firestore provides NoSQL cloud data storage.

Main Relationships

A user can have multiple subjects, tasks, examinations and focus sessions, as well as a progress record.

Tasks and examinations can also be associated with subjects where applicable.

---

11. Security

Security was considered during the development of StudyHub.

Password Hashing

The Spring Boot authentication implementation uses BCrypt to hash passwords before storing them.

Passwords should not be stored or logged in plain text.

Input Validation

The application validates user input during registration and other data-entry operations.

Authentication

StudyHub integrates Firebase Authentication alongside its backend authentication functionality.

Sensitive Firebase configuration files and credentials must not be committed to a public GitHub repository.

---

12. Input Validation

Input validation helps prevent incomplete or invalid information from being submitted.

Examples include:

- Required name.
- Required email.
- Valid email format.
- Required password.
- Minimum password length.
- Required task information.
- Required examination title.
- Valid examination date format.

Backend validation is implemented using Spring Validation.

---

13. Error Handling

StudyHub includes error handling for common situations, including:

- Invalid registration information.
- Existing email addresses.
- Incorrect login credentials.
- Invalid input.
- Server errors.
- Failed API requests.
- Network connection problems.

The Android application displays appropriate messages when API requests fail.

---

14. Logging

Logging is used in the backend to assist with monitoring, debugging and understanding application execution.

Important operations include user registration, login processing and error handling.

Sensitive information, particularly passwords and authentication credentials, should never be included in application logs.

---

15. Code Comments and Documentation

Comments are included in important sections of the source code to explain application functionality and improve readability.

Comments are particularly useful for explaining:

- Authentication.
- API communication.
- Validation.
- Database operations.
- Timer functionality.
- Important application logic.

External code examples and documentation used during development should be acknowledged through appropriate references.

---

16. Testing

Functional testing was performed during development to verify the main application features.

Functional Testing

Test ID| Functionality| Expected Result
T01| User registration| User account is created
T02| Registration validation| Invalid data is rejected
T03| User login| Valid credentials allow login
T04| Incorrect password| Login is rejected
T05| Add task| Task is saved
T06| Complete task| Task status changes
T07| Delete task| Task is removed
T08| Add subject| Subject is saved
T09| Add examination| Examination is saved
T10| Select examination date| Date is formatted correctly
T11| Focus timer| Timer starts and can be controlled
T12| Progress| Progress information is displayed
T13| Calendar| Calendar screen opens
T14| REST API| API requests are processed
T15| Database| Application data is stored
T16| Application build| Android application builds successfully

Testing evidence and screenshots should be maintained in the repository or submitted with the assessment.

---

17. Git and GitHub

Git was used for version control throughout development.

The project source code is hosted on GitHub.

Git was used to:

- Initialise and maintain the repository.
- Track source code changes.
- Commit updates.
- Push source code to GitHub.
- Maintain project history.

GitHub Repository

"StudyHub GitHub Repository" (https://github.com/KhaogeloMatlala/StudyHub)

The complete Kotlin source code is available through the repository.

---

18. GitHub Actions

GitHub Actions is configured through the workflow file:

".github/workflows/android-build.yml"

The workflow is intended to automate project verification by performing steps such as:

1. Checking out the repository.
2. Setting up Java.
3. Setting up Gradle.
4. Running configured automated tests.
5. Building the Android application.

Successful workflow runs can be viewed through the repository's Actions tab.

---

19. Project Structure

The Android project contains the application source code, resources and configuration files.

A simplified structure is shown below:

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
│           │       ├── DashboardActivity.kt
│           │       ├── ExamActivity.kt
│           │       └── Other activity files
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
├── screenshots/
├── README.md
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew

The Spring Boot backend contains its own controllers, models, repositories, application configuration and Maven build files.

---

20. Development Process

StudyHub was developed incrementally through the following stages:

1. Planning: Identifying application requirements and functionality.
2. System Design: Planning the screens, database entities, API endpoints and architecture.
3. Backend Development: Developing the Spring Boot REST API.
4. Android Development: Building the Android application using Kotlin.
5. API Integration: Connecting the Android application to the backend using Retrofit.
6. Security: Implementing password hashing and input validation.
7. Firebase Integration: Integrating Firebase Authentication and Cloud Firestore.
8. Testing: Testing the main application features.
9. Version Control: Managing source code using Git and GitHub.
10. Continuous Integration: Configuring GitHub Actions.
11. Final Preparation: Updating documentation and preparing release materials.

---

21. Release Information

The final version of StudyHub builds on the original prototype.

Updates include:

- Improved and more consistent purple-themed user interface.
- Updated dashboard and application screens.
- Improved examination date entry through a calendar date picker.
- Firebase Authentication integration.
- Cloud Firestore integration.
- Continued integration with the Spring Boot REST API.
- Functional testing and application improvements.

A separate "RELEASE_NOTES.md" file should provide the detailed version history and explain the innovative features added since the prototype.

---

22. Video Presentation

The video presentation demonstrates the StudyHub application and explains its main functionality.

Video Link:
"Watch the StudyHub Video Presentation" 
https://youtube.com/shorts/ipMrTkEMIXc?si=MvGMFHYcBW2IomA4

The final assessment video must include a voice-over explaining the application and demonstrating an understanding of its features and implementation.

The presentation should cover:

- User registration and login.
- Dashboard and progress information.
- Task management.
- Subject management.
- Examination management and date selection.
- Calendar functionality.
- Focus timer.
- Firebase integration.
- REST API communication.
- Database integration.
- Input validation and error handling.

---

23. References

Android Developers. (n.d.). Android Developers Documentation. Available at: https://developer.android.com/ (Accessed: 7 November 2026).

Firebase. (n.d.). Firebase Documentation. Available at: https://firebase.google.com/docs (Accessed: 7 November 2026).

GitHub. (n.d.). GitHub Documentation. Available at: https://docs.github.com/ (Accessed: 7 November 2026).

GitHub. (n.d.). GitHub Actions Documentation. Available at: https://docs.github.com/en/actions (Accessed: 7 November 2026).

Google. (n.d.). Cloud Firestore Documentation. Available at: https://firebase.google.com/docs/firestore (Accessed: 7 November 2026).

Kotlin. (n.d.). Kotlin Documentation. Available at: https://kotlinlang.org/docs/home.html (Accessed: 7 November 2026).

MySQL. (n.d.). MySQL Documentation. Available at: https://dev.mysql.com/doc/ (Accessed: 7 November 2026).

Retrofit. (n.d.). Retrofit Documentation. Available at: https://square.github.io/retrofit/ (Accessed: 7 November 2026).

Spring. (n.d.). Spring Boot Documentation. Available at: https://docs.spring.io/spring-boot/ (Accessed: 7 November 2026).

Spring. (n.d.). Spring Data JPA Documentation. Available at: https://spring.io/projects/spring-data-jpa (Accessed: 7 November 2026).

---

24. Author

Khaogelo Matlala

StudyHub – Student Study Planner and Productivity Application.
