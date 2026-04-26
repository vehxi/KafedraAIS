package ru.kafpin.kafedraais.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kafpin.kafedraais.dao.TeacherDAO;
import ru.kafpin.kafedraais.models.Course;
import ru.kafpin.kafedraais.models.Teacher;

import java.util.List;

public class CourseFormController {

    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;
    @FXML private TextField semesterField;
    @FXML private ComboBox<Teacher> teacherComboBox;

    private Stage dialogStage;
    private Course course;
    private boolean saveClicked = false;
    private TeacherDAO teacherDAO = new TeacherDAO();

    @FXML
    private void initialize() {
        List<Teacher> teachers = teacherDAO.getAll();
        teacherComboBox.setItems(FXCollections.observableArrayList(teachers));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setCourse(Course course) {
        this.course = course;
        if (course != null && course.getCourseName() != null) {
            nameField.setText(course.getCourseName());
            descriptionArea.setText(course.getDescription());
            if (course.getSemester() != null) {
                semesterField.setText(String.valueOf(course.getSemester()));
            }
            if (course.getTeacherId() != null) {
                for (Teacher t : teacherComboBox.getItems()) {
                    if (t.getId() == course.getTeacherId()) {
                        teacherComboBox.getSelectionModel().select(t);
                        break;
                    }
                }
            }
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            course.setCourseName(nameField.getText());
            course.setDescription(descriptionArea.getText());

            if (!semesterField.getText().trim().isEmpty()) {
                course.setSemester(Integer.parseInt(semesterField.getText().trim()));
            } else {
                course.setSemester(null);
            }

            Teacher selected = teacherComboBox.getSelectionModel().getSelectedItem();
            if (selected != null) {
                course.setTeacherId(selected.getId());
            } else {
                course.setTeacherId(null);
            }

            saveClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            errorMessage += "Не заполнено название дисциплины!\n";
        }

        if (semesterField.getText() != null && !semesterField.getText().trim().isEmpty()) {
            try {
                Integer.parseInt(semesterField.getText().trim());
            } catch (NumberFormatException e) {
                errorMessage += "Семестр должен быть числом!\n";
            }
        }

        if (errorMessage.isEmpty()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Пожалуйста, исправьте недочеты");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
