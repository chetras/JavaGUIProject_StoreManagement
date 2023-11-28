package controller;

import Model.Order;
import com.store.logininterface.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Adminhomecontroller implements Initializable {

    @FXML
    private Label numberOfCustomersLabel;

    @FXML
    public void handleadminlogoutbutton(Event e) throws IOException{
        main me = new main();
        me.changeScene("login-view.fxml");
    }

    public void onCustomerbtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("admincustomer.fxml");
    }

    public void onproductbtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("admin-dashboard.fxml");
    }

    public void onOrderbtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("order-admin.fxml");
    }

    public void onHomebtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("adminhome.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int numberOfCustomers = countCustomers();
            numberOfCustomersLabel.setText(String.valueOf(numberOfCustomers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int countCustomers() throws IOException {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            while (reader.readLine() != null) {
                count++;
            }
        }

        return count;
    }
}
