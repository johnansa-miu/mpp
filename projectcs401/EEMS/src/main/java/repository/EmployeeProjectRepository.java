package main.java.repository;

import main.java.domain.models.EmployeeProject;
import java.sql.*;

public class EmployeeProjectRepository extends JunctionRepository<EmployeeProject, String> {
    @Override
    protected String getTableName() { return "EmployeeProject"; }
    @Override
    protected String getIdColumn() { return "employeeId"; } // For composite key, override deleteById
    @Override
    protected String getSecondaryIdColumn() { return "projectId"; }

    @Override
    protected EmployeeProject mapResultSet(ResultSet rs) throws SQLException {
        return new EmployeeProject(
            rs.getInt("employeeId"),
            rs.getInt("projectId"),
            rs.getDouble("allocationPercentage")
        );
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, EmployeeProject entity) throws SQLException {
        String sql = "INSERT INTO EmployeeProject (employeeId, projectId, allocationPercentage) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        setEmployeeProjectParams(ps, entity);
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, EmployeeProject entity) throws SQLException {
        String sql = "UPDATE EmployeeProject SET allocationPercentage=? WHERE employeeId=? AND projectId=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        setEmployeeProjectUpdateParams(ps, entity);
        return ps;
    }

    /**
     * Helper to set EmployeeProject parameters for insert
     */
    private void setEmployeeProjectParams(PreparedStatement ps, EmployeeProject entity) throws SQLException {
        ps.setInt(1, entity.getEmployeeId());
        ps.setInt(2, entity.getProjectId());
        ps.setDouble(3, entity.getAllocationPercentage());
    }

    /**
     * Helper to set EmployeeProject parameters for update
     */
    private void setEmployeeProjectUpdateParams(PreparedStatement ps, EmployeeProject entity) throws SQLException {
        ps.setDouble(1, entity.getAllocationPercentage());
        ps.setInt(2, entity.getEmployeeId());
        ps.setInt(3, entity.getProjectId());
    }

    @Override
    protected String getId(EmployeeProject entity) {
        return entity.getEmployeeId() + ":" + entity.getProjectId();
    }

}
