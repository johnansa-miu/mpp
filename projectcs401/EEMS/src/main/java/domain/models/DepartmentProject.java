package main.java.domain.models;

public class DepartmentProject {
    private int departmentId;
    private int projectId;
    private String responsibility;

    private Department department;
    private Project project;

    public DepartmentProject(int departmentId, int projectId, String responsibility) {
        this.departmentId = departmentId;
        this.projectId = projectId;
        this.responsibility = responsibility;
    }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public String getResponsibility() { return responsibility; }
    public void setResponsibility(String responsibility) { this.responsibility = responsibility; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    @Override
    public String toString() {
        return "DepartmentProject [DepartmentID=" + departmentId +
                ", ProjectID=" + projectId +
                ", Responsibility=" + responsibility + "]";
    }
}


