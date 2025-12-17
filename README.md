## Clinic Management System
## Overview
This project is a Clinic Management System designed to demonstrate core healthcare workflows including user management, doctor scheduling, patient records, appointments, visits, consultations, and reporting dashboards.
The system emphasizes data integrity, real-world constraints, and clear relational design.
## Default Login Credentials

### Admin User
- **Username:** Admin
- **Password:** Password@123

### System Requirements**

**Backend**: _Java JDK 17 (recommended), Maven (dependency management), MySQL 8.x, Internet connection (required for Maven to download dependencies)_
**Frontend**: _Web browser (Chrome / Edge recommended)_

### Java & Dependency Requirements**

All dependencies are managed via Maven and will be downloaded automatically on first build.
Key dependencies include:

  Spring Boot,   Spring Web,   Spring Data JPA,   MySQL Connector,   Hibernate,   Spring Validation,   Spring Security (basic authentication support)

⚠️ _**Internet access is required the first time the project is built to download all dependencies.**_

## Database Setup (MySQL)
Run Database Scripts (IMPORTANT ORDER)
_**Script Location : https://github.com/Nyein-Ko-Lat/HealthCareSystem/tree/master/DatabaseFiles**_
Execute the following SQL files in order:

01_db_schema.sql : **Creates all tables**

Defines primary keys, foreign keys, constraints, and relationships

02_triggers.sql :

Creates database triggers for data consistency and automation

03_views.sql :

Creates database views used for reporting, dashboard queries, and all listing and get data store procedures use views

04_stored_procedures.sql

Creates stored procedures used by the application and reports

⚠️ Do not change the order, as later scripts depend on earlier objects.
Dashboard & Report Data Setup

To support dashboard and reporting features, sample data is provided via SQL scripts.

### Master Data Insert

05_db_insert_data.sql : Inserts following master data

Doctors, Patients, Doctor schedules

⚠️ _**This script:**_ :

_Deletes all existing related data_

_Resets auto-increment values_

_Prevents duplicate record conflicts_

### Transaction Data Insert

06_db_insert_transactions.sql : Inserts following transaction tables

Appointments, Patient visits, Consultation / diagnosis records

⚠️ **_Depends on data created in the previous script_**
