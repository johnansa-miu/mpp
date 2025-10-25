package main.java.repository;

import main.java.domain.models.Department;
import java.sql.*;

public class DepartmentRepository extends BaseRepository<Department, Integer> {
    @Override
    protected String getTableName() { return "Department"; }
    @Override
    protected String getIdColumn() { return "departmentId"; }

    @Override
    protected Department mapResultSet(ResultSet rs) throws SQLException {
        return new Department(
            rs.getInt("departmentId"),
            rs.getString("name"),
            rs.getString("location"),
            rs.getDouble("annualBudget")
        );
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, Department entity) throws SQLException {
        String sql = "INSERT INTO Department (name, location, annualBudget) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, entity.getName());
        ps.setString(2, entity.getLocation());
        ps.setDouble(3, entity.getAnnualBudget());
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, Department entity) throws SQLException {
        String sql = "UPDATE Department SET name=?, location=?, annualBudget=? WHERE departmentId=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, entity.getName());
        ps.setString(2, entity.getLocation());
        ps.setDouble(3, entity.getAnnualBudget());
        ps.setInt(4, entity.getId());
        return ps;
    }

    @Override
    protected Integer getId(Department entity) {
        return entity.getId();
    }
}
