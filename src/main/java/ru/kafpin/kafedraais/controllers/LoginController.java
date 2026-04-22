package ru.kafpin.kafedraais.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ru.kafpin.kafedraais.dao.UserDAO;
import ru.kafpin.kafedraais.models.User;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    private UserDAO userDAO = new UserDAO();

    @FXML
    protected void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Введите логин и пароль", true);
            return;
        }

        User user = userDAO.getByUsername(username);

        // В учебной БД пароли хранятся в открытом виде в поле PasswordHash
        if (user != null && user.getPasswordHash().equals(password)) {
            showError("Успешный вход!", false);
            openMainWindow();
        } else {
            showError("Неверный логин или пароль", true);
        }
    }

    private void openMainWindow() {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(ru.kafpin.kafedraais.HelloApplication.class.getResource("main-view.fxml"));
            javafx.scene.Scene scene = new javafx.scene.Scene(loader.load(), 800, 600);
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setTitle("АИС Кафедра - Панель администратора");
            stage.setScene(scene);
            stage.show();
            
            // Закрываем окно логина
            javafx.stage.Stage loginStage = (javafx.stage.Stage) loginButton.getScene().getWindow();
            loginStage.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            showError("Ошибка загрузки главного окна", true);
        }
    }

    private void showError(String message, boolean isError) {
        errorLabel.setText(message);
        errorLabel.setTextFill(isError ? Color.RED : Color.GREEN);
        errorLabel.setVisible(true);
    }
}
