# Online Billing System

A comprehensive Java-based online billing system built with Spring Boot, MySQL, and Thymeleaf. This application provides complete invoice management, customer management, payment tracking, and analytics capabilities.

## 🌟 Features

### User Management
- User Authentication & Authorization
- Role-based Access Control (Admin, Manager, User)
- Secure login and registration
- Session management

### Customer Management
- Create, update, and manage customers
- Search and filter customers
- Track customer details (email, phone, GSTIN, address)
- Customer status management (active/inactive)

### Invoice Management
- Create and manage invoices
- Multiple invoice statuses (Draft, Pending, Sent, Paid, Partially Paid, Cancelled, Overdue)
- Invoice items with tax calculation
- Automatic invoice number generation
- Track invoice history and status

### Payment Management
- Record and track payments
- Multiple payment methods (Cash, Cheque, Credit Card, Debit Card, Bank Transfer, UPI)
- Payment reconciliation
- Transaction tracking

### Reports & Analytics
- Revenue reports with date range filtering
- Payment collection reports
- Invoice status breakdown
- Customer sales analytics
- Dashboard with key metrics

## 🛠️ Technology Stack

- **Backend**: Java 17+, Spring Boot 3.x
- **Security**: Spring Security with BCrypt password encoding
- **Database**: MySQL 8.0+
- **ORM**: Spring Data JPA (Hibernate)
- **Frontend**: Thymeleaf, Bootstrap 5, HTML5/CSS3
- **Build Tool**: Maven
- **Additional Libraries**:
  - Lombok (reduce boilerplate)
  - Jasper Reports (PDF generation)
  - Spring Mail (email notifications)
  - Validation Framework (Bean Validation)

## 📋 Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher
- Git

## 🚀 Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/chidrewarkrishna86/online-billing-system.git
cd online-billing-system
```

### 2. Create Database
```sql
CREATE DATABASE billing_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. Configure Database Connection
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/billing_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4. Build the Project
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will be available at: `http://localhost:8080/billing`

## 🔐 Default Credentials

The system will create database tables automatically. Create your first admin user through the registration page.

## 📚 Project Structure

```
online-billing-system/
├── pom.xml
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/billing/
│   │   │   ├── controller/          # Web controllers
│   │   │   ├── service/             # Business logic
│   │   │   ├── entity/              # JPA entities
│   │   │   ├── repository/          # Data access layer
│   │   │   ├── config/              # Spring configuration
│   │   │   ├── security/            # Security configurations
│   │   │   └── BillingSystemApplication.java
│   │   └── resources/
│   │       ├── templates/           # Thymeleaf templates
│   │       ├── static/              # Static assets
│   │       ��── application.properties
│   └── test/                        # Unit and integration tests
```

## 🌐 API Endpoints

### Authentication
- `GET /billing/login` - Login page
- `POST /billing/login` - Process login
- `GET /billing/register` - Registration page
- `POST /billing/register` - Create new user
- `POST /billing/logout` - Logout

### Dashboard
- `GET /billing/dashboard` - Dashboard with metrics

### Customers
- `GET /billing/customers` - List all customers
- `GET /billing/customers/create` - Create customer form
- `POST /billing/customers/create` - Save new customer
- `GET /billing/customers/{id}` - View customer details
- `GET /billing/customers/{id}/edit` - Edit customer form
- `POST /billing/customers/{id}/edit` - Update customer
- `POST /billing/customers/{id}/delete` - Delete customer

### Invoices
- `GET /billing/invoices` - List all invoices
- `GET /billing/invoices/create` - Create invoice form
- `GET /billing/invoices/{id}/view` - View invoice
- `GET /billing/invoices/{id}/edit` - Edit invoice form
- `POST /billing/invoices/{id}/delete` - Delete invoice

### Reports
- `GET /billing/reports` - Reports dashboard
- `GET /billing/reports/revenue` - Revenue report
- `GET /billing/reports/payments` - Payment collection report
- `GET /billing/reports/invoice-status` - Invoice status report

## 🔒 Security Features

- **Password Encryption**: BCrypt hashing for passwords
- **Role-Based Access Control**: Admin, Manager, User roles
- **Session Management**: Configurable session timeout
- **CSRF Protection**: Enabled by default
- **Form Validation**: Server-side validation with constraints
- **SQL Injection Prevention**: Using parameterized queries with JPA

## 📊 Database Schema

### Key Tables
- `users` - User accounts
- `customers` - Customer information
- `invoices` - Invoice records
- `invoice_items` - Line items in invoices
- `payments` - Payment records

Tables are automatically created using JPA `hibernate.ddl-auto=update`.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📝 License

This project is open source and available under the MIT License.

## 📞 Support

For support, please create an issue in the repository.

## 🎯 Future Enhancements

- [ ] Email notification system
- [ ] PDF invoice generation
- [ ] Multi-currency support
- [ ] Inventory management
- [ ] Automated billing schedules
- [ ] REST API endpoints
- [ ] Advanced analytics with charts
- [ ] Mobile application
- [ ] Two-factor authentication
- [ ] Audit logging

---

**Created by**: Chidrewa Rkrishna
**Last Updated**: 2026-03-11 10:52:21