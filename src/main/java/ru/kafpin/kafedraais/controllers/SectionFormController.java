package ru.kafpin.kafedraais.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kafpin.kafedraais.models.Section;

public class SectionFormController {

    @FXML private TextField nameField;
    @FXML private TextArea descriptionArea;

    private Stage dialogStage;
    private Section section;
    private boolean saveClicked = false;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setSection(Section section) {
        this.section = section;
        if (section != null && section.getName() != null) {
            nameField.setText(section.getName());
            descriptionArea.setText(section.getDescription());
        }
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        if (isInputValid()) {
            section.setName(nameField.getText());
            section.setDescription(descriptionArea.getText());

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

        if (nameField.getText() == null || nameField.getText().trim().length() == 0) {
            errorMessage += "Не заполнено название секции!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные поля");
            alert.setHeaderText("Пожалуйста, исправьте недочеты");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
