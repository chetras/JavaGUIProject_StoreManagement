package controller;

import com.store.logininterface.main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class logincontroller {
    @FXML
    public Label wronglogin;

    @FXML
    public TextField usernamefield;

    @FXML
    public PasswordField passwordfield;

    public void handleloginbutton (Event e) throws IOException {
        checkLogin();
    }

    public void checkLogin() throws IOException{
        main m = new main();
        String username = usernamefield.getText();
        String password = passwordfield.getText();
        if ("John".equals(username) && "123".equals(password)) {
            wronglogin.setText("Success - Admin Dashboard");
            m.changeScene("admin-dashboard.fxml");
        } else if ("Alice".equals(username) && "456".equals(password)) {
            wronglogin.setText("Success - User Dashboard");
            m.changeScene("user-dashboard.fxml");
        } else if (username.isEmpty() || password.isEmpty()) {
            wronglogin.setText("Please enter both username and password.");
        } else {
            wronglogin.setText("Wrong username or password");
        }
    }

    public void handleregisterbutton(Event e) throws IOException{
        main me = new main();
        me.changeScene("register-view.fxml");

    }
}