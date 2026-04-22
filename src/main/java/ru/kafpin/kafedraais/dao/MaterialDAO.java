package ru.kafpin.kafedraais.dao;

import ru.kafpin.kafedraais.database.DatabaseHandler;
import ru.kafpin.kafedraais.models.Material;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO implements DAO<Material> {

    @Override
    public Material get(int id) {
        String query = "SELECT * FROM Materials WHERE MaterialID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Material material = new Material();
                material.setId(rs.getInt("MaterialID"));
                material.setMaterialName(rs.getString("MaterialName"));
                material.setFileData(rs.getBytes("FileData"));
                material.setFileExtension(rs.getString("FileExtension"));
                material.setUploadDate(rs.getTimestamp("UploadDate"));
                
                int courseId = rs.getInt("CourseID");
                if (!rs.wasNull()) material.setCourseId(courseId);
                
                return material;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Material> getAll() {
        List<Material> materials = new ArrayList<>();
        String query = "SELECT * FROM Materials ORDER BY MaterialID";
        try (Statement stmt = DatabaseHandler.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Material material = new Material();
                material.setId(rs.getInt("MaterialID"));
                material.setMaterialName(rs.getString("MaterialName"));
                material.setFileData(rs.getBytes("FileData"));
                material.setFileExtension(rs.getString("FileExtension"));
                material.setUploadDate(rs.getTimestamp("UploadDate"));
                
                int courseId = rs.getInt("CourseID");
                if (!rs.wasNull()) material.setCourseId(courseId);
                
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    @Override
    public void save(Material material) {
        String query = "INSERT INTO Materials (MaterialName, FileData, FileExtension, CourseID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, material.getMaterialName());
            pstmt.setBytes(2, material.getFileData());
            pstmt.setString(3, material.getFileExtension());
            if (material.getCourseId() != null) pstmt.setInt(4, material.getCourseId());
            else pstmt.setNull(4, Types.INTEGER);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Material material) {
        String query = "UPDATE Materials SET MaterialName = ?, FileData = ?, FileExtension = ?, CourseID = ? WHERE MaterialID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, material.getMaterialName());
            pstmt.setBytes(2, material.getFileData());
            pstmt.setString(3, material.getFileExtension());
            if (material.getCourseId() != null) pstmt.setInt(4, material.getCourseId());
            else pstmt.setNull(4, Types.INTEGER);
            
            pstmt.setInt(5, material.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Materials WHERE MaterialID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
