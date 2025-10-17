# OnlineExaminationApp-GUI
💻 Online Examination System (GUI Based)
📘 Project Description
The Online Examination System is a Java-based desktop application developed using Swing for the Graphical User Interface (GUI) and MySQL for backend database management.
It is designed to automate and simplify the traditional examination process by providing a secure, efficient, and user-friendly platform for both students and administrators.
The system allows students to log in, take multiple-choice exams, and instantly view their results. Administrators can add, edit, and manage questions, monitor exams, and analyze student performance.

All information — including user details, exam questions, and results — is stored securely in a MySQL database using JDBC connectivity.
To enhance integrity, the system can integrate AI-based proctoring features such as camera and microphone monitoring, ensuring fair and transparent examinations.
This project demonstrates strong skills in Java GUI design, event-driven programming, and database integration, showcasing how technology can make education systems more digital and accessible.


⚙️ Key Features
🔐 Secure Login System: Separate login access for administrators and students with password authentication.
🧑‍🏫 Admin Panel: Enables adding, editing, and managing exam questions and student details.
🎓 Student Dashboard: Allows students to take exams through an intuitive GUI and view instant results.
⏰ Exam Timer: Built-in timer for automatic submission when the time limit expires.
✅ Automatic Evaluation: Instantly calculates scores and displays performance results.
💾 Database Integration: All exam data is securely managed using MySQL and JDBC.
📊 Instant Result Display: Shows marks, correct answers, and performance summary right after submission.
🧠 AI-Based Proctoring (Optional): Uses camera/mic monitoring to detect and prevent cheating.
🖥️ User-Friendly Interface: Designed with Java Swing components for a clean and easy user experience.
🧩 Scalable Architecture: Can be expanded to include more exam types, analytics, and reporting tools.


🧰 Technologies Used

Programming Language: Java
GUI Framework: Java Swing
Database: MySQL
Connectivity: JDBC (Java Database Connectivity)
IDE Used: NetBeans / Eclipse / IntelliJ IDEA
Additional Features: AI-based proctoring (camera & microphone integration)


🎯 Learning Outcomes

Strengthened understanding of Java programming and object-oriented design principles.
Learned to create interactive GUI-based applications using Swing components.
Acquired hands-on experience with database connectivity (JDBC) and SQL operations.
Improved debugging, logic-building, and problem-solving abilities.
Explored integrating AI-based proctoring techniques for real-world exam monitoring.
Understood event-driven programming, interface design, and data management concepts.


▶️ How to Run the Project

Follow these steps to set up and run the Online Examination System (GUI Based) on your computer:

Install Required Software
Download and install JDK (Java Development Kit) — version 8 or higher.
Install an IDE such as NetBeans, Eclipse, or IntelliJ IDEA.
Install MySQL Server and MySQL Workbench for database management.
Set Up the Database
Open MySQL Workbench.
Create a new database (for example: online_exam).
Import or execute the provided database.sql file to create the necessary tables.
Make sure to note your database username and password.
Configure Database Connection
Open the project in your IDE.
In the Java source files, locate the database connection code (usually using DriverManager.getConnection() in JDBC).
Update the URL, username, and password to match your MySQL configuration, for example:
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_exam", "root", "your_password");
Compile and Run the Project
Open your IDE and build the project to resolve dependencies.
Run the main Java file (e.g., OnlineExamMain.java or LoginPage.java).
The login interface will appear — you can log in as:
Admin to add or manage questions.
Student to attempt exams.
Test the Application
Add a few sample questions via the Admin Panel.
Log in as a student to attempt the exam.
Check the automatic evaluation and result generation features.
(Optional) Enable AI-Based Proctoring
Integrate webcam and microphone modules (if implemented).
Grant camera/mic permissions when running the application.


✅ Output
Login window for Admin and Student
Question interface with timer
Result display after exam submission
Database showing stored user details, questions, and scores
