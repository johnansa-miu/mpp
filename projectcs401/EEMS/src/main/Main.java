package main;

import main.java.controller.*;
import main.java.domain.enums.ProjectSortParameters;
import main.java.domain.enums.ProjectStatus;
import main.java.domain.models.*;
import main.java.service.ServiceException;

import java.time.LocalDate;
import java.util.List;

/**
 * Main demonstration class for the Employee Entity Management System (EEMS)
 * Demonstrates all CRUD operations and specialized business logic tasks
 * using only the Controller layer (no direct service or repository access)
 */
public class Main {

    // Get controller instances
    private static final EmployeeController employeeController = ControllerFactory.getEmployeeController();
    private static final DepartmentController departmentController = ControllerFactory.getDepartmentController();
    private static final ProjectController projectController = ControllerFactory.getProjectController();
    private static final ClientController clientController = ControllerFactory.getClientController();

    public static void main(String[] args) {
        System.out.println("=".repeat(80));
        System.out.println("EMPLOYEE ENTITY MANAGEMENT SYSTEM (EEMS) - DEMONSTRATION");
        System.out.println("=".repeat(80));
        System.out.println();

        try {
            // 1. CRUD Operations Demonstration
            demonstrateCRUDOperations();

            System.out.println("\n" + "=".repeat(80));
            System.out.println("SPECIALIZED BUSINESS LOGIC TASKS");
            System.out.println("=".repeat(80) + "\n");

            // 2. Task 1: Calculate projected human resource cost for a project
            demonstrateTask1_CalculateProjectHRCost();

            // 3. Task 2: Get all active projects for a department, sorted by specified field
            demonstrateTask2_GetActiveProjectsSorted();

            // 4. Task 3: Find clients with projects scheduled to end within specified days
            demonstrateTask3_FindClientsByUpcomingDeadline();

            // 5. Task 4: Transfer employee to a new department
            demonstrateTask4_TransferEmployee();

            // Additional demonstrations
            demonstrateAdditionalFeatures();

            System.out.println("\n" + "=".repeat(80));
            System.out.println("DEMONSTRATION COMPLETED SUCCESSFULLY");
            System.out.println("=".repeat(80));

        } catch (ServiceException e) {
            System.err.println("\n❌ Service Exception: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("\n❌ Unexpected Error: " + e.getMessage());
        }
    }

    /**
     * Demonstrates CRUD operations for all entities
     */
    private static void demonstrateCRUDOperations() {
        System.out.println("SECTION 1: CRUD OPERATIONS DEMONSTRATION");
        System.out.println("-".repeat(80));

        // ============ DEPARTMENT CRUD ============
        System.out.println("\n📁 DEPARTMENT Operations:");
        System.out.println("-".repeat(40));

        // CREATE
        Department newDept = new Department(0, "Innovation Lab", "San Francisco", 1500000.0);
        Department createdDept = departmentController.save(newDept);
        System.out.println("✓ Created: " + createdDept);

        // READ
        List<Department> allDepartments = departmentController.findAll();
        System.out.println("✓ Found " + allDepartments.size() + " departments:");
        allDepartments.forEach(dept -> System.out.println("  - " + dept));

        // UPDATE
        if (createdDept != null) {
            createdDept.setAnnualBudget(1600000.0);
            Department updatedDept = departmentController.update(createdDept);
            System.out.println("✓ Updated: " + updatedDept);
        }

        // ============ EMPLOYEE CRUD ============
        System.out.println("\n👤 EMPLOYEE Operations:");
        System.out.println("-".repeat(40));

        // CREATE
        Employee newEmployee = new Employee(0, "Jane Smith", "Senior Developer",
                LocalDate.of(2024, 1, 15), 95000.0, 1);
        Employee createdEmployee = employeeController.save(newEmployee);
        System.out.println("✓ Created: " + createdEmployee);

        // READ
        List<Employee> allEmployees = employeeController.findAll();
        System.out.println("✓ Found " + allEmployees.size() + " employees:");
        allEmployees.stream().limit(5).forEach(emp -> System.out.println("  - " + emp));
        if (allEmployees.size() > 5) {
            System.out.println("  ... and " + (allEmployees.size() - 5) + " more");
        }

        // UPDATE
        if (createdEmployee != null) {
            createdEmployee.setSalary(98000.0);
            createdEmployee.setTitle("Lead Developer");
            Employee updatedEmployee = employeeController.update(createdEmployee);
            System.out.println("✓ Updated: " + updatedEmployee);
        }

        // ============ CLIENT CRUD ============
        System.out.println("\n🏢 CLIENT Operations:");
        System.out.println("-".repeat(40));

        // CREATE
        Client newClient = new Client(0, "TechCorp Solutions", "Technology",
                "John Doe", "+1-555-0100", "john.doe@techcorp.com");
        Client createdClient = clientController.save(newClient);
        System.out.println("✓ Created: " + createdClient);

        // READ
        List<Client> allClients = clientController.findAll();
        System.out.println("✓ Found " + allClients.size() + " clients:");
        allClients.forEach(client -> System.out.println("  - Client ID=" + client.getId() +
                ", Name=" + client.getName() + ", Industry=" + client.getIndustry()));

        // UPDATE
        if (createdClient != null) {
            createdClient.setContactPerson("Jane Doe");
            createdClient.setPhone("+1-555-0101");
            Client updatedClient = clientController.update(createdClient);
            System.out.println("✓ Updated: Client ID=" + updatedClient.getId() +
                    ", Contact=" + updatedClient.getContactPerson());
        }

        // ============ PROJECT CRUD ============
        System.out.println("\n📊 PROJECT Operations:");
        System.out.println("-".repeat(40));

        // CREATE
        Project newProject = new Project(0, "AI Integration Platform",
                "Develop AI-powered integration platform",
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2025, 5, 31),
                750000.0);
        newProject.setStatus(ProjectStatus.NOT_STARTED);
        Project createdProject = projectController.save(newProject);
        System.out.println("✓ Created: " + createdProject);

        // READ
        List<Project> allProjects = projectController.findAll();
        System.out.println("✓ Found " + allProjects.size() + " projects:");
        allProjects.stream().limit(3).forEach(proj -> System.out.println("  - " + proj));
        if (allProjects.size() > 3) {
            System.out.println("  ... and " + (allProjects.size() - 3) + " more");
        }

        // UPDATE
        if (createdProject != null) {
            createdProject.setStatus(ProjectStatus.IN_PROGRESS);
            createdProject.setBudget(800000.0);
            Project updatedProject = projectController.update(createdProject);
            System.out.println("✓ Updated: " + updatedProject);
        }

        // DELETE demonstration (optional - commenting out to preserve data)
        System.out.println("\n🗑️  DELETE Operations:");
        System.out.println("-".repeat(40));
        System.out.println("ℹ️  Delete operations available but not executed to preserve demo data");
        System.out.println("   Use: controller.deleteById(id) to delete entities");
    }

    /**
     * Task 1: Calculate projected human resource cost for a project
     */
    private static void demonstrateTask1_CalculateProjectHRCost() {
        System.out.println("\n📈 TASK 1: Calculate Projected Human Resource Cost");
        System.out.println("-".repeat(80));

        // Get all projects and calculate HR cost for each
        List<Project> projects = projectController.findAll();

        if (projects.isEmpty()) {
            System.out.println("⚠️  No projects found in the database");
            return;
        }

        System.out.println("Calculating HR costs for projects:\n");

        for (Project project : projects.stream().limit(5).toList()) {
            try {
                double hrCost = projectController.calculateProjectHRCost(project.getId());
                System.out.printf("Project: %-30s | HR Cost: $%,.2f%n",
                        project.getName(), hrCost);
            } catch (ServiceException e) {
                System.out.printf("Project: %-30s | Error: %s%n",
                        project.getName(), e.getMessage());
            }
        }

        if (projects.size() > 5) {
            System.out.println("... and " + (projects.size() - 5) + " more projects");
        }
    }

    /**
     * Task 2: Get all active projects for a department, sorted by specified field
     */
    private static void demonstrateTask2_GetActiveProjectsSorted() {
        System.out.println("\n📋 TASK 2: Get Active Projects for Department (Sorted)");
        System.out.println("-".repeat(80));

        List<Department> departments = departmentController.findAll();

        if (departments.isEmpty()) {
            System.out.println("⚠️  No departments found in the database");
            return;
        }

        // Test with first department
        Department testDept = departments.get(0);
        System.out.println("Department: " + testDept.getName() + " (ID: " + testDept.getId() + ")");
        System.out.println();

        // Test different sort parameters
        ProjectSortParameters[] sortOptions = {
                ProjectSortParameters.PROJECT_NAME,
                ProjectSortParameters.PROJECT_BUDGET,
                ProjectSortParameters.END_DATE,
                ProjectSortParameters.START_DATE
        };

        for (ProjectSortParameters sortBy : sortOptions) {
            System.out.println("Sorted by " + sortBy + ":");
            try {
                List<Project> sortedProjects = departmentController.getProjectsByDepartment(
                        testDept.getId(), sortBy);

                if (sortedProjects.isEmpty()) {
                    System.out.println("  No active projects found");
                } else {
                    for (Project project : sortedProjects.stream().limit(3).toList()) {
                        System.out.printf("  - %-30s | Budget: $%,.2f | Status: %s%n",
                                project.getName(), project.getBudget(), project.getStatus());
                    }
                    if (sortedProjects.size() > 3) {
                        System.out.println("  ... and " + (sortedProjects.size() - 3) + " more");
                    }
                }
            } catch (ServiceException e) {
                System.out.println("  Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    /**
     * Task 3: Find clients with projects scheduled to end within specified days
     */
    private static void demonstrateTask3_FindClientsByUpcomingDeadline() {
        System.out.println("\n⏰ TASK 3: Find Clients with Upcoming Project Deadlines");
        System.out.println("-".repeat(80));

        // Test with different timeframes
        int[] timeframes = {30, 60, 90, 180};

        for (int days : timeframes) {
            System.out.println("\nClients with projects ending within " + days + " days:");
            System.out.println("-".repeat(40));

            try {
                List<Client> clients = clientController.findClientsByUpcomingProjectDeadline(days);

                if (clients.isEmpty()) {
                    System.out.println("  No clients found with projects ending in this timeframe");
                } else {
                    System.out.println("  Found " + clients.size() + " client(s):");
                    for (Client client : clients) {
                        System.out.printf("  - %-30s | Industry: %-20s | Contact: %s%n",
                                client.getName(), client.getIndustry(), client.getContactPerson());
                    }
                }
            } catch (ServiceException e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }
    }

    /**
     * Task 4: Transfer employee to a new department
     */
    private static void demonstrateTask4_TransferEmployee() {
        System.out.println("\n🔄 TASK 4: Transfer Employee to New Department");
        System.out.println("-".repeat(80));

        List<Employee> employees = employeeController.findAll();
        List<Department> departments = departmentController.findAll();

        if (employees.isEmpty() || departments.size() < 2) {
            System.out.println("⚠️  Insufficient data for transfer demonstration");
            return;
        }

        // Get first employee and their details
        Employee employeeToTransfer = employees.get(0);
        int currentDeptId = employeeToTransfer.getDepartmentId();

        // Find a different department
        Department newDepartment = departments.stream()
                .filter(dept -> dept.getId() != currentDeptId)
                .findFirst()
                .orElse(null);

        if (newDepartment == null) {
            System.out.println("⚠️  No alternative department available for transfer");
            return;
        }

        System.out.println("Transfer Details:");
        System.out.println("  Employee: " + employeeToTransfer.getFullName() +
                " (ID: " + employeeToTransfer.getId() + ")");
        System.out.println("  Current Department ID: " + currentDeptId);
        System.out.println("  New Department: " + newDepartment.getName() +
                " (ID: " + newDepartment.getId() + ")");
        System.out.println();

        try {
            // Perform transfer
            employeeController.transferEmployeeToDepartment(
                    employeeToTransfer.getId(),
                    newDepartment.getId());

            System.out.println("✓ Transfer completed successfully!");

            // Verify transfer
            Employee updatedEmployee = employeeController.findById(employeeToTransfer.getId())
                    .orElseThrow(() -> new ServiceException("Employee not found after transfer"));

            System.out.println("  Verified: Employee now in Department ID " +
                    updatedEmployee.getDepartmentId());

            // Transfer back to original department for demo consistency
            System.out.println("\n  Transferring back to original department...");
            employeeController.transferEmployeeToDepartment(
                    employeeToTransfer.getId(),
                    currentDeptId);
            System.out.println("✓ Restored to original department");

        } catch (ServiceException e) {
            System.out.println("❌ Transfer failed: " + e.getMessage());
        }
    }

    /**
     * Demonstrate additional features available through controllers
     */
    private static void demonstrateAdditionalFeatures() {
        System.out.println("\n\n🌟 ADDITIONAL FEATURES DEMONSTRATION");
        System.out.println("-".repeat(80));

        // Employee features
        System.out.println("\n💼 Employee Features:");
        System.out.println("-".repeat(40));
        try {
            List<Employee> highEarners = employeeController.findBySalaryGreaterThan(80000);
            System.out.println("✓ Employees with salary > $80,000: " + highEarners.size());

            if (!departmentController.findAll().isEmpty()) {
                int deptId = departmentController.findAll().get(0).getId();
                double avgSalary = employeeController.calculateAverageSalary(deptId);
                System.out.printf("✓ Average salary in Department %d: $%,.2f%n", deptId, avgSalary);
            }
        } catch (ServiceException e) {
            System.out.println("  Error: " + e.getMessage());
        }

        // Department features
        System.out.println("\n🏛️  Department Features:");
        System.out.println("-".repeat(40));
        try {
            List<Department> departments = departmentController.findByLocation("New York");
            System.out.println("✓ Departments in New York: " + departments.size());

            List<Department> overBudget = departmentController.getDepartmentsOverBudget();
            System.out.println("✓ Departments over budget: " + overBudget.size());
        } catch (ServiceException e) {
            System.out.println("  Error: " + e.getMessage());
        }

        // Project features
        System.out.println("\n🚀 Project Features:");
        System.out.println("-".repeat(40));
        try {
            List<Project> activeProjects = projectController.findByStatus(ProjectStatus.IN_PROGRESS);
            System.out.println("✓ Active (In Progress) projects: " + activeProjects.size());

            List<Project> endingSoon = projectController.findProjectsEndingWithin(60);
            System.out.println("✓ Projects ending within 60 days: " + endingSoon.size());

            List<Project> budgetRange = projectController.findByBudgetRange(100000, 500000);
            System.out.println("✓ Projects with budget $100K-$500K: " + budgetRange.size());
        } catch (ServiceException e) {
            System.out.println("  Error: " + e.getMessage());
        }

        // Client features
        System.out.println("\n🤝 Client Features:");
        System.out.println("-".repeat(40));
        try {
            List<Client> techClients = clientController.findByIndustry("Technology");
            System.out.println("✓ Clients in Technology industry: " + techClients.size());

            List<Client> highValueClients = clientController.findHighValueClients(1000000);
            System.out.println("✓ High-value clients (>$1M total budget): " + highValueClients.size());

            if (!clientController.findAll().isEmpty()) {
                int clientId = clientController.findAll().get(0).getId();
                double totalBudget = clientController.calculateClientTotalProjectBudget(clientId);
                System.out.printf("✓ Total project budget for Client %d: $%,.2f%n",
                        clientId, totalBudget);
            }
        } catch (ServiceException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }
}
