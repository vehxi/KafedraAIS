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
}
