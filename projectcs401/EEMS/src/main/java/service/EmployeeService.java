package main.java.service;

import main.java.domain.models.Employee;
import main.java.repository.EmployeeRepository;
import main.java.repository.DepartmentRepository;

import java.util.List;

/**
 * Service layer for Employee operations
 * Implements business logic for employee management
 */
public class EmployeeService extends BaseService<Employee, Integer> {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        super(employeeRepository);
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    /**
     * Task 4: Transfer employee to a new department
     * Service layer handles business validation, repository handles transaction
     *
     * @param employeeId the employee ID to transfer
     * @param newDepartmentId the new department ID
     * @throws ServiceException if validation fails or transaction fails
     */
    public void transferEmployeeToDepartment(int employeeId, int newDepartmentId) {
        try {
            // Business Validation: Validate employee exists
            Employee employee = getByIdOrThrow(employeeId);

            // Business Validation: Validate new department exists
            departmentRepository.findById(newDepartmentId)
                    .orElseThrow(() -> new ServiceException(
                            "Cannot transfer employee: Department with ID " + newDepartmentId + " does not exist"));

            // Business Validation: Validate employee is not already in the target department
            if (employee.getDepartmentId() == newDepartmentId) {
                throw new ServiceException(
                        "Employee is already in department with ID " + newDepartmentId);
            }

            // Delegate to repository for transactional database operation
            employeeRepository.updateEmployeeDepartment(employeeId, newDepartmentId);

        } catch (RuntimeException e) {
            // Wrap repository exceptions in service exception
            if (e instanceof ServiceException) {
                throw e;
            }
            throw new ServiceException("Failed to transfer employee to department", e);
        }
    }


    /**
     * Find employees by department
     * @param departmentId the department ID
     * @return list of employees in the department
     */
    public List<Employee> findByDepartment(int departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    /**
     * Find employees with salary greater than specified amount
     * @param minSalary the minimum salary
     * @return list of employees with salary greater than minSalary
     */
    public List<Employee> findBySalaryGreaterThan(double minSalary) {
        return employeeRepository.findBySalaryGreaterThan(minSalary);
    }

    /**
     * Get total salary expense for a department
     * @param departmentId the department ID
     * @return total salary expense
     */
    public double calculateDepartmentSalaryExpense(int departmentId) {
        return findByDepartment(departmentId).stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    /**
     * Get average salary for a department
     * @param departmentId the department ID
     * @return average salary or 0 if no employees
     */
    public double calculateAverageSalary(int departmentId) {
        return findByDepartment(departmentId).stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }
}

