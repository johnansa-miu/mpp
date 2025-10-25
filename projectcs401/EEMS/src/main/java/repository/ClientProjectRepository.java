package main.java.repository;

import main.java.domain.models.ClientProject;

import java.sql.*;

public class ClientProjectRepository extends JunctionRepository<ClientProject, String> {
    @Override
    protected String getTableName() { return "ClientProject"; }
    @Override
    protected String getIdColumn() { return "projectId"; } // For composite key, override deleteById
    @Override
    protected String getSecondaryIdColumn() { return "clientId"; }

    @Override
    protected ClientProject mapResultSet(ResultSet rs) throws SQLException {
        return new ClientProject(
            rs.getInt("projectId"),
            rs.getInt("clientId")
        );
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, ClientProject entity) throws SQLException {
        String sql = "INSERT INTO ClientProject (projectId, clientId) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        setClientProjectParams(ps, entity);
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, ClientProject entity) throws SQLException {
        // No updatable fields in ClientProject, so just return a dummy statement
        String sql = "SELECT 1";
        return conn.prepareStatement(sql);
    }

    @Override
    protected String getId(ClientProject entity) {
        return entity.getProjectId() + ":" + entity.getClientId();
    }

    /**
     * Helper to set ClientProject parameters for insert
     */
    private void setClientProjectParams(PreparedStatement ps, ClientProject entity) throws SQLException {
        ps.setInt(1, entity.getProjectId());
        ps.setInt(2, entity.getClientId());
    }
}
