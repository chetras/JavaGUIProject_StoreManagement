package controller;

import javafx.scene.control.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelperMethod {


    // Method to check if it is a username or not
    public static boolean checkusername(String username){
        String pattern = "^[A-Za-z]\\w{4,29}$";
        Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(username);
        return matcher.find();
    }

    // Method to check if it is an email address or not
    public static boolean checkemail(String email){
        Matcher matcher = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE).matcher(email);
        return matcher.find();
    }

    // Method to check if it is a password or not
    public static boolean checkpassword(String password){
        return password.matches("^.{6,16}$");

    }

    public static void alertbox(String infoMessage, String headertext,String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(String.valueOf(infoMessage));
        alert.setTitle(title);
        alert.setHeaderText(headertext);
        alert.showAndWait();
    }





}
