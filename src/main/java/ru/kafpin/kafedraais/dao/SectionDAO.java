package ru.kafpin.kafedraais.dao;

import ru.kafpin.kafedraais.database.DatabaseHandler;
import ru.kafpin.kafedraais.models.Section;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO implements DAO<Section> {

    @Override
    public Section get(int id) {
        String query = "SELECT * FROM Sections WHERE SectionID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Section(
                        rs.getInt("SectionID"),
                        rs.getString("SectionName"),
                        rs.getString("Description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Section> getAll() {
        List<Section> sections = new ArrayList<>();
        String query = "SELECT * FROM Sections ORDER BY SectionID";
        try (Statement stmt = DatabaseHandler.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                sections.add(new Section(
                        rs.getInt("SectionID"),
                        rs.getString("SectionName"),
                        rs.getString("Description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sections;
    }

    @Override
    public void save(Section section) {
        String query = "INSERT INTO Sections (SectionName, Description) VALUES (?, ?)";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, section.getName());
            pstmt.setString(2, section.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Section section) {
        String query = "UPDATE Sections SET SectionName = ?, Description = ? WHERE SectionID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, section.getName());
            pstmt.setString(2, section.getDescription());
            pstmt.setInt(3, section.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Sections WHERE SectionID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
