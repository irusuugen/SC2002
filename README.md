# BTO Management System - SC2002 Assignment

## Overview
This project is a **Build-To-Order (BTO) Management System** developed as part of the SC2002 Object-Oriented Design & Programming course at Nanyang Technological University. The system serves as a centralized hub for applicants and HDB staff to manage BTO projects, applications, and enquiries. It is implemented as a **Command Line Interface (CLI) application** using Java, adhering to object-oriented principles.

---

## Features
### User Roles and Capabilities
1. **Applicant**  
   - View projects open to their user group (Single/Married).  
   - Apply for projects (with eligibility checks).  
   - Manage enquiries (create, view, edit, delete).  
   - Track application status (Pending/Successful/Unsuccessful/Booked).  
   - Request withdrawal from applications.  

2. **HDB Officer**  
   - All Applicant capabilities.  
   - Register to handle projects (subject to approval).  
   - Update flat availability and applicant booking status.  
   - Generate flat selection receipts.  
   - Reply to project enquiries.  

3. **HDB Manager**  
   - Create, edit, and delete BTO projects.  
   - Toggle project visibility.  
   - Approve/reject Officer registrations and Applicant applications.  
   - Generate filtered reports (e.g., by marital status or flat type).  
   - Manage enquiries across all projects.  

---

## Technical Specifications
- **Language**: Java  
- **Paradigm**: Object-Oriented Programming (OOP)  
- **Key OO Concepts Applied**:  
  - Encapsulation (e.g., private fields with getters/setters).  
  - Inheritance (e.g., `User` superclass for `Applicant`, `HDBOfficer`, `HDBManager`).  
  - Polymorphism (e.g., method overriding for role-specific actions).  
  - SOLID principles (e.g., single responsibility in classes).  
- **Data Storage**: Flat files (CSV/text) for user and project data.  
- **Constraints**:  
  - No databases (MySQL, etc.) or JSON/XML used.  
  - CLI-only interface (no GUI).  

---

## UML Diagrams
1. **Class Diagram**:  
   - Shows entity classes (`User`, `Project`, `Enquiry`), control/boundary classes, and relationships.    
2. **Sequence Diagram**:  
   - Illustrates HDB Officer’s workflow (applying for BTO + registering for a project).  

---

## Setup Instructions
1. **Prerequisites**:  
   - JDK 8+ installed.  
   - IDE (e.g., IntelliJ, Eclipse) or command-line tools.  
2. **Run the Application**:  
   - Clone the repository:  
     ```bash
     git clone https://github.com/irusuugen/SC2002.git
     ```
   - Open the src folder:  
      ```bash
      cd SC2002/src
      ```
   - Compile `Main.java`:  
     ```bash
     javac Main.java
     ```
   - Compile `Main.java`:  
     ```bash
     java Main
     ```
---

## Additional Features
- **Password Change**: Users can update their default password (`password`).  
- **Dynamic Filtering**: Saved filter settings when switching menus.  
- **Comprehensive Error Handling**: Validates NRIC format, age, marital status, etc.  

---
## Contributors
- Trongmetheerat Theerapat
- Suthamkasem Thanwisit 
- Aempongpaitoon Pattanon
- Aye Su Nandar Michelle
- Murali Iniya
