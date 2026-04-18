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
            showError("Успешный вход! (Главное окно в разработке)", false);
            // TODO: Переход на главное окно администратора
        } else {
            showError("Неверный логин или пароль", true);
        }
    }

    private void showError(String message, boolean isError) {
        errorLabel.setText(message);
        errorLabel.setTextFill(isError ? Color.RED : Color.GREEN);
        errorLabel.setVisible(true);
    }
}
