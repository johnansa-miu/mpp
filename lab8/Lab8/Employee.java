public class Employee {
    private int emp_id;
    private String name;
    private int salary;
    private int address_id;
    private int dept_id;

    public Employee() {}

    public Employee(int emp_id, String name, int salary, int address_id, int dept_id) {
        this.emp_id = emp_id;
        this.name = name;
        this.salary = salary;
        this.address_id = address_id;
        this.dept_id = dept_id;
    }

    public int getEmpId() {
        return emp_id;
    }

    public void setEmpId(int emp_id) {
        this.emp_id = emp_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAddressId() {
        return address_id;
    }

    public void setAddressId(int address_id) {
        this.address_id = address_id;
    }

    public int getDeptId() {
        return dept_id;
    }

    public void setDeptId(int dept_id) {
        this.dept_id = dept_id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "emp_id=" + emp_id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", address_id=" + address_id +
                ", dept_id=" + dept_id +
                '}';
    }
}