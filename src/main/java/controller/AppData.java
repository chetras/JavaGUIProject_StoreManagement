package controller;

import Model.Order;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppData {
    private static AppData instance;
    private ObservableList<Product> productList;
    private ObservableList<Order> userOrders;

    private AppData(){
        productList = FXCollections.observableArrayList();
        userOrders = FXCollections.observableArrayList();
    }

    public static synchronized AppData getInstance(){
        if (instance == null){
            instance= new AppData();
        }
        return instance;
    }

    public ObservableList<Product> getProductList(){
        return productList;
    }

    public ObservableList<Order> getUserOrders(){
        return userOrders;
    }

    public void setProductList(ObservableList<Product> productList){
        this.productList = productList;
    }


}
