package main.java.domain.models;


import java.time.LocalDate;
import java.util.List;

public class Employee extends Entity {
    private String fullName;
    private String title;
    private LocalDate hireDate;
    private double salary;
    private int departmentId;

    private Department department;
    private List<EmployeeProject> employeeProjects;


    public Employee(int employeeId, String fullName, String title, LocalDate hireDate, double salary, int departmentId) {
        super(employeeId);
        this.fullName = fullName;
        this.title = title;
        this.hireDate = hireDate;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    public List<EmployeeProject> getEmployeeProjects() { return employeeProjects; }
    public void setEmployeeProjects(List<EmployeeProject> employeeProjects) { this.employeeProjects = employeeProjects; }

    @Override
    public String toString() {
        return "Employee [ID=" + getId() + ", Name=" + fullName + ", Title=" + title +
                ", HireDate=" + hireDate + ", Salary=" + salary +
                ", DepartmentID=" + departmentId +
                ", Projects=" + (employeeProjects != null ? employeeProjects.size() : 0) + "]";
    }
}

