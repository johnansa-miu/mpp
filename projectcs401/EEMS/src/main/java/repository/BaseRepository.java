package main.java.repository;

import main.java.util.DBConnection;
import java.sql.*;
import java.util.*;

/**
 * Abstract base repository for CRUD operations using JDBC and Java Streams.
 * @param <T> Entity type
 * @param <ID> ID type
 */
public abstract class BaseRepository<T, ID> implements Repository<T, ID> {
    protected abstract String getTableName();
    protected abstract String getIdColumn();
    protected abstract T mapResultSet(ResultSet rs) throws SQLException;
    protected abstract PreparedStatement prepareInsert(Connection conn, T entity) throws SQLException;
    protected abstract PreparedStatement prepareUpdate(Connection conn, T entity) throws SQLException;
    protected abstract ID getId(T entity);

    @Override
    public T save(T entity) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = prepareInsert(conn, entity)) {
            ps.executeUpdate();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSet(rs));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        String sql = "SELECT * FROM " + getTableName();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
            return new ArrayList<>(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T update(T entity) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = prepareUpdate(conn, entity)) {
            ps.executeUpdate();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteById(ID id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE " + getIdColumn() + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setObject(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
