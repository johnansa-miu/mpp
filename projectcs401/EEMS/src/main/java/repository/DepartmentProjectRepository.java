package main.java.repository;

import main.java.domain.models.DepartmentProject;

import java.sql.*;

public class DepartmentProjectRepository extends JunctionRepository<DepartmentProject, String> {
    @Override
    protected String getTableName() { return "DepartmentProject"; }
    @Override
    protected String getIdColumn() { return "departmentId"; } // For composite key, override deleteById
    @Override
    protected String getSecondaryIdColumn() { return "projectId"; }

    @Override
    protected DepartmentProject mapResultSet(ResultSet rs) throws SQLException {
        return new DepartmentProject(
            rs.getInt("departmentId"),
            rs.getInt("projectId"),
            rs.getString("responsibility")
        );
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, DepartmentProject entity) throws SQLException {
        String sql = "INSERT INTO DepartmentProject (departmentId, projectId, responsibility) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        setDepartmentProjectParams(ps, entity);
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, DepartmentProject entity) throws SQLException {
        String sql = "UPDATE DepartmentProject SET responsibility=? WHERE departmentId=? AND projectId=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        setDepartmentProjectUpdateParams(ps, entity);
        return ps;
    }

    /**
     * Helper to set DepartmentProject parameters for insert
     */
    private void setDepartmentProjectParams(PreparedStatement ps, DepartmentProject entity) throws SQLException {
        ps.setInt(1, entity.getDepartmentId());
        ps.setInt(2, entity.getProjectId());
        ps.setString(3, entity.getResponsibility());
    }

    /**
     * Helper to set DepartmentProject parameters for update
     */
    private void setDepartmentProjectUpdateParams(PreparedStatement ps, DepartmentProject entity) throws SQLException {
        ps.setString(1, entity.getResponsibility());
        ps.setInt(2, entity.getDepartmentId());
        ps.setInt(3, entity.getProjectId());
    }

    @Override
    protected String getId(DepartmentProject entity) {
        return entity.getDepartmentId() + ":" + entity.getProjectId();
    }

}
