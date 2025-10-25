package main.java.domain.models;

import java.util.List;

public class Department extends Entity {
    private String name;
    private String location;
    private double annualBudget;

    private List<Employee> employees;
    private List<DepartmentProject> departmentProjects;

    public Department(int departmentId, String name, String location, double annualBudget) {
        super(departmentId);
        this.name = name;
        this.location = location;
        this.annualBudget = annualBudget;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public double getAnnualBudget() {
        return annualBudget;
    }
    public void setAnnualBudget(double annualBudget) {
        this.annualBudget = annualBudget;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<DepartmentProject> getDepartmentProjects() {
        return departmentProjects;
    }
    public void setDepartmentProjects(List<DepartmentProject> departmentProjects) {
        this.departmentProjects = departmentProjects;
    }

    @Override
    public String toString() {
        return "Department [ID=" + getId() + ", Name=" + name + ", Location=" + location +
                ", AnnualBudget=" + annualBudget + ", Employees=" + (employees != null ? employees.size() : 0) +
                ", Projects=" + (departmentProjects != null ? departmentProjects.size() : 0) + "]";
    }
}
