package ru.kafpin.kafedraais.dao;

import ru.kafpin.kafedraais.database.DatabaseHandler;
import ru.kafpin.kafedraais.models.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO implements DAO<Course> {

    @Override
    public Course get(int id) {
        String query = "SELECT * FROM Courses WHERE CourseID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("CourseID"));
                course.setCourseName(rs.getString("CourseName"));
                course.setDescription(rs.getString("Description"));
                
                int semester = rs.getInt("Semester");
                if (!rs.wasNull()) course.setSemester(semester);
                
                int teacherId = rs.getInt("TeacherID");
                if (!rs.wasNull()) course.setTeacherId(teacherId);
                
                return course;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM Courses ORDER BY CourseID";
        try (Statement stmt = DatabaseHandler.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("CourseID"));
                course.setCourseName(rs.getString("CourseName"));
                course.setDescription(rs.getString("Description"));
                
                int semester = rs.getInt("Semester");
                if (!rs.wasNull()) course.setSemester(semester);
                
                int teacherId = rs.getInt("TeacherID");
                if (!rs.wasNull()) course.setTeacherId(teacherId);
                
                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public void save(Course course) {
        String query = "INSERT INTO Courses (CourseName, Description, Semester, TeacherID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getDescription());
            if (course.getSemester() != null) pstmt.setInt(3, course.getSemester());
            else pstmt.setNull(3, Types.INTEGER);
            
            if (course.getTeacherId() != null) pstmt.setInt(4, course.getTeacherId());
            else pstmt.setNull(4, Types.INTEGER);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Course course) {
        String query = "UPDATE Courses SET CourseName = ?, Description = ?, Semester = ?, TeacherID = ? WHERE CourseID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, course.getCourseName());
            pstmt.setString(2, course.getDescription());
            if (course.getSemester() != null) pstmt.setInt(3, course.getSemester());
            else pstmt.setNull(3, Types.INTEGER);
            
            if (course.getTeacherId() != null) pstmt.setInt(4, course.getTeacherId());
            else pstmt.setNull(4, Types.INTEGER);
            
            pstmt.setInt(5, course.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Courses WHERE CourseID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
