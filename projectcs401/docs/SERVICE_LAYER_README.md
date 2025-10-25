# Service Layer Implementation

## Overview
This service layer implements the business logic for the Enterprise Employee Management System (EEMS). It follows best practices, uses Java Stream API extensively, implements generic types where appropriate, and adheres to the DRY (Don't Repeat Yourself) principle.

## Architecture

### Design Patterns Used
1. **Generic Base Service**: All services extend `BaseService<T, ID>` to avoid code duplication
2. **Factory Pattern**: `ServiceFactory` provides centralized service instantiation
3. **Dependency Injection**: Services receive their dependencies through constructors
4. **Exception Handling**: Custom `ServiceException` for service-layer errors
5. **Transaction Management**: Database transactions for critical operations

## Service Classes

### 1. BaseService<T, ID>
**Purpose**: Generic base class providing common CRUD operations

**Key Features**:
- Eliminates code duplication across services
- Generic implementation for type safety
- Standardized error handling
- Protected helper method `getByIdOrThrow()` for validation

**Methods**:
- `save(T entity)`: Save an entity
- `findById(ID id)`: Find entity by ID
- `findAll()`: Retrieve all entities
- `update(T entity)`: Update an entity
- `deleteById(ID id)`: Delete entity by ID

### 2. ProjectService
**Purpose**: Handles project-related business logic

**Key Business Methods**:

#### Task 1: calculateProjectHRCost(int projectId)
Calculates projected human resource cost for a project.

**Algorithm**:
1. Validate project exists and has valid dates
2. Calculate project duration in months (rounded up)
3. For each employee assigned:
   - Get employee salary
   - Calculate monthly salary (annual / 12)
   - Apply allocation percentage
   - Multiply by duration: `Cost = (monthlySalary × allocation%) × duration`
4. Sum all employee costs using Stream API

**Example**:
```java
ProjectService projectService = ServiceFactory.getProjectService();
double hrCost = projectService.calculateProjectHRCost(1);
System.out.println("Total HR Cost: $" + hrCost);
```

#### Task 2: getProjectsByDepartment(int departmentId, String sortBy)
Retrieves all active (IN_PROGRESS) projects for a department, sorted by specified field.

**Supported Sort Fields**:
- `budget` / `project_budget`
- `end_date` / `enddate`
- `start_date` / `startdate`
- `name`

**Algorithm**:
1. Get all department-project associations
2. Filter projects by department ID
3. Filter by IN_PROGRESS status
4. Sort using dynamic comparator based on sortBy parameter
5. Return sorted list

**Example**:
```java
List<Project> projects = projectService.getProjectsByDepartment(1, "budget");
projects.forEach(p -> System.out.println(p.getName()));
```

**Additional Methods**:
- `findProjectsEndingWithin(int days)`: Find projects ending soon
- `findByStatus(ProjectStatus status)`: Filter by status
- `findByBudgetRange(double min, double max)`: Filter by budget range
- `calculateDepartmentProjectBudget(int deptId)`: Sum department project budgets

### 3. ClientService
**Purpose**: Manages client-related operations

**Key Business Methods**:

#### Task 3: findClientsByUpcomingProjectDeadline(int daysUntilDeadline)
Finds clients with projects scheduled to end within specified days.

**Algorithm**:
1. Calculate target date (current date + days)
2. Find all projects ending between now and target date using Stream API
3. Get client IDs associated with those projects
4. Return client objects

**Example**:
```java
ClientService clientService = ServiceFactory.getClientService();
List<Client> clients = clientService.findClientsByUpcomingProjectDeadline(30);
System.out.println("Clients with deadlines: " + clients.size());
```

**Additional Methods**:
- `findByIndustry(String industry)`: Filter clients by industry
- `getProjectsForClient(int clientId)`: Get all client projects
- `calculateClientTotalProjectBudget(int clientId)`: Sum project budgets
- `findHighValueClients(double threshold)`: Find high-value clients

### 4. EmployeeService
**Purpose**: Handles employee operations including transfers

**Key Business Methods**:

#### Task 4: transferEmployeeToDepartment(int employeeId, int newDepartmentId)
Transfers an employee to a new department with transaction support.

**Algorithm**:
1. Start database transaction (disable auto-commit)
2. Validate employee exists
3. Validate target department exists
4. Validate employee is not already in target department
5. Update employee's department ID
6. Commit transaction
7. Rollback on any error

**Example**:
```java
EmployeeService employeeService = ServiceFactory.getEmployeeService();
try {
    employeeService.transferEmployeeToDepartment(1, 2);
    System.out.println("Transfer successful");
} catch (ServiceException e) {
    System.out.println("Transfer failed: " + e.getMessage());
}
```

**Additional Methods**:
- `findByDepartment(int deptId)`: Get employees in department
- `findBySalaryGreaterThan(double salary)`: Filter by salary
- `calculateDepartmentSalaryExpense(int deptId)`: Sum salaries
- `calculateAverageSalary(int deptId)`: Calculate average salary

### 5. DepartmentService
**Purpose**: Manages department-level operations

**Key Methods**:
- `findByLocation(String location)`: Filter by location
- `getEmployeeCount(int deptId)`: Count employees
- `findByMinimumBudget(double budget)`: Filter by budget
- `getDepartmentsByEmployeeCount(boolean ascending)`: Sort by employee count
- `calculateTotalSalaryExpense(int deptId)`: Calculate salary expense
- `isWithinBudget(int deptId)`: Check budget compliance
- `getDepartmentsOverBudget()`: Find over-budget departments

## Java Stream API Usage

### Examples Throughout the Implementation

1. **Filtering and Mapping**:
```java
List<Integer> projectIds = departmentProjectRepository.findAll().stream()
    .filter(dp -> dp.getDepartmentId() == departmentId)
    .map(DepartmentProject::getProjectId)
    .collect(Collectors.toList());
```

2. **Complex Filtering with Multiple Conditions**:
```java
List<Project> activeProjects = projectRepository.findAll().stream()
    .filter(p -> projectIds.contains(p.getId()))
    .filter(p -> p.getStatus() == ProjectStatus.IN_PROGRESS)
    .collect(Collectors.toList());
```

3. **Aggregation Operations**:
```java
double totalCost = employeeProjects.stream()
    .mapToDouble(ep -> calculateEmployeeCost(ep, durationInMonths))
    .sum();
```

4. **Sorting with Dynamic Comparators**:
```java
return projects.stream()
    .sorted(comparator)
    .collect(Collectors.toList());
```

5. **Set Operations for Deduplication**:
```java
Set<Integer> clientIds = clientProjectRepository.findAll().stream()
    .filter(cp -> projectIdsEndingSoon.contains(cp.getProjectId()))
    .map(ClientProject::getClientId)
    .collect(Collectors.toSet());
```

## Best Practices Implemented

### 1. Generic Types
- `BaseService<T, ID>` provides type-safe operations
- Reduces code duplication across services
- Maintains compile-time type checking

### 2. DRY Principle
- Common CRUD operations in BaseService
- Reusable helper methods (e.g., `getByIdOrThrow()`)
- Shared calculation methods (e.g., `calculateEmployeeCost()`)

### 3. Exception Handling
- Custom `ServiceException` for service-layer errors
- Proper exception wrapping and context
- Transaction rollback on errors

### 4. Transaction Management
- Explicit transaction handling for critical operations
- Proper commit/rollback logic
- Resource cleanup in finally blocks

### 5. Validation
- Input validation before operations
- Entity existence checks
- Business rule validation

### 6. Separation of Concerns
- Services handle business logic only
- Repositories handle data access
- Clear layer boundaries

## Usage Example

```java
public class Example {
    public static void main(String[] args) {
        // Get service instances
        ProjectService projectService = ServiceFactory.getProjectService();
        ClientService clientService = ServiceFactory.getClientService();
        EmployeeService employeeService = ServiceFactory.getEmployeeService();
        
        // Task 1: Calculate project cost
        double cost = projectService.calculateProjectHRCost(1);
        System.out.println("HR Cost: $" + cost);
        
        // Task 2: Get department projects
        List<Project> projects = projectService.getProjectsByDepartment(1, "budget");
        
        // Task 3: Find clients with upcoming deadlines
        List<Client> clients = clientService.findClientsByUpcomingProjectDeadline(30);
        
        // Task 4: Transfer employee
        employeeService.transferEmployeeToDepartment(1, 2);
    }
}
```

## Error Handling

All service methods throw `ServiceException` on errors. Always wrap service calls in try-catch blocks:

```java
try {
    employeeService.transferEmployeeToDepartment(employeeId, newDeptId);
} catch (ServiceException e) {
    System.err.println("Error: " + e.getMessage());
    // Handle error appropriately
}
```

## Requirements Met

✅ **Task 1**: Calculate Project HR Cost - `ProjectService.calculateProjectHRCost()`
✅ **Task 2**: Department Project Report - `ProjectService.getProjectsByDepartment()`
✅ **Task 3**: High-Value Client Identification - `ClientService.findClientsByUpcomingProjectDeadline()`
✅ **Task 4**: Employee Transfer Transaction - `EmployeeService.transferEmployeeToDepartment()`

✅ **Java Stream API**: Extensively used throughout all services
✅ **Generic Types**: BaseService<T, ID> and proper type parameters
✅ **Best Practices**: Proper exception handling, separation of concerns, validation
✅ **DRY Principle**: BaseService eliminates code duplication

## Testing

To test the service layer, run the `ServiceDemo` class:

```bash
java main.java.service.ServiceDemo
```

This will demonstrate all four required tasks with sample data.

## Future Enhancements

Potential improvements:
1. Add caching layer for frequently accessed data
2. Implement async processing for heavy operations
3. Add comprehensive logging with SLF4J
4. Implement audit trail for critical operations
5. Add pagination for large result sets
6. Implement batch operations for bulk updates

