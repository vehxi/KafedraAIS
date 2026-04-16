package ru.kafpin.kafedraais.dao;

import ru.kafpin.kafedraais.database.DatabaseHandler;
import ru.kafpin.kafedraais.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements DAO<User> {

    @Override
    public User get(int id) {
        String query = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPasswordHash(rs.getString("PasswordHash"));
                user.setRole(rs.getString("Role"));
                int teacherId = rs.getInt("TeacherID");
                if (!rs.wasNull()) {
                    user.setTeacherId(teacherId);
                }
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getByUsername(String username) {
        String query = "SELECT * FROM Users WHERE Username = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPasswordHash(rs.getString("PasswordHash"));
                user.setRole(rs.getString("Role"));
                int teacherId = rs.getInt("TeacherID");
                if (!rs.wasNull()) {
                    user.setTeacherId(teacherId);
                }
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users ORDER BY UserID";
        try (Statement stmt = DatabaseHandler.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPasswordHash(rs.getString("PasswordHash"));
                user.setRole(rs.getString("Role"));
                int teacherId = rs.getInt("TeacherID");
                if (!rs.wasNull()) {
                    user.setTeacherId(teacherId);
                }
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User user) {
        String query = "INSERT INTO Users (Username, PasswordHash, Role, TeacherID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getRole());
            if (user.getTeacherId() != null) {
                pstmt.setInt(4, user.getTeacherId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE Users SET Username = ?, PasswordHash = ?, Role = ?, TeacherID = ? WHERE UserID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPasswordHash());
            pstmt.setString(3, user.getRole());
            if (user.getTeacherId() != null) {
                pstmt.setInt(4, user.getTeacherId());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            pstmt.setInt(5, user.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
