package main.java.controller;

import main.java.domain.enums.ProjectSortParameters;
import main.java.domain.models.Department;
import main.java.domain.models.Project;
import main.java.service.DepartmentService;
import main.java.service.ServiceException;

import java.util.List;

/**
 * Controller for Department operations
 * Delegates all business logic to DepartmentService
 * Responsible only for receiving requests and returning responses
 */
public class DepartmentController extends BaseController<Department, Integer> {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        super(departmentService);
        this.departmentService = departmentService;
    }

    /**
     * Get all active projects for a department, sorted by specified field
     * Task 2 implementation
     * 
     * @param departmentId the department ID
     * @param sortBy the field to sort by
     * @return sorted list of active projects
     * @throws ServiceException if department not found or invalid sort parameter
     */
    public List<Project> getProjectsByDepartment(int departmentId, ProjectSortParameters sortBy) {
        return departmentService.getProjectsByDepartment(departmentId, sortBy);
    }

    /**
     * Find departments by location
     * @param location the location
     * @return list of departments in the specified location
     * @throws ServiceException if operation fails
     */
    public List<Department> findByLocation(String location) {
        return departmentService.findByLocation(location);
    }

    /**
     * Get employee count for a department
     * @param departmentId the department ID
     * @return number of employees in the department
     * @throws ServiceException if operation fails
     */
    public long getEmployeeCount(int departmentId) {
        return departmentService.getEmployeeCount(departmentId);
    }

    /**
     * Find departments by minimum budget
     * @param minBudget the minimum annual budget
     * @return list of departments with budget >= minBudget
     * @throws ServiceException if operation fails
     */
    public List<Department> findByMinimumBudget(double minBudget) {
        return departmentService.findByMinimumBudget(minBudget);
    }

    /**
     * Get departments sorted by employee count
     * @param ascending true for ascending order, false for descending
     * @return sorted list of departments
     * @throws ServiceException if operation fails
     */
    public List<Department> getDepartmentsByEmployeeCount(boolean ascending) {
        return departmentService.getDepartmentsByEmployeeCount(ascending);
    }

    /**
     * Calculate total salary expense for a department
     * @param departmentId the department ID
     * @return total salary expense
     * @throws ServiceException if operation fails
     */
    public double calculateTotalSalaryExpense(int departmentId) {
        return departmentService.calculateTotalSalaryExpense(departmentId);
    }

    /**
     * Check if department is within budget
     * @param departmentId the department ID
     * @return true if within budget
     * @throws ServiceException if department not found or operation fails
     */
    public boolean isWithinBudget(int departmentId) {
        return departmentService.isWithinBudget(departmentId);
    }

    /**
     * Get departments that are over budget
     * @return list of departments exceeding their annual budget
     * @throws ServiceException if operation fails
     */
    public List<Department> getDepartmentsOverBudget() {
        return departmentService.getDepartmentsOverBudget();
    }

    /**
     * Calculate total budget for all projects in a department
     * @param departmentId the department ID
     * @return total project budget
     * @throws ServiceException if operation fails
     */
    public double calculateDepartmentProjectBudget(int departmentId) {
        return departmentService.calculateDepartmentProjectBudget(departmentId);
    }
}

