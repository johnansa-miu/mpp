package main.java.controller;

import main.java.domain.enums.ProjectStatus;
import main.java.domain.models.Project;
import main.java.service.ProjectService;
import main.java.service.ServiceException;

import java.util.List;

/**
 * Controller for Project operations
 * Delegates all business logic to ProjectService
 * Responsible only for receiving requests and returning responses
 */
public class ProjectController extends BaseController<Project, Integer> {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        super(projectService);
        this.projectService = projectService;
    }

    /**
     * Calculate projected human resource cost for a project
     * Task 1 implementation
     * 
     * @param projectId the project ID
     * @return the total HR cost for the project
     * @throws ServiceException if project not found or calculation fails
     */
    public double calculateProjectHRCost(int projectId) {
        return projectService.calculateProjectHRCost(projectId);
    }

    /**
     * Find projects ending within specified days from now
     * @param daysFromNow number of days from current date
     * @return list of projects ending within the timeframe
     * @throws ServiceException if operation fails
     */
    public List<Project> findProjectsEndingWithin(int daysFromNow) {
        return projectService.findProjectsEndingWithin(daysFromNow);
    }

    /**
     * Find projects by status
     * @param status the project status
     * @return list of projects with the specified status
     * @throws ServiceException if operation fails
     */
    public List<Project> findByStatus(ProjectStatus status) {
        return projectService.findByStatus(status);
    }

    /**
     * Find projects within budget range
     * @param minBudget minimum budget
     * @param maxBudget maximum budget
     * @return list of projects within the budget range
     * @throws ServiceException if operation fails
     */
    public List<Project> findByBudgetRange(double minBudget, double maxBudget) {
        return projectService.findByBudgetRange(minBudget, maxBudget);
    }
}

