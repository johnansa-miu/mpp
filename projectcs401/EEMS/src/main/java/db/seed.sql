-- Insert sample data for Department table (at least 5 records)
INSERT INTO Department (departmentId, name, location, annualBudget) VALUES
(1, 'Engineering', 'San Francisco, CA', 2500000.00),
(2, 'Marketing', 'New York, NY', 1200000.00),
(3, 'Human Resources', 'Chicago, IL', 800000.00),
(4, 'Finance', 'Boston, MA', 1500000.00),
(5, 'Operations', 'Seattle, WA', 1800000.00),
(6, 'Sales', 'Austin, TX', 2000000.00),
(7, 'Research & Development', 'San Diego, CA', 3000000.00);

-- Insert sample data for Employee table (at least 5 records)
INSERT INTO Employee (employeeId, fullName, title, hireDate, salary, departmentId) VALUES
(1, 'John Smith', 'Senior Software Engineer', '2020-03-15', 125000.00, 1),
(2, 'Sarah Johnson', 'Marketing Manager', '2019-07-22', 95000.00, 2),
(3, 'Michael Brown', 'HR Director', '2018-01-10', 110000.00, 3),
(4, 'Emily Davis', 'Financial Analyst', '2021-05-18', 85000.00, 4),
(5, 'David Wilson', 'Operations Coordinator', '2020-11-03', 70000.00, 5),
(6, 'Jessica Martinez', 'Senior Sales Executive', '2019-09-12', 105000.00, 6),
(7, 'Robert Garcia', 'Lead Software Engineer', '2017-06-20', 145000.00, 1),
(8, 'Linda Anderson', 'Research Scientist', '2021-02-28', 120000.00, 7),
(9, 'James Taylor', 'Junior Developer', '2022-08-01', 75000.00, 1),
(10, 'Maria Rodriguez', 'Content Marketing Specialist', '2021-10-15', 68000.00, 2);

-- Insert sample data for Client table (at least 5 records)
INSERT INTO Client (clientId, name, industry, contactPerson, phone, email) VALUES
(1, 'TechCorp Solutions', 'Technology', 'Alice Cooper', '415-555-0101', 'alice.cooper@techcorp.com'),
(2, 'Global Finance Inc.', 'Finance', 'Bob Mitchell', '212-555-0202', 'bob.mitchell@globalfinance.com'),
(3, 'Healthcare Partners', 'Healthcare', 'Carol White', '312-555-0303', 'carol.white@healthpartners.com'),
(4, 'Retail Giants LLC', 'Retail', 'David Green', '617-555-0404', 'david.green@retailgiants.com'),
(5, 'Manufacturing Pro', 'Manufacturing', 'Emma Black', '206-555-0505', 'emma.black@mfgpro.com'),
(6, 'Education First', 'Education', 'Frank Johnson', '512-555-0606', 'frank.johnson@edufirst.com'),
(7, 'Energy Solutions Corp', 'Energy', 'Grace Lee', '858-555-0707', 'grace.lee@energysol.com');

-- Insert sample data for Project table (at least 5 records)
INSERT INTO Project (projectId, name, description, startDate, endDate, budget, status) VALUES
(1, 'Cloud Migration', 'Migrate legacy systems to cloud infrastructure', '2024-01-15', '2024-12-31', 500000.00, 'IN_PROGRESS'),
(2, 'Digital Marketing Campaign', 'Launch comprehensive digital marketing strategy', '2024-02-01', '2024-08-31', 250000.00, 'IN_PROGRESS'),
(3, 'HR Management System', 'Implement new HR management software', '2024-03-10', '2024-09-30', 180000.00, 'NOT_STARTED'),
(4, 'Financial Analytics Platform', 'Build real-time financial analytics dashboard', '2023-10-01', '2024-06-30', 350000.00, 'IN_PROGRESS'),
(5, 'Supply Chain Optimization', 'Optimize supply chain and inventory management', '2024-01-20', '2024-10-31', 420000.00, 'IN_PROGRESS'),
(6, 'Mobile App Development', 'Develop cross-platform mobile application', '2023-08-15', '2024-04-30', 300000.00, 'COMPLETED'),
(7, 'AI Research Initiative', 'Research and develop AI-powered solutions', '2024-04-01', '2025-03-31', 800000.00, 'NOT_STARTED');

-- Insert sample data for EmployeeProject junction table (at least 5 records)
INSERT INTO EmployeeProject (employeeId, projectId, allocationPercentage) VALUES
(1, 1, 80.0),
(1, 6, 20.0),
(2, 2, 100.0),
(3, 3, 75.0),
(4, 4, 90.0),
(5, 5, 85.0),
(6, 2, 50.0),
(7, 1, 100.0),
(8, 7, 100.0),
(9, 1, 60.0),
(9, 6, 40.0),
(10, 2, 70.0);

-- Insert sample data for DepartmentProject junction table (at least 5 records)
INSERT INTO DepartmentProject (departmentId, projectId, responsibility) VALUES
(1, 1, 'Technical implementation and architecture'),
(2, 2, 'Campaign strategy and execution'),
(3, 3, 'Requirements gathering and user training'),
(4, 4, 'Data analysis and reporting requirements'),
(5, 5, 'Process optimization and monitoring'),
(1, 6, 'Mobile app development and testing'),
(7, 7, 'Research and development of AI algorithms'),
(4, 1, 'Budget management and cost analysis'),
(2, 6, 'Marketing and user acquisition strategy'),
(1, 4, 'Platform development and maintenance');

-- Insert sample data for ClientProject junction table (at least 5 records)
INSERT INTO ClientProject (projectId, clientId) VALUES
(1, 1),
(2, 4),
(3, 3),
(4, 2),
(5, 5),
(6, 1),
(7, 7),
(2, 6),
(4, 4),
(5, 4);

-- Reset sequences to continue from the last inserted ID
SELECT setval('department_departmentid_seq', (SELECT MAX(departmentId) FROM Department));
SELECT setval('employee_employeeid_seq', (SELECT MAX(employeeId) FROM Employee));
SELECT setval('client_clientid_seq', (SELECT MAX(clientId) FROM Client));
SELECT setval('project_projectid_seq', (SELECT MAX(projectId) FROM Project));

