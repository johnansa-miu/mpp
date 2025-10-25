package main.java.repository;

import main.java.domain.models.Employee;
import main.java.util.DBConnection;

import java.io.IOException;
import java.sql.*;

public class EmployeeRepository extends BaseRepository<Employee, Integer> {
    @Override
    protected String getTableName() { return "Employee"; }
    @Override
    protected String getIdColumn() { return "employeeId"; }

    @Override
    protected Employee mapResultSet(ResultSet rs) throws SQLException {
        return new Employee(
            rs.getInt("employeeId"),
            rs.getString("fullName"),
            rs.getString("title"),
            rs.getDate("hireDate").toLocalDate(),
            rs.getDouble("salary"),
            rs.getInt("departmentId")
        );
    }


    public java.util.List<Employee> findByDepartmentId(int departmentId) {
        return findAll().stream()
                .filter(e -> e.getDepartmentId() == departmentId)
                .collect(java.util.stream.Collectors.toList());
    }

    public java.util.List<Employee> findBySalaryGreaterThan(double salary) {
        return findAll().stream()
                .filter(e -> e.getSalary() > salary)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, Employee entity) throws SQLException {
        String sql = "INSERT INTO Employee (fullName, title, hireDate, salary, departmentId) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        setEmployeeParams(ps, entity);
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, Employee entity) throws SQLException {
        String sql = "UPDATE Employee SET fullName=?, title=?, hireDate=?, salary=?, departmentId=? WHERE employeeId=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        setEmployeeParams(ps, entity);
        ps.setInt(6, entity.getId());
        return ps;
    }

    /**
     * Helper to set Employee parameters for insert/update
     */
    private void setEmployeeParams(PreparedStatement ps, Employee entity) throws SQLException {
        ps.setString(1, entity.getFullName());
        ps.setString(2, entity.getTitle());
        ps.setDate(3, Date.valueOf(entity.getHireDate()));
        ps.setDouble(4, entity.getSalary());
        ps.setInt(5, entity.getDepartmentId());
    }

    @Override
    protected Integer getId(Employee entity) {
        return entity.getId();
    }

    /**
     * Update employee's department with transaction support
     * This method handles the transaction logic for transferring an employee to a new department
     *
     * @param employeeId the employee ID
     * @param newDepartmentId the new department ID
     * @throws RuntimeException if transaction fails
     */
    public void updateEmployeeDepartment(int employeeId, int newDepartmentId) {
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String sql = "UPDATE Employee SET departmentId = ? WHERE employeeId = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, newDepartmentId);
                ps.setInt(2, employeeId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Employee update failed, no rows affected");
                }
            }
            conn.commit();

        } catch (SQLException e) {
            DBConnection.rollback(conn);

            throw new RuntimeException("Failed to update employee department", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    DBConnection.closeConnection(conn);
                } catch (SQLException e) {
                    System.err.println("Failed to close connection: " + e.getMessage());
                }
            }
        }
    }
}
