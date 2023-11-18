package Model;

public class Product {
    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCatagory() {
        return Catagory;
    }

    public void setCatagory(String catagory) {
        Catagory = catagory;
    }

    public String getProductStocks() {
        return ProductStocks;
    }

    public void setProductStocks(String productStocks) {
        ProductStocks = productStocks;
    }

    private String ProductID;
    private String ProductName;
    private String Price;
    private String Catagory;
    private String ProductStocks;

    public Product(String productID, String productName, String price, String catagory, String productStocks) {
        ProductID = productID;
        ProductName = productName;
        Price = price;
        Catagory = catagory;
        ProductStocks = productStocks;
    }
}
