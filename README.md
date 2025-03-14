# Mega City Cab 🚖

Mega City Cab is a Java web application for a cab booking system. It allows users to book different types of vehicles while following SOLID principles, a 3-tier architecture, and design patterns such as the Factory pattern.

## Features ✨
- User authentication & role-based access
- Vehicle booking with multiple vehicle types
- Real-time booking status updates
- Admin dashboard for managing bookings & vehicles
- Follows SOLID principles & design patterns

## Technologies Used 🛠️
- **Frontend**: HTML, CSS, JavaScript (JSP for dynamic content)
- **Backend**: Java (JSP, Servlets)
- **Database**: MySQL
- **Architecture**: 3-Tier (Presentation, Business Logic, Data Access)
- **Design Patterns**: Factory Pattern

## Installation & Setup 🚀

### Prerequisites
- Java JDK 23
- Apache Tomcat or TomEE
- MySQL Server
- IDE (Eclipse/IntelliJ IDEA/NetBeans)

### Steps to Run
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/mega-city-cab.git
   ```
2. Import the project into your IDE.
3. Configure MySQL database with the provided `schema.sql`.
4. Update `database.properties` with your MySQL credentials.
5. Deploy the project on Apache Tomcat or TomEE.
6. Access the application via `http://localhost:8080/MegaCityCab`.

## Folder Structure 📂
```
MegaCityCab/
│── Web Pages/           # Frontend JSP files
│── Source Packages/
│   ├── controller/       # Servlets for request handling
│   ├── persistance.dao/  # Data Access Objects
│   ├── persistance.impl/ # DAO implementations
│   ├── service.factory/  # Factory pattern implementation
│   ├── service.model/    # Business logic & data models
│   ├── service.services/ # Service layer
│   ├── util/             # Utility classes
│── Test Packages/
│── Libraries/
│   ├── mysql-connector-j-9.2.0.jar
│   ├── JDK 23
│   ├── Apache Tomcat or TomEE
│── Test Libraries/
│   ├── JUnit 4.13.2
│   ├── Hamcrest 1.3
│── Configuration Files/
│── schema.sql            # Database schema
```

## Contributing 🤝
Feel free to submit pull requests and contribute to the project.

## Contact 📧
For any inquiries, contact **Thanuja Fernando** at thanujadfernando@gmail.com.
