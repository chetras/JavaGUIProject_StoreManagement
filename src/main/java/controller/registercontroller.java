package controller;

import Model.User;
import Model.UserDataHandler;
import com.store.logininterface.main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;


public class registercontroller {
    @FXML
    protected TextField fullnameField;

    @FXML
    protected TextField usernameField;

    @FXML
    protected TextField emailField;

    @FXML
    protected PasswordField passwordField;

    @FXML
    protected PasswordField confirmPasswordField;

    public void handleloginbutton2(Event e) throws IOException {
        main me = new main();
        me.changeScene("login-view.fxml");
    }

    public void handleCreateButton(Event e) throws IOException {
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match!");
            return;
        }

        User newUser = new User();
        newUser.setFullname(fullnameField.getText());  // Set the fullname property
        newUser.setUsername(usernameField.getText());
        newUser.setEmail(emailField.getText());
        newUser.setPassword(password);
        fullnameField.setText("");
        usernameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");

        UserDataHandler.saveUser(newUser);
    }
}