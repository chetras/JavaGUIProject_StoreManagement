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
        if(usernamefield.getText().toString().equals("John") && passwordfield.getText().toString().equals("123")){
            wronglogin.setText("Success");
            m.changeScene("admin-dashboard.fxml");
        }
        else if (usernamefield.getText().isEmpty() && passwordfield.getText().isEmpty()){
            wronglogin.setText("Please enter user data.");
        }
        else {
            wronglogin.setText("Wrong username or password");
        }
    }

    public void handleregisterbutton(Event e) throws IOException{
        main me = new main();
        me.changeScene("register-view.fxml");

    }
}