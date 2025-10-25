package main.java.domain.models;

import main.java.domain.enums.ProjectStatus;

import java.time.LocalDate;
import java.util.List;

public class Project extends Entity {
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private double budget;
    private ProjectStatus status;

    private List<EmployeeProject> employeeProjects;
    private List<DepartmentProject> departmentProjects;
    private List<ClientProject> clientProjects;

    public Project(int projectId, String name, String description, LocalDate startDate, LocalDate endDate, double budget) {
        super(projectId);
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.status = ProjectStatus.NOT_STARTED;
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public ProjectStatus getStatus() { return status; }
    public void setStatus(ProjectStatus status) { this.status = status; }

    public List<EmployeeProject> getEmployeeProjects() { return employeeProjects; }
    public void setEmployeeProjects(List<EmployeeProject> employeeProjects) { this.employeeProjects = employeeProjects; }

    public List<DepartmentProject> getDepartmentProjects() { return departmentProjects; }
    public void setDepartmentProjects(List<DepartmentProject> departmentProjects) { this.departmentProjects = departmentProjects; }

    public List<ClientProject> getClientProjects() { return clientProjects; }
    public void setClientProjects(List<ClientProject> clientProjects) { this.clientProjects = clientProjects; }

    @Override
    public String toString() {
        return "Project [ID=" + getId() + ", Name=" + name + ", Start=" + startDate + ", End=" + endDate +
                ", Budget=" + budget + ", Status=" + status +
                ", Employees=" + (employeeProjects != null ? employeeProjects.size() : 0) +
                ", Departments=" + (departmentProjects != null ? departmentProjects.size() : 0) +
                ", Clients=" + (clientProjects != null ? clientProjects.size() : 0) + "]";
    }
}

