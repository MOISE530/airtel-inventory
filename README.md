# Airtel Professional End-User Equipment Inventory Management System

A comprehensive, production-ready inventory management system for Airtel to track end-user equipment including laptops, desktops, and mobile phones. Built with Spring Boot MVC architecture and MySQL database with advanced analytics and professional features.

## Technology Stack

- **Spring Boot 3.2.0** with MVC architecture
- **Java 17** as the programming language
- **MySQL 8.0** database via XAMPP
- **Spring Data JPA** with Hibernate ORM
- **Thymeleaf** as the template engine
- **Maven** for build automation
- **Apache Tomcat** embedded server on port 8081
- **Chart.js** for interactive analytics and data visualization

## Professional Features

### Core Functionality
- **User Authentication**: Secure login system with session management
- **Dashboard**: Real-time statistics and overview of inventory
- **Device Management**: Complete CRUD operations for device registration and tracking
- **Employee Management**: Store employee information for device assignments
- **Assignment Tracking**: Track device assignments to employees with return management
- **Responsive Design**: Modern, mobile-friendly interface
- **Search & Filter**: Advanced device search and filtering capabilities

### Advanced Analytics
- **Analytics Dashboard**: Interactive charts and visualizations
- **Device Type Distribution**: Doughnut chart showing device categories
- **Department Distribution**: Bar chart for employee departments
- **Device Condition Overview**: Pie chart for device status
- **Monthly Assignment Trends**: Line chart for assignment patterns
- **Real-time Statistics**: Live data updates and metrics
- **Recent Activity Feed**: Latest assignment history with status indicators

### Professional UI/UX
- **Modern Design**: Professional Airtel-branded interface
- **Interactive Charts**: Dynamic data visualization with Chart.js
- **Responsive Layout**: Works seamlessly on all devices
- **Professional Styling**: Consistent theme throughout the application
- **Developer Attribution**: System credits and information on login page

## Project Structure

```
src/main/java/com/airtel/inventory/
├── AirtelInventoryApplication.java     # Main application class
├── controller/                         # Web controllers
│   ├── LoginController.java
│   ├── DashboardController.java
│   ├── DeviceController.java
│   ├── EmployeeController.java
│   ├── AssignmentController.java
│   ├── AnalyticsController.java
│   └── ExportController.java
├── model/                              # JPA entities
│   ├── User.java
│   ├── Device.java
│   ├── Employee.java
│   ├── Assignment.java
│   └── AuditLog.java
├── repository/                         # Spring Data JPA repositories
│   ├── UserRepository.java
│   ├── DeviceRepository.java
│   ├── EmployeeRepository.java
│   ├── AssignmentRepository.java
│   └── AuditLogRepository.java
└── service/                           # Business logic
    ├── DeviceService.java
    ├── EmployeeService.java
    ├── AssignmentService.java
    ├── AnalyticsService.java
    ├── ExportService.java
    └── AuditLogService.java

src/main/resources/
├── application.properties             # Application configuration
├── templates/                         # Thymeleaf templates
│   ├── login.html
│   ├── dashboard.html
│   ├── analytics/
│   │   └── dashboard.html
│   ├── devices/
│   │   ├── list.html
│   │   └── form.html
│   ├── employees/
│   │   ├── list.html
│   │   ├── form.html
│   │   └── view.html
│   └── assignments/
│       ├── list.html
│       ├── assign.html
│       ├── device-assignments.html
│       └── employee-assignments.html
└── static/css/                       # CSS styling
    └── style.css
```

## Database Setup

### Prerequisites
- XAMPP with MySQL 8.0 installed and running
- phpMyAdmin for database management

### Setup Instructions

1. **Start MySQL Server**: Launch XAMPP and start the MySQL service

2. **Create Database**: Open phpMyAdmin and run the provided SQL script:
   ```sql
   -- Import the database_setup.sql file
   -- This will create the airtel_inventory database with all required tables and sample data
   ```

3. **Verify Setup**: Ensure the following tables are created:
   - `users` (with admin user: username='24RP01839', password='24RP03971')
   - `devices` (with sample devices)
   - `employees` (with sample employees)
   - `assignments` (assignment tracking)
   - `audit_logs` (activity tracking)

## Running the Application

### Using Command Line

```bash
# Navigate to project directory
cd windsurf-project

# Build and run
mvn clean spring-boot:run
```

### Access Application
- **URL**: `http://localhost:8081/airtel-inventory/login`
- **Default Credentials**:
  - Username: `24RP01839`
  - Password: `24RP03971`

## Application Usage

### Login
- Access the application at `/airtel-inventory/login`
- Professional login interface with developer attribution
- System description and credits displayed

### Dashboard
- View real-time statistics and metrics
- Quick access to all major functions
- Professional menu cards for navigation

### Device Management
- **View Devices**: Browse all registered devices with advanced search and filtering
- **Add Device**: Register new equipment with comprehensive details
- **Edit Device**: Update device information and status
- **Delete Device**: Remove devices from inventory (with safety checks)

### Employee Management
- **View Employees**: Browse all employees with search capabilities
- **Add Employee**: Register new employees with complete information
- **Edit Employee**: Update employee details and assignments
- **View Employee Details**: Comprehensive employee profile with assignment history
- **Delete Employee**: Safe deletion with assignment checks

### Assignment Management
- **View Assignments**: Browse all device assignments
- **Assign Device**: Assign devices to employees with tracking
- **Return Device**: Process device returns with date tracking
- **Device Assignment History**: View complete assignment timeline
- **Employee Assignment History**: Track employee device usage

### Analytics Dashboard
- **Real-time Statistics**: Live data updates and metrics
- **Interactive Charts**: Device type distribution, department distribution, condition overview
- **Monthly Trends**: Assignment patterns and trends over time
- **Recent Activity**: Latest assignment history with status indicators

### Device Types Supported
- Laptop
- Desktop  
- Mobile Phone

### Device Status Options
- Available
- Assigned
- Maintenance
- Lost
- Retired

### Device Condition Options
- New
- Good
- Damaged
- Under Repair

## Configuration

### Server Configuration (application.properties)
- Server Port: 8081
- Context Path: /airtel-inventory
- Database: MySQL on localhost:3306/airtel_inventory

### Database Connection
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/airtel_inventory
spring.datasource.username=root
spring.datasource.password=
```

## Testing the Application

### Test Complete Workflow
1. **Login**: Use credentials `24RP01839` / `24RP03971`
2. **Dashboard**: Verify all statistics and navigation links
3. **Add Device**: Register new device with all details
4. **Add Employee**: Register new employee information
5. **Assign Device**: Assign device to employee
6. **View Analytics**: Check analytics dashboard for charts
7. **Return Device**: Process device return
8. **Search & Filter**: Test advanced search functionality

### Test Analytics Features
1. Navigate to Analytics Dashboard
2. Verify all charts load correctly
3. Check real-time statistics
4. Test recent activity feed
5. Verify data visualization accuracy

## Troubleshooting

### Common Issues

1. **Database Connection Error**:
   - Ensure MySQL is running in XAMPP
   - Verify database name and credentials in application.properties
   - Check if MySQL is on default port 3306

2. **JPA Mapping Issues**:
   - Ensure all entity classes have @Entity annotation
   - Verify no javax.persistence imports (use jakarta.persistence)
   - Confirm all repository interfaces extend JpaRepository

3. **Template Rendering Issues**:
   - Verify Thymeleaf templates are in src/main/resources/templates
   - Ensure templates have .html extension
   - Check template syntax and namespace declarations

4. **Compilation Errors**:
   - Verify Java 17 is configured in IDE
   - Check Maven dependencies in pom.xml
   - Ensure all imports are correct

### Debug Tips
- Check console output for startup messages
- Verify database tables are created correctly
- Use browser developer tools for frontend issues
- Check application logs for error details

## Security Considerations

- Change default admin password in production
- Configure HTTPS for production deployment
- Implement proper password hashing (currently using plain text for demo)
- Add input validation and sanitization
- Implement role-based access control

## Export Functionality

- **Data Export**: Export devices, employees, and assignments to CSV/TXT
- **Comprehensive Reports**: Generate detailed inventory reports
- **Filter Exports**: Export filtered data sets
- **Professional Formatting**: Clean, organized export formats

## Audit Trail

- **Activity Logging**: Track all system activities
- **User Actions**: Monitor user interactions
- **IP Address Tracking**: Log access locations
- **Timestamp Records**: Complete audit timeline

## Professional Features Implemented

✅ **Advanced Analytics Dashboard** - Interactive charts and real-time statistics  
✅ **Professional UI/UX** - Modern, responsive design with Airtel branding  
✅ **Complete CRUD Operations** - Full device, employee, and assignment management  
✅ **Search & Filtering** - Advanced search capabilities across all modules  
✅ **Data Export** - CSV and TXT export functionality  
✅ **Audit Logging** - Complete activity tracking and audit trail  
✅ **Developer Attribution** - Professional credits and system information  

## Future Enhancements

- QR code generation for devices
- Email notifications for assignments
- Bulk operations for devices and employees
- Maintenance scheduling system
- User role management
- Backup and restore functionality
- API endpoints for mobile access
- Integration with Active Directory

## System Developers

**Developed By:**
- **SHYIRAMBERE MOISE** (Regno: 24RP01839)
- **UWINGABIYE DELPHINE** (Regno: 24RP03971)

**System Description:**
A comprehensive inventory management system for tracking and managing end-user equipment assignments, maintenance schedules, and employee device allocations.

## Support

For issues and questions, refer to the troubleshooting section or check the application logs for detailed error information.

### Application Access
- **URL**: `http://localhost:8081/airtel-inventory/login`
- **Credentials**: Username `24RP01839`, Password `24RP03971`
- **Port**: 8081 (not 8080)

---

**Project Status**: ✅ Complete and Production Ready

**Last Updated**: April 2026

**Version**: 1.0.0 Professional Edition
