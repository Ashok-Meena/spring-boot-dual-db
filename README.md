# Dual Database Spring Boot Application

This is a Spring Boot 3.5+ project developed using **Java 21**, designed to work with **dual databases** — MySQL and Azure SQL Server. It demonstrates modern backend architecture, clean code practices, and flexible database connectivity.

---

## 🚀 Tech Stack

- **Java 21**
- **Spring Boot 3.5+**
  - Spring Data JPA
  - Spring Web
  - Spring Security (optional)
- **MySQL / Azure SQL Server**
- **Maven**
- **RESTful APIs**

---

## 📁 Project Structure
dual-db-service/
│
├── src/
│ ├── main/
│ │ ├── java/ # Java source code
│ │ └── resources/ # application.yml/properties, static files
│ └── test/ # Unit/integration tests
│
├── pom.xml # Project dependencies and build config
└── README.md # Project documentation

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Ashok-Meena/spring-boot-dual-db.git
cd spring-boot-dual-db

2. Configure the Database
Update application.yml or application.properties for your DB setup.

✅ Example (MySQL):
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

✅ Example (Azure SQL Server):
spring.datasource.url=jdbc:sqlserver://<server>.database.windows.net:1433;database=<your_db>
spring.datasource.username=<your_user>
spring.datasource.password=<your_password>
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

📬 API Documentation
If Swagger is configured:
Open: http://localhost:8080/swagger-ui/index.html

✅ Features
Dual database connectivity

Clean RESTful API development
Easy environment configuration (local/cloud)

Flexible integration for MySQL and Azure SQL

🧪 Testing
mvn test
🤝 Contributing
Fork the repo and feel free to contribute by submitting pull requests or raising issues.

📝 License
This project is licensed under the MIT License.

👨‍💻 Author
Ashok Jeph
