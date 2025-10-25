package main.java.repository;

import main.java.domain.models.Client;
import java.sql.*;

public class ClientRepository extends BaseRepository<Client, Integer> {
    @Override
    protected String getTableName() { return "Client"; }
    @Override
    protected String getIdColumn() { return "clientId"; }

    @Override
    protected Client mapResultSet(ResultSet rs) throws SQLException {
        return new Client(
            rs.getInt("clientId"),
            rs.getString("name"),
            rs.getString("industry"),
            rs.getString("contactPerson"),
            rs.getString("phone"),
            rs.getString("email")
        );
    }

    @Override
    protected PreparedStatement prepareInsert(Connection conn, Client entity) throws SQLException {
        String sql = "INSERT INTO Client (name, industry, contactPerson, phone, email) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        setClientParams(ps, entity);
        return ps;
    }

    @Override
    protected PreparedStatement prepareUpdate(Connection conn, Client entity) throws SQLException {
        String sql = "UPDATE Client SET name=?, industry=?, contactPerson=?, phone=?, email=? WHERE clientId=?";
        PreparedStatement ps = conn.prepareStatement(sql);
        setClientParams(ps, entity);
        ps.setInt(6, entity.getId());
        return ps;
    }

    /**
     * Helper to set Client parameters for insert/update
     */
    private void setClientParams(PreparedStatement ps, Client entity) throws SQLException {
        ps.setString(1, entity.getName());
        ps.setString(2, entity.getIndustry());
        ps.setString(3, entity.getContactPerson());
        ps.setString(4, entity.getPhone());
        ps.setString(5, entity.getEmail());
    }

    @Override
    protected Integer getId(Client entity) {
        return entity.getId();
    }
}
