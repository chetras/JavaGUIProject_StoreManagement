package controller;
import controller.UserDashboardcontroller;
import Model.User;
import Model.UserDataHandler;
import com.store.logininterface.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class adminCustomersController {

    @FXML
    private TableView<User> tableCustomersPage;

    @FXML
    private TableColumn<User, String> nameSurnameCo;

    @FXML
    private TableColumn<User, String> emailCo;

    @FXML
    private TableColumn<User, String> usernameCo;

    @FXML
    private TableColumn<User, Integer> orderCo;




    public void gobackbutton(Event e) throws IOException {
        main me = new main();
        me.changeScene("admin-dashboard.fxml");
    }

    public void initialize(){
        nameSurnameCo.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        emailCo.setCellValueFactory(new PropertyValueFactory<>("email"));
        usernameCo.setCellValueFactory(new PropertyValueFactory<>("username"));
        orderCo.setCellValueFactory(new PropertyValueFactory<>("ordercount"));

    // Load user data and populate the table
        ObservableList<User> users = FXCollections.observableArrayList(UserDataHandler.loadUsers());
        tableCustomersPage.setItems(users);
    }

    public void updateoncount(String username){
        ObservableList<User> customers = tableCustomersPage.getItems();
        User customertoUpdate = null;
        for (User customer : customers){
            if(customer.getUsername().equals(username) ){
                customertoUpdate = customer;
                break;
            }
        }

        if (customertoUpdate != null){
            int currentOrdercount = customertoUpdate.getOrdercount();
            int newOrderCount = currentOrdercount + 1;
            customertoUpdate.setOrdercount(newOrderCount);
        }

        tableCustomersPage.refresh();
    }



}
