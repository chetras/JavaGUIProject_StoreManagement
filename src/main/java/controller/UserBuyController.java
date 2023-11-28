package controller;

import Model.Order;
import Model.Product;
import com.store.logininterface.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UserBuyController implements Initializable {
    private static final String PRODUCT_FILE_PATH = "Product.txt";
    private static final String USER_ORDERS_FILE_PATH = "user-order.txt";
    private final ObservableList<Product> prodctlist = FXCollections.observableArrayList();
    private final ObservableList<Product> userOrders = FXCollections.observableArrayList();

    @FXML
    private Label totalprices;

    @FXML
    private ComboBox<Product> product_id;

    @FXML
    private TextField stockfield;

    @FXML
    private TableView<Product> productData;

    @FXML
    private TableColumn<Product, String> ProdId;

    @FXML
    private TableColumn<Product, String> ProdName;

    @FXML
    private TableColumn<Product, String> ProdPrice;

    @FXML
    private TableColumn<Product, String> ProdCat;

    @FXML
    private TableColumn<Product, String> ProdStock;

    public void handlelogoutbutton(Event e) throws IOException {
        main me = new main();
        me.changeScene("login-view.fxml");
    }
    public void onhistorybtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("userhistory.fxml");
    }
    public void onProductbtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("userdashboard.fxml");
    }
    public void onHomebtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("user-home.fxml");
    }

    private void initeCols(){
        ProdId.setCellValueFactory(new PropertyValueFactory<>("productID"));
        ProdName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        ProdPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProdCat.setCellValueFactory(new PropertyValueFactory<>("catagory"));
        ProdStock.setCellValueFactory(new PropertyValueFactory<>("productStocks"));
    }

    public void loadData() throws FileNotFoundException{
        prodctlist.clear();
        Scanner input = new Scanner(new File(PRODUCT_FILE_PATH));

        product_id.getItems().clear();
        while (input.hasNextLine()){
            String[] str = input.nextLine().split(",");
            if (str.length == 5) {
                Product product = new Product(str[0], str[1], str[2], str[3], str[4]);
                prodctlist.add(product);
                product_id.getItems().add(product); // Add the product to the ComboBox
            } else {
                // Handle the case where the array doesn't have enough elements
                System.err.println("Invalid data format: " );
            }
        }
        productData.setItems(prodctlist);
        input.close();
    }

    @FXML
    public void addproduct(Event e) throws IOException {
        Product selectedProduct = product_id.getSelectionModel().getSelectedItem();
        String stockText = stockfield.getText();

        if (selectedProduct != null && !stockText.isEmpty()) {
            int stock = Integer.parseInt(stockText);

            if (stock <= 0) {
                System.out.println("Invalid quantity");
                return;
            }

            int availableStock = Integer.parseInt(selectedProduct.getProductStocks());

            if (stock > availableStock) {
                System.out.println("Insufficient stock");
                return;
            }
            String priceString = selectedProduct.getPrice().replaceAll("\\$", "");
            int price = Integer.parseInt(priceString);
            int totalPrice = price * stock;
            // Update the total price label
            totalprices.setText("$" + totalPrice);
            // Update user orders file
            saveUserOrder(selectedProduct, stock);

            // Update product stock in the Product.txt file
            updateProductStock(selectedProduct, stock);

            // Refresh the product data
            loadData();

            product_id.getSelectionModel().clearSelection();
            stockfield.clear();
        }
    }

    private void saveUserOrder(Product product, int stock)throws IOException{
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_ORDERS_FILE_PATH))){
            writer.println(product.getProductID() + "," + product.getProductName() +","+  product.getPrice()+","+stock);
            System.out.println("Order placed successfully");
        }
    }

    private void updateProductStock(Product updatedproduct, int quantity) throws IOException{
        ArrayList<Product> products = readProduct();

        for (Product product : products){
            if (product.getProductID().equals(updatedproduct.getProductID())){
                int stock = Integer.parseInt(product.getProductStocks());
                stock-=quantity;
                product.setProductStocks(String.valueOf(stock));
                break;
            }
        }
        saveProductList(products);
    }

    private ArrayList<Product> readProduct() throws IOException
    {
        ArrayList<Product> products = new ArrayList<>();
        File file = new File(PRODUCT_FILE_PATH);

        if(!file.exists()){
            return products;
        }

        try (Scanner input = new Scanner(file)){
            while(input.hasNextLine()){
                String line = input.nextLine();
                String[] parts = line.split(",");
                if(parts.length == 5 ){
                    String productID = parts[0];
                    String productName = parts[1];
                    String productPrice = parts[2];
                    String productCatagory = parts[3];
                    String productStock = parts[4];
                    products.add(new Product(productID, productName, productPrice, productCatagory, productStock));
                }
            }

        }
        return products;
    }

    private void saveProductList(ArrayList<Product> products) throws IOException{   //to update the stock of the product in our product txt file when the user buy the product
        try(PrintWriter writer = new PrintWriter(new FileWriter(PRODUCT_FILE_PATH))){
            for (Product product : products){
                writer.println(product.getProductID() + "," +
                        product.getProductName() + "," +
                        product.getPrice() + "," +
                        product.getCatagory() + "," +
                        product.getProductStocks());
            }
            System.out.println("Product list updated successfully");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initeCols();
        try {
            loadData();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
