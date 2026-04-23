package ru.kafpin.kafedraais.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.kafpin.kafedraais.dao.CourseDAO;
import ru.kafpin.kafedraais.dao.SectionDAO;
import ru.kafpin.kafedraais.dao.TeacherDAO;
import ru.kafpin.kafedraais.models.Course;
import ru.kafpin.kafedraais.models.Section;
import ru.kafpin.kafedraais.models.Teacher;

import java.util.List;

public class MainController {

    // --- Sections ---
    @FXML private TableView<Section> sectionsTable;
    @FXML private TableColumn<Section, Integer> sectionIdCol;
    @FXML private TableColumn<Section, String> sectionNameCol;
    @FXML private TableColumn<Section, String> sectionDescCol;

    // --- Teachers ---
    @FXML private TableView<Teacher> teachersTable;
    @FXML private TableColumn<Teacher, Integer> teacherIdCol;
    @FXML private TableColumn<Teacher, String> teacherNameCol;
    @FXML private TableColumn<Teacher, String> teacherPositionCol;
    @FXML private TableColumn<Teacher, String> teacherDegreeCol;

    // --- Courses ---
    @FXML private TableView<Course> coursesTable;
    @FXML private TableColumn<Course, Integer> courseIdCol;
    @FXML private TableColumn<Course, String> courseNameCol;
    @FXML private TableColumn<Course, Integer> courseSemesterCol;

    // --- DAOs ---
    private SectionDAO sectionDAO = new SectionDAO();
    private TeacherDAO teacherDAO = new TeacherDAO();
    private CourseDAO courseDAO = new CourseDAO();

    @FXML
    public void initialize() {
        // Инициализация колонок Секций
        sectionIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        sectionNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        sectionDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Инициализация колонок Преподавателей
        teacherIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        teacherNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        teacherPositionCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        teacherDegreeCol.setCellValueFactory(new PropertyValueFactory<>("academicDegree"));

        // Инициализация колонок Курсов
        courseIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseSemesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));

        loadAllData();
    }

    private void loadAllData() {
        // Секции
        List<Section> sections = sectionDAO.getAll();
        sectionsTable.setItems(FXCollections.observableArrayList(sections));

        // Преподаватели
        List<Teacher> teachers = teacherDAO.getAll();
        teachersTable.setItems(FXCollections.observableArrayList(teachers));

        // Курсы
        List<Course> courses = courseDAO.getAll();
        coursesTable.setItems(FXCollections.observableArrayList(courses));
    }
}
