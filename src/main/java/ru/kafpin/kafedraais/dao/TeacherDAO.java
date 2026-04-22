package ru.kafpin.kafedraais.dao;

import ru.kafpin.kafedraais.database.DatabaseHandler;
import ru.kafpin.kafedraais.models.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO implements DAO<Teacher> {

    @Override
    public Teacher get(int id) {
        String query = "SELECT * FROM Teachers WHERE TeacherID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getInt("TeacherID"));
                teacher.setFullName(rs.getString("FullName"));
                teacher.setPosition(rs.getString("Position"));
                teacher.setAcademicDegree(rs.getString("AcademicDegree"));
                teacher.setScientificInterests(rs.getString("ScientificInterests"));
                teacher.setConsultationSchedule(rs.getString("ConsultationSchedule"));
                teacher.setPhoto(rs.getBytes("Photo"));
                teacher.setEmail(rs.getString("Email"));
                int sectionId = rs.getInt("SectionID");
                if (!rs.wasNull()) {
                    teacher.setSectionId(sectionId);
                }
                return teacher;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Teacher> getAll() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "SELECT * FROM Teachers ORDER BY TeacherID";
        try (Statement stmt = DatabaseHandler.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getInt("TeacherID"));
                teacher.setFullName(rs.getString("FullName"));
                teacher.setPosition(rs.getString("Position"));
                teacher.setAcademicDegree(rs.getString("AcademicDegree"));
                teacher.setScientificInterests(rs.getString("ScientificInterests"));
                teacher.setConsultationSchedule(rs.getString("ConsultationSchedule"));
                teacher.setPhoto(rs.getBytes("Photo"));
                teacher.setEmail(rs.getString("Email"));
                int sectionId = rs.getInt("SectionID");
                if (!rs.wasNull()) {
                    teacher.setSectionId(sectionId);
                }
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    @Override
    public void save(Teacher teacher) {
        String query = "INSERT INTO Teachers (FullName, Position, AcademicDegree, ScientificInterests, ConsultationSchedule, Photo, Email, SectionID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, teacher.getFullName());
            pstmt.setString(2, teacher.getPosition());
            pstmt.setString(3, teacher.getAcademicDegree());
            pstmt.setString(4, teacher.getScientificInterests());
            pstmt.setString(5, teacher.getConsultationSchedule());
            pstmt.setBytes(6, teacher.getPhoto());
            pstmt.setString(7, teacher.getEmail());
            if (teacher.getSectionId() != null) {
                pstmt.setInt(8, teacher.getSectionId());
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Teacher teacher) {
        String query = "UPDATE Teachers SET FullName = ?, Position = ?, AcademicDegree = ?, ScientificInterests = ?, ConsultationSchedule = ?, Photo = ?, Email = ?, SectionID = ? WHERE TeacherID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setString(1, teacher.getFullName());
            pstmt.setString(2, teacher.getPosition());
            pstmt.setString(3, teacher.getAcademicDegree());
            pstmt.setString(4, teacher.getScientificInterests());
            pstmt.setString(5, teacher.getConsultationSchedule());
            pstmt.setBytes(6, teacher.getPhoto());
            pstmt.setString(7, teacher.getEmail());
            if (teacher.getSectionId() != null) {
                pstmt.setInt(8, teacher.getSectionId());
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }
            pstmt.setInt(9, teacher.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM Teachers WHERE TeacherID = ?";
        try (PreparedStatement pstmt = DatabaseHandler.getConnection().prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
