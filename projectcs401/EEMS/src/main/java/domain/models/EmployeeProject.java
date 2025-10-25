package main.java.domain.models;

public class EmployeeProject {
    private int employeeId;
    private int projectId;
    private double allocationPercentage;

    private Employee employee;
    private Project project;

    public EmployeeProject(int employeeId, int projectId, double allocationPercentage) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.allocationPercentage = allocationPercentage;
    }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public double getAllocationPercentage() { return allocationPercentage; }
    public void setAllocationPercentage(double allocationPercentage) { this.allocationPercentage = allocationPercentage; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    @Override
    public String toString() {
        return "EmployeeProject [EmployeeID=" + employeeId +
                ", ProjectID=" + projectId +
                ", Allocation=" + allocationPercentage + "%]";
    }
}

