package Model;

public class Order {
    private String productID;
    private String productName;
    private String productPrice;
    private String productCategory;
    private String productStock;

    public Order(){

    }

    public Order(String productID, String productName, String productPrice, String productStock) {
        this.productID = productID;
        this.productName= productName;
        this.productPrice = productPrice;
        this.productStock = productStock;

    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock;
    }
}
