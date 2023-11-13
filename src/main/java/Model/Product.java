package Model;

public class Product {
    private String name;
    private int id; // The product ID
    private double price;
    private int quantity; // The item in stock
    private String description;
    private int cat_id;
    private String cat_name;
    private int nr_sales;
    private int you;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public int getNr_sales() {
        return nr_sales;
    }

    public void setNr_sales(int nr_sales) {
        this.nr_sales = nr_sales;
    }
}
