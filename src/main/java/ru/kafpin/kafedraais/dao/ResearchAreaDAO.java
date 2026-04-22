package ru.kafpin.kafedraais.dao;

import ru.kafpin.kafedraais.database.DatabaseHandler;
import ru.kafpin.kafedraais.models.ResearchArea;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResearchAreaDAO implements DAO<ResearchArea> {

    @Override
    public ResearchArea get(int id) {
        String query = "SELECT * FROM ResearchAreas WHERE ResearchID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new ResearchArea(
                        rs.getInt("ResearchID"),
                        rs.getString("AreaName"),
                        rs.getString("Description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ResearchArea> getAll() {
        List<ResearchArea> areas = new ArrayList<>();
        String query = "SELECT * FROM ResearchAreas ORDER BY ResearchID";
        try (Statement stmt = DatabaseHandler.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                areas.add(new ResearchArea(
                        rs.getInt("ResearchID"),
                        rs.getString("AreaName"),
                        rs.getString("Description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return areas;
    }

    @Override
    public void save(ResearchArea area) {
        String query = "INSERT INTO ResearchAreas (AreaName, Description) VALUES (?, ?)";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, area.getAreaName());
            pstmt.setString(2, area.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(ResearchArea area) {
        String query = "UPDATE ResearchAreas SET AreaName = ?, Description = ? WHERE ResearchID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, area.getAreaName());
            pstmt.setString(2, area.getDescription());
            pstmt.setInt(3, area.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM ResearchAreas WHERE ResearchID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
