package main.java.service;

import main.java.domain.enums.ProjectSortParameters;
import main.java.domain.models.Department;
import main.java.domain.models.DepartmentProject;
import main.java.domain.models.Employee;
import main.java.domain.models.Project;
import main.java.domain.enums.ProjectStatus;
import main.java.repository.DepartmentProjectRepository;
import main.java.repository.DepartmentRepository;
import main.java.repository.EmployeeRepository;
import main.java.repository.ProjectRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Department operations
 * Implements business logic for department management
 */
public class DepartmentService extends BaseService<Department, Integer> {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final DepartmentProjectRepository departmentProjectRepository;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            EmployeeRepository employeeRepository,
            ProjectRepository projectRepository,
            DepartmentProjectRepository departmentProjectRepository) {
        super(departmentRepository);
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.departmentProjectRepository = departmentProjectRepository;
    }

    /**
     * Task 2: Get all active projects for a department, sorted by specified field
     *
     * @param departmentId the department ID
     * @param sortBy the field to sort by ("budget", "end_date", "start_date", "name")
     * @return sorted list of active projects
     * @throws ServiceException if invalid sortBy parameter
     */
    public List<Project> getProjectsByDepartment(int departmentId, ProjectSortParameters sortBy) {
        // Get all department-project associations for this department
        List<Integer> projectIds = departmentProjectRepository.findAll().stream()
                .filter(dp -> dp.getDepartmentId() == departmentId)
                .map(DepartmentProject::getProjectId)
                .toList();

        // Get all projects that are active and belong to this department

        return projectRepository.findAll().stream()
                .filter(p -> projectIds.contains(p.getId()))
                .filter(p -> p.getStatus() == ProjectStatus.IN_PROGRESS)
                .sorted(getProjectComparator(sortBy))
                .collect(Collectors.toList());
    }

    /**
     * Helper method to sort projects by specified field
     * @param sortBy the field to sort by
     * @return sorted list of projects
     */
    private Comparator<Project> getProjectComparator(ProjectSortParameters sortBy) {

        return switch (sortBy) {
            case ProjectSortParameters.PROJECT_BUDGET -> Comparator.comparingDouble(Project::getBudget);
            case ProjectSortParameters.END_DATE -> Comparator.comparing(Project::getEndDate,
                    Comparator.nullsLast(Comparator.naturalOrder()));
            case ProjectSortParameters.START_DATE -> Comparator.comparing(Project::getStartDate,
                    Comparator.nullsLast(Comparator.naturalOrder()));
            case ProjectSortParameters.PROJECT_NAME -> Comparator.comparing(Project::getName);
            default -> throw new ServiceException("Invalid sort parameter: " + sortBy);
        };

    }

    /**
     * Find departments by location
     * @param location the location
     * @return list of departments in the specified location
     */
    public List<Department> findByLocation(String location) {
        return departmentRepository.findAll().stream()
                .filter(d -> d.getLocation() != null)
                .filter(d -> d.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
    }

    /**
     * Get employee count for a department
     * @param departmentId the department ID
     * @return number of employees in the department
     */
    public long getEmployeeCount(int departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).size();
    }

    /**
     * Find departments by minimum budget
     * @param minBudget the minimum annual budget
     * @return list of departments with budget >= minBudget
     */
    public List<Department> findByMinimumBudget(double minBudget) {
        return departmentRepository.findAll().stream()
                .filter(d -> d.getAnnualBudget() >= minBudget)
                .collect(Collectors.toList());
    }

    /**
     * Get departments sorted by employee count
     * @param ascending true for ascending order, false for descending
     * @return sorted list of departments
     */
    public List<Department> getDepartmentsByEmployeeCount(boolean ascending) {
        List<Department> departments = departmentRepository.findAll();

        Comparator<Department> comparator = Comparator.comparingLong(d -> getEmployeeCount(d.getId()));

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return departments.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Calculate total salary expense for a department
     * @param departmentId the department ID
     * @return total salary expense
     */
    public double calculateTotalSalaryExpense(int departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    /**
     * Check if department is within budget (total salary < annual budget)
     * @param departmentId the department ID
     * @return true if within budget
     */
    public boolean isWithinBudget(int departmentId) {
        Department department = getByIdOrThrow(departmentId);
        double totalSalary = calculateTotalSalaryExpense(departmentId);
        return totalSalary <= department.getAnnualBudget();
    }

    /**
     * Get departments that are over budget
     * @return list of departments exceeding their annual budget
     */
    public List<Department> getDepartmentsOverBudget() {
        return departmentRepository.findAll().stream()
                .filter(d -> !isWithinBudget(d.getId()))
                .collect(Collectors.toList());
    }


    /**
     * Calculate total budget for all projects in a department
     * @param departmentId the department ID
     * @return total budget
     */
    public double calculateDepartmentProjectBudget(int departmentId) {
        return getProjectsByDepartment(departmentId, ProjectSortParameters.PROJECT_NAME).stream()
                .mapToDouble(Project::getBudget)
                .sum();
    }
}

