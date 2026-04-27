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
    private ru.kafpin.kafedraais.dao.StatsDAO statsDAO = new ru.kafpin.kafedraais.dao.StatsDAO();

    // --- Statistics ---
    @FXML private javafx.scene.control.Label statsTeachersLabel;
    @FXML private javafx.scene.control.Label statsCoursesLabel;
    @FXML private javafx.scene.control.Label statsMaterialsLabel;

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
        handleRefreshStats();
    }

    @FXML
    private void handleRefreshStats() {
        int[] stats = statsDAO.getDepartmentStats();
        if (statsTeachersLabel != null) statsTeachersLabel.setText(String.valueOf(stats[0]));
        if (statsCoursesLabel != null) statsCoursesLabel.setText(String.valueOf(stats[1]));
        if (statsMaterialsLabel != null) statsMaterialsLabel.setText(String.valueOf(stats[2]));
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

    @FXML
    private void handleAddSection() {
        Section tempSection = new Section();
        boolean okClicked = showSectionEditDialog(tempSection);
        if (okClicked) {
            sectionDAO.save(tempSection);
            loadAllData(); // Обновляем таблицу
        }
    }

    @FXML
    private void handleEditSection() {
        Section selectedSection = sectionsTable.getSelectionModel().getSelectedItem();
        if (selectedSection != null) {
            boolean okClicked = showSectionEditDialog(selectedSection);
            if (okClicked) {
                sectionDAO.update(selectedSection);
                loadAllData();
            }
        } else {
            // Ничего не выбрано
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Секция не выбрана");
            alert.setContentText("Пожалуйста, выберите секцию в таблице.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteSection() {
        int selectedIndex = sectionsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Section selectedSection = sectionsTable.getItems().get(selectedIndex);
            sectionDAO.delete(selectedSection.getId());
            sectionsTable.getItems().remove(selectedIndex);
        } else {
            // Ничего не выбрано
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Секция не выбрана");
            alert.setContentText("Пожалуйста, выберите секцию в таблице.");
            alert.showAndWait();
        }
    }

    private boolean showSectionEditDialog(Section section) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(ru.kafpin.kafedraais.HelloApplication.class.getResource("section-form-view.fxml"));
            javafx.scene.layout.VBox page = loader.load();

            javafx.stage.Stage dialogStage = new javafx.stage.Stage();
            dialogStage.setTitle("Редактирование секции");
            dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            // dialogStage.initOwner(...); // Если нужно привязать к главному окну
            javafx.scene.Scene scene = new javafx.scene.Scene(page);
            dialogStage.setScene(scene);

            SectionFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setSection(section);

            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Управление преподавателями ---

    @FXML
    private void handleAddTeacher() {
        Teacher tempTeacher = new Teacher();
        boolean okClicked = showTeacherEditDialog(tempTeacher);
        if (okClicked) {
            teacherDAO.save(tempTeacher);
            loadAllData();
        }
    }

    @FXML
    private void handleEditTeacher() {
        Teacher selectedTeacher = teachersTable.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            boolean okClicked = showTeacherEditDialog(selectedTeacher);
            if (okClicked) {
                teacherDAO.update(selectedTeacher);
                loadAllData();
            }
        } else {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Преподаватель не выбран");
            alert.setContentText("Пожалуйста, выберите преподавателя в таблице.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteTeacher() {
        int selectedIndex = teachersTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Teacher selectedTeacher = teachersTable.getItems().get(selectedIndex);
            teacherDAO.delete(selectedTeacher.getId());
            teachersTable.getItems().remove(selectedIndex);
        } else {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Преподаватель не выбран");
            alert.setContentText("Пожалуйста, выберите преподавателя в таблице.");
            alert.showAndWait();
        }
    }

    private boolean showTeacherEditDialog(Teacher teacher) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(ru.kafpin.kafedraais.HelloApplication.class.getResource("teacher-form-view.fxml"));
            javafx.scene.layout.VBox page = loader.load();

            javafx.stage.Stage dialogStage = new javafx.stage.Stage();
            dialogStage.setTitle("Редактирование преподавателя");
            dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            javafx.scene.Scene scene = new javafx.scene.Scene(page);
            dialogStage.setScene(scene);

            TeacherFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setTeacher(teacher);

            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Управление курсами (дисциплинами) ---

    @FXML
    private void handleAddCourse() {
        Course tempCourse = new Course();
        boolean okClicked = showCourseEditDialog(tempCourse);
        if (okClicked) {
            courseDAO.save(tempCourse);
            loadAllData();
        }
    }

    @FXML
    private void handleEditCourse() {
        Course selectedCourse = coursesTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            boolean okClicked = showCourseEditDialog(selectedCourse);
            if (okClicked) {
                courseDAO.update(selectedCourse);
                loadAllData();
            }
        } else {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Дисциплина не выбрана");
            alert.setContentText("Пожалуйста, выберите дисциплину в таблице.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeleteCourse() {
        int selectedIndex = coursesTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Course selectedCourse = coursesTable.getItems().get(selectedIndex);
            courseDAO.delete(selectedCourse.getId());
            coursesTable.getItems().remove(selectedIndex);
        } else {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Дисциплина не выбрана");
            alert.setContentText("Пожалуйста, выберите дисциплину в таблице.");
            alert.showAndWait();
        }
    }

    private boolean showCourseEditDialog(Course course) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(ru.kafpin.kafedraais.HelloApplication.class.getResource("course-form-view.fxml"));
            javafx.scene.layout.VBox page = loader.load();

            javafx.stage.Stage dialogStage = new javafx.stage.Stage();
            dialogStage.setTitle("Редактирование дисциплины");
            dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            javafx.scene.Scene scene = new javafx.scene.Scene(page);
            dialogStage.setScene(scene);

            CourseFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCourse(course);

            dialogStage.showAndWait();

            return controller.isSaveClicked();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
