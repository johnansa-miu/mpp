-- Drop existing tables if they exist (in reverse order of dependencies)
DROP TABLE IF EXISTS EmployeeProject CASCADE;
DROP TABLE IF EXISTS DepartmentProject CASCADE;
DROP TABLE IF EXISTS ClientProject CASCADE;
DROP TABLE IF EXISTS Employee CASCADE;
DROP TABLE IF EXISTS Project CASCADE;
DROP TABLE IF EXISTS Department CASCADE;
DROP TABLE IF EXISTS Client CASCADE;

-- Create Department table
CREATE TABLE Department (
    departmentId SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL,
    annualBudget NUMERIC(12, 2) NOT NULL
);

-- Create Employee table
CREATE TABLE Employee (
    employeeId SERIAL PRIMARY KEY,
    fullName VARCHAR(100) NOT NULL,
    title VARCHAR(100) NOT NULL,
    hireDate DATE NOT NULL,
    salary NUMERIC(12, 2) NOT NULL,
    departmentId INT NOT NULL,
    FOREIGN KEY (departmentId) REFERENCES Department(departmentId) ON DELETE CASCADE
);

-- Create Client table
CREATE TABLE Client (
    clientId SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    industry VARCHAR(100) NOT NULL,
    contactPerson VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Create Project table
CREATE TABLE Project (
    projectId SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    startDate DATE NOT NULL,
    endDate DATE,
    budget NUMERIC(12, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'NOT_STARTED',
    CHECK (status IN ('NOT_STARTED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'))
);

-- Create EmployeeProject junction table
CREATE TABLE EmployeeProject (
    employeeId INT NOT NULL,
    projectId INT NOT NULL,
    allocationPercentage NUMERIC(5, 2) NOT NULL,
    PRIMARY KEY (employeeId, projectId),
    FOREIGN KEY (employeeId) REFERENCES Employee(employeeId) ON DELETE CASCADE,
    FOREIGN KEY (projectId) REFERENCES Project(projectId) ON DELETE CASCADE,
    CHECK (allocationPercentage >= 0 AND allocationPercentage <= 100)
);

-- Create DepartmentProject junction table
CREATE TABLE DepartmentProject (
    departmentId INT NOT NULL,
    projectId INT NOT NULL,
    responsibility VARCHAR(255) NOT NULL,
    PRIMARY KEY (departmentId, projectId),
    FOREIGN KEY (departmentId) REFERENCES Department(departmentId) ON DELETE CASCADE,
    FOREIGN KEY (projectId) REFERENCES Project(projectId) ON DELETE CASCADE
);

-- Create ClientProject junction table
CREATE TABLE ClientProject (
    projectId INT NOT NULL,
    clientId INT NOT NULL,
    PRIMARY KEY (projectId, clientId),
    FOREIGN KEY (projectId) REFERENCES Project(projectId) ON DELETE CASCADE,
    FOREIGN KEY (clientId) REFERENCES Client(clientId) ON DELETE CASCADE
);

