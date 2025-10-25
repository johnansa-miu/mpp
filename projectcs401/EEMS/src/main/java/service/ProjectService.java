package main.java.service;

import main.java.domain.enums.ProjectStatus;
import main.java.domain.models.Employee;
import main.java.domain.models.EmployeeProject;
import main.java.domain.models.Project;
import main.java.repository.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for Project operations
 * Implements business logic for project management including cost calculations
 */
public class ProjectService extends BaseService<Project, Integer> {
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeProjectRepository employeeProjectRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            EmployeeRepository employeeRepository,
            EmployeeProjectRepository employeeProjectRepository) {
        super(projectRepository);
        this.projectRepository = projectRepository;
        this.employeeRepository = employeeRepository;
        this.employeeProjectRepository = employeeProjectRepository;
    }

    /**
     * Task 1: Calculate projected human resource cost for a project
     * Formula: Sum of (Monthly Salary × Allocation % × Project Duration in Months) for all employees
     * 
     * @param projectId the project ID
     * @return the total HR cost for the project
     * @throws ServiceException if project not found
     */
    public double calculateProjectHRCost(int projectId) {
        // Validate project exists
        Project project = getByIdOrThrow(projectId);

        // Validate project dates
        if (project.getStartDate() == null || project.getEndDate() == null) {
            throw new ServiceException("Project must have both start and end dates for cost calculation");
        }

        if (project.getEndDate().isBefore(project.getStartDate())) {
            throw new ServiceException("Project end date cannot be before start date");
        }

        // Calculate project duration in months (rounded up)
        double durationInMonths = calculateProjectDurationInMonths(
                project.getStartDate(), 
                project.getEndDate()
        );

        // Get all employee-project assignments for this project
        List<EmployeeProject> employeeProjects = employeeProjectRepository.findAll().stream()
                .filter(ep -> ep.getProjectId() == projectId)
                .toList();

        // Calculate total HR cost using Stream API
        return employeeProjects.stream()
                .mapToDouble(ep -> calculateEmployeeCost(ep, durationInMonths))
                .sum();
    }

    /**
     * Helper method to calculate cost for a single employee on a project
     * @param employeeProject the employee-project assignment
     * @param durationInMonths the project duration in months
     * @return the cost for this employee
     */
    private double calculateEmployeeCost(EmployeeProject employeeProject, double durationInMonths) {
        // Get employee details
        Employee employee = employeeRepository.findById(employeeProject.getEmployeeId())
                .orElse(null);

        // Calculate monthly salary
        double salary = employee == null ? 0.0 : employee.getSalary();
        double monthlySalary =  salary / 12.0;

        // Calculate allocation percentage (convert from 0-100 to 0-1)
        double allocationFactor = employeeProject.getAllocationPercentage() / 100.0;

        // Cost = Monthly Salary × Allocation % × Duration in Months
        return monthlySalary * allocationFactor * durationInMonths;
    }

    /**
     * Calculate project duration in months, rounded up to next full month
     * @param startDate project start date
     * @param endDate project end date
     * @return duration in months (rounded up)
     */
    private double calculateProjectDurationInMonths(LocalDate startDate, LocalDate endDate) {
        long months = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1));

        if (endDate.getDayOfMonth() > 1 || months == 0) {
            months = months + 1;
        }

        return months;
    }


    /**
     * Find projects ending within specified days from now
     * @param daysFromNow number of days from current date
     * @return list of projects ending within the timeframe
     */
    public List<Project> findProjectsEndingWithin(int daysFromNow) {
        LocalDate currentDate = LocalDate.now();
        LocalDate targetDate = currentDate.plusDays(daysFromNow);

        return projectRepository.findAll().stream()
                .filter(p -> p.getEndDate() != null)
                .filter(p -> !p.getEndDate().isBefore(currentDate))
                .filter(p -> !p.getEndDate().isAfter(targetDate))
                .collect(Collectors.toList());
    }

    /**
     * Find projects by status
     * @param status the project status
     * @return list of projects with the specified status
     */
    public List<Project> findByStatus(ProjectStatus status) {
        return projectRepository.findAll().stream()
                .filter(p -> p.getStatus() == status)
                .collect(Collectors.toList());
    }

    /**
     * Find projects within budget range
     * @param minBudget minimum budget
     * @param maxBudget maximum budget
     * @return list of projects within the budget range
     */
    public List<Project> findByBudgetRange(double minBudget, double maxBudget) {
        return projectRepository.findAll().stream()
                .filter(p -> p.getBudget() >= minBudget && p.getBudget() <= maxBudget)
                .collect(Collectors.toList());
    }
}

