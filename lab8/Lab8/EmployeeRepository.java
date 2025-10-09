import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private static final String URL = "jdbc:postgresql://localhost:5432/miu_mpp";
    private static final String USER = "admin@roofapp.com.ng";
    private static final String PASSWORD = "xWGqtYouZUZabFhjCntExsjhfSdpih";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT emp_id, name, salary, address_id, dept_id FROM employee";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee e = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getInt("salary"),
                        rs.getInt("address_id"),
                        rs.getInt("dept_id")
                );
                employees.add(e);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    public Employee findById(int empId) {
        String sql = "SELECT emp_id, name, salary, address_id, dept_id FROM Employee WHERE emp_id = ?";
        Employee employee = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, empId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getInt("salary"),
                        rs.getInt("address_id"),
                        rs.getInt("dept_id")
                );
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employee;
    }

    public void create(Employee employee) {
        String sql = "INSERT INTO Employee (emp_id, name, salary, address_id, dept_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employee.getEmpId());
            stmt.setString(2, employee.getName());
            stmt.setInt(3, employee.getSalary());
            stmt.setObject(4, employee.getAddressId());
            stmt.setObject(5, employee.getDeptId());
            stmt.executeUpdate();

            System.out.println("Employee created successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Employee employee) {
        String sql = "UPDATE Employee SET name = ?, salary = ?, address_id = ?, dept_id = ? WHERE emp_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employee.getName());
            stmt.setInt(2, employee.getSalary());
            stmt.setObject(3, employee.getAddressId());
            stmt.setObject(4, employee.getDeptId());
            stmt.setInt(5, employee.getEmpId());
            stmt.executeUpdate();

            System.out.println("Employee updated successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(int empId) {
        String sql = "DELETE FROM Employee WHERE emp_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, empId);
            stmt.executeUpdate();

            System.out.println("Employee deleted successfully.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        EmployeeRepository repo = new EmployeeRepository();
        int emp_id = 116;

        System.out.println("1. Create new employee:");
        Employee emp = new Employee(emp_id, "John Ansa", 500000, 1, 1);
        repo.create(emp);

        System.out.println("2. All Employees:");
        repo.findAll().forEach(System.out::println);

        System.out.println("3. Find Employee with ID 116:");
        System.out.println(repo.findById(emp_id));

        System.out.println("4. Update employee:");
        emp.setSalary(130000);
        repo.update(emp);

        System.out.println("5. After Update:");
        System.out.println(repo.findById(emp_id));

        System.out.println("5. Delete employee:");
        repo.delete(emp_id);

        System.out.println("All Employees after deletion:");
        repo.findAll().forEach(System.out::println);
    }
}