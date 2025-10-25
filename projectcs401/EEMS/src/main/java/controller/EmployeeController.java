package main.java.controller;

import main.java.domain.models.Employee;
import main.java.service.EmployeeService;
import main.java.service.ServiceException;

import java.util.List;

/**
 * Controller for Employee operations
 * Delegates all business logic to EmployeeService
 * Responsible only for receiving requests and returning responses
 */
public class EmployeeController extends BaseController<Employee, Integer> {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        super(employeeService);
        this.employeeService = employeeService;
    }

    /**
     * Transfer employee to a new department
     * Task 4 implementation
     * 
     * @param employeeId the employee ID to transfer
     * @param newDepartmentId the new department ID
     * @throws ServiceException if validation fails or transaction fails
     */
    public void transferEmployeeToDepartment(int employeeId, int newDepartmentId) {
        employeeService.transferEmployeeToDepartment(employeeId, newDepartmentId);
    }

    /**
     * Find employees by department
     * @param departmentId the department ID
     * @return list of employees in the department
     * @throws ServiceException if operation fails
     */
    public List<Employee> findByDepartment(int departmentId) {
        return employeeService.findByDepartment(departmentId);
    }

    /**
     * Find employees with salary greater than specified amount
     * @param minSalary the minimum salary
     * @return list of employees with salary greater than minSalary
     * @throws ServiceException if operation fails
     */
    public List<Employee> findBySalaryGreaterThan(double minSalary) {
        return employeeService.findBySalaryGreaterThan(minSalary);
    }

    /**
     * Calculate total salary expense for a department
     * @param departmentId the department ID
     * @return total salary expense
     * @throws ServiceException if operation fails
     */
    public double calculateDepartmentSalaryExpense(int departmentId) {
        return employeeService.calculateDepartmentSalaryExpense(departmentId);
    }

    /**
     * Calculate average salary for a department
     * @param departmentId the department ID
     * @return average salary or 0 if no employees
     * @throws ServiceException if operation fails
     */
    public double calculateAverageSalary(int departmentId) {
        return employeeService.calculateAverageSalary(departmentId);
    }
}

