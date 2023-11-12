package controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class logincontroller {
    @FXML
    public TextField usernamefield;

    @FXML
    public PasswordField passwordfield;


    public void handleloginbutton (Event e) throws IOException {
        String username = usernamefield.getText();
        String password = passwordfield.getText();

        if(isValidInput(username, password)){


        }
    }

    private boolean isValidInput(String username, String password) {
        if ( username == null || username.isEmpty() || password == null || password.isEmpty()){
            HelperMethod.alertbox("Please enter the correct Email and Password",null,"Login failed" );
            return false;
        }
        else {
            return true;
        }
    }



}
