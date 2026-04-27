package ru.kafpin.kafedraais.dao;

import ru.kafpin.kafedraais.database.DatabaseHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StatsDAO {
    
    // Возвращает массив из 3 элементов:
    // [0] - TotalTeachers
    // [1] - TotalCourses
    // [2] - TotalMaterials
    public int[] getDepartmentStats() {
        int[] stats = new int[3];
        String query = "SELECT * FROM get_department_stats()";
        try (Statement stmt = DatabaseHandler.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                stats[0] = rs.getInt("TotalTeachers");
                stats[1] = rs.getInt("TotalCourses");
                stats[2] = rs.getInt("TotalMaterials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stats;
    }
}
