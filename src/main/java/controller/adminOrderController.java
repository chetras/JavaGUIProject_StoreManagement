package controller;

import Model.Order;
import com.store.logininterface.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class adminOrderController implements Initializable {

    @FXML
    private TableView<Order> ordercustomer;

    @FXML
    private TableColumn<Order, String> ProdID;

    @FXML
    private TableColumn<Order, String> ProdName;

    @FXML
    private TableColumn<Order,String> PricePaid;

    @FXML
    private TableColumn<Order, String> Quantity;

    @FXML
    private TableColumn<Order, String> TotalPrice;

    @FXML
    private TableColumn<Order, String> Customer;

    @FXML
    private TextField searchorder;

    public void onbackbtn(Event e) throws IOException {
        main me = new main();
        me.changeScene("admin-dashboard.fxml");
    }

    public void onlogoutbtn(Event e) throws IOException {
        main me = new main();
        me.changeScene("login-view.fxml");
    }

    public void onsearch(Event e) throws IOException{
        String searchText = searchorder.getText().trim();
        ObservableList<Order> filteredOrderList = FXCollections.observableArrayList();

        try (Scanner scanner = new Scanner(new File("user-order.txt"))){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 6 && parts[0].equals(searchText)){
                    Order order = new Order();
                    order.setProductID(parts[0]);
                    order.setProductName(parts[1]);
                    order.setProductPrice(parts[2]);
                    order.setProductStock(parts[3]);
                    order.setProductTotalprice(parts[4]);
                    order.setUsername(parts[5]);

                    filteredOrderList.add(order);
                }
            }
        }

        ordercustomer.setItems(filteredOrderList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProdID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        ProdName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        PricePaid.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        TotalPrice.setCellValueFactory(new PropertyValueFactory<>("productTotalprice"));
        Customer.setCellValueFactory(new PropertyValueFactory<>("username"));

        try {
            loadData();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadData() throws IOException{
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        try (Scanner scanner = new Scanner(new File("user-order.txt"))){
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 6){
                    Order order = new Order();
                    order.setProductID(parts[0]);
                    order.setProductName(parts[1]);
                    order.setProductPrice(parts[2]);
                    order.setProductStock(parts[3]);
                    order.setProductTotalprice(parts[4]);
                    order.setUsername(parts[5]);

                    orderList.add(order);
                }
            }
            ordercustomer.setItems(orderList);

        }

    }
}
