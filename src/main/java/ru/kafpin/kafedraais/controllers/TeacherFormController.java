package ru.kafpin.kafedraais.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kafpin.kafedraais.dao.SectionDAO;
import ru.kafpin.kafedraais.models.Section;
import ru.kafpin.kafedraais.models.Teacher;

import java.util.List;

public class TeacherFormController {

    @FXML private TextField nameField;
    @FXML private TextField positionField;
    @FXML private TextField degreeField;
    @FXML private TextField emailField;
    @FXML private ComboBox<Section> sectionComboBox;

    private Stage dialogStage;
    private Teacher teacher;
    private boolean saveClicked = false;
    private SectionDAO sectionDAO = new SectionDAO();

    @FXML
    private void initialize() {
        List<Section> sections = sectionDAO.getAll();
        sectionComboBox.setItems(FXCollections.observableArrayList(sections));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        if (teacher != null && teacher.getFullName() != null) {
            nameField.setText(teacher.getFullName());
            positionField.setText(teacher.getPosition());
            degreeField.setText(teacher.getAcademicDegree());
            emailField.setText(teacher.getEmail());

            if (teacher.getSectionId() != null) {
                for (Section s : sectionComboBox.getItems()) {
                    if (s.getId() == teacher.getSectionId()) {
                        sectionComboBox.getSelectionModel().select(s);
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
            teacher.setFullName(nameField.getText());
            teacher.setPosition(positionField.getText());
            teacher.setAcademicDegree(degreeField.getText());
            teacher.setEmail(emailField.getText());

            Section selected = sectionComboBox.getSelectionModel().getSelectedItem();
            if (selected != null) {
                teacher.setSectionId(selected.getId());
            } else {
                teacher.setSectionId(null);
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
        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Не заполнено обязательное поле!");
            alert.setContentText("Пожалуйста, введите ФИО преподавателя.");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
