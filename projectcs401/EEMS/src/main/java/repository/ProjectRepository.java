package main.java.repository;

import main.java.domain.models.Project;
import main.java.domain.enums.ProjectStatus;

import java.sql.*;

public class ProjectRepository extends BaseRepository<Project, Integer> {
    @Override
    protected String getTableName() { return "Project"; }
    @Override
    protected String getIdColumn() { return "projectId"; }

    @Override
    protected Project mapResultSet(ResultSet rs) throws SQLException {
        Project project = new Project(
            rs.getInt("projectId"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getDate("startDate").toLocalDate(),
            rs.getDate("endDate") != null ? rs.getDate("endDate").toLocalDate() : null,
            rs.getDouble("budget")
        );
        project.setStatus(ProjectStatus.valueOf(rs.getString("status")));
        return project;
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, Project entity) throws SQLException {
        String sql = "INSERT INTO Project (name, description, startDate, endDate, budget, status) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        setProjectParams(ps, entity);
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, Project entity) throws SQLException {
        String sql = "UPDATE Project SET name=?, description=?, startDate=?, endDate=?, budget=?, status=? WHERE projectId=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        setProjectParams(ps, entity);
        ps.setInt(7, entity.getId());
        return ps;
    }

    /**
     * Helper to set Project parameters for insert/update
     */
    private void setProjectParams(PreparedStatement ps, Project entity) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setString(2, entity.getDescription());
        ps.setDate(3, Date.valueOf(entity.getStartDate()));
        if (entity.getEndDate() != null) {
            ps.setDate(4, Date.valueOf(entity.getEndDate()));
        } else {
            ps.setNull(4, Types.DATE);
        }
        ps.setDouble(5, entity.getBudget());
        ps.setString(6, entity.getStatus().name());
    }

    @Override
    protected Integer getId(Project entity) {
        return entity.getId();
    }
}
