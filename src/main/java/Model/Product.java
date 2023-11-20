package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    public String getProductID() {
        return productID.get();
    }

    public SimpleStringProperty productIDProperty() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID.set(productID);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getCatagory() {
        return catagory.get();
    }

    public SimpleStringProperty catagoryProperty() {
        return catagory;
    }

    public void setCatagory(String catagory) {
        this.catagory.set(catagory);
    }

    public String getProductStocks() {
        return productStocks.get();
    }

    public SimpleStringProperty productStocksProperty() {
        return productStocks;
    }

    public void setProductStocks(String productStocks) {
        this.productStocks.set(productStocks);
    }

    private final SimpleStringProperty productID = new SimpleStringProperty("");
    private final SimpleStringProperty productName = new SimpleStringProperty("");
    private final SimpleStringProperty price = new SimpleStringProperty("");
    private final SimpleStringProperty catagory = new SimpleStringProperty("");
    private final SimpleStringProperty productStocks = new SimpleStringProperty("");


    public Product(String productID, String productName, String price, String catagory, String productStocks) {
        setProductID(productID);
        setProductName(productName);
        setPrice(price);
        setCatagory(catagory);
        setProductStocks(productStocks);
    }
}
