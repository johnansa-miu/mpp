package main.java.repository;

import main.java.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public abstract class JunctionRepository<T, ID> extends BaseRepository<T, ID>{
    protected abstract String getSecondaryIdColumn();

    public boolean deleteByCompositeKey(String id) {
        String[] ids = id.split(":");
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?" + " AND " + getSecondaryIdColumn() + " = ?" ;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(ids[0]));
            ps.setInt(2, Integer.parseInt(ids[1]));
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
