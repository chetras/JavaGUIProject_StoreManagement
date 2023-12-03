package controller;

import Model.Order;
import Model.Product;
import Model.User;
import Model.UserDataHandler;
import com.store.logininterface.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UserBuyController implements Initializable {
    private static final String PRODUCT_FILE_PATH = "Product.txt";
    private static final String USER_ORDERS_FILE_PATH = "user-order.txt";
    private final ObservableList<Product> prodctlist = FXCollections.observableArrayList();
    private final ObservableList<Order> userOrders = FXCollections.observableArrayList();
    private int totalprice = 0;
    User currentUser = logincontroller.getCurrentUser();

    @FXML
    private Label displaylable;

    @FXML
    private Button buttonre;

    @FXML
    private TextField UserPayAmount;

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
                System.out.println("Invalid data format: " );
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
                displaylable.setText("Invalid Quantity ");
                return;
            }
            int availableStock = Integer.parseInt(selectedProduct.getProductStocks());
            if (stock > availableStock) {
                displaylable.setText("Not Enough Stock please select different Product");
                return;
            }
            String priceString = selectedProduct.getPrice().replaceAll("\\$", "");
            int price = Integer.parseInt(priceString);
            int orderprice = price * stock;
            Order order = new Order(selectedProduct.getProductID(), selectedProduct.getProductName(),selectedProduct.getPrice(),String.valueOf(stock),String.valueOf(orderprice));
            userOrders.add(order);
            totalprice+=orderprice;
            // Update the total price label
            totalprices.setText("$" + totalprice);
            // Update user orders file
            saveUserOrder(order);
            // Update product stock in the Product.txt file
            updateProductStock(selectedProduct, stock);
            // Update logged-in user's order count
            if (currentUser != null) {
                UserDataHandler.updateUseroderCount(currentUser.getUsername(), stock);
            } else {
                System.out.println("User not authenticated.");
            }
            loadData();
            product_id.getSelectionModel().clearSelection();
            stockfield.clear();
        }
    }

    private void saveUserOrder(Order order)throws IOException{
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_ORDERS_FILE_PATH ,true))){
            writer.println(order.getProductID() + "," + order.getProductName() +","+  order.getProductPrice()+","+order.getProductStock()+","+order.getProductTotalprice()+","+currentUser.getUsername());
            displaylable.setText("Product added Successfully");
        }catch (NullPointerException e){

        }

    }

    private void updateProductStock(Product updatedproduct, int quantity) throws IOException {
        ArrayList<Product> products = readProduct();
        for (Product product : products) {
            if (product.getProductID().equals(updatedproduct.getProductID())) {
                int stock = Integer.parseInt(product.getProductStocks());
                stock -= quantity;
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

    public void saveProductList(ArrayList<Product> products) throws IOException{   //to update the stock of the product in our product txt file when the user buy the product
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

    @FXML
    public void Paybtn(Event e) {
        try {
            String enterAmountText = UserPayAmount.getText();
            if (enterAmountText.isEmpty()) {
                displaylable.setText("Please entered the amount");
                return;
            }

            int enteredAmount = Integer.parseInt(enterAmountText);
            int change = enteredAmount - totalprice;
            if (change >= 0) {
                displaylable.setText("Payment Successful");
                savePurchaseDetails(userOrders, totalprice, enteredAmount, change);
                clearUserCart();
            } else {
                displaylable.setText("Insufficient funds. Please enter a higher amount.");
            }
        } catch (NumberFormatException ex) {
            displaylable.setText("Invalid amount. Please enter a valid number.");
        }

    }

    private void savePurchaseDetails(ObservableList<Order> orders, int total, int enteredAmount, int change){
        String purchaseDetailsFilename = "purchase-details-" + currentUser.getUsername();
        try (PrintWriter purchaseWriter = new PrintWriter(new FileWriter(purchaseDetailsFilename,true));
             PrintWriter AllproductWriter = new PrintWriter(new FileWriter("Allpurchased-details.txt"))) {

            for (Order order : orders) {
                // Save purchase details
                purchaseWriter.write("Receipt: \n" +
                        "Product ID : " + order.getProductID() +
                        "\nProduct Name: " + order.getProductName() +
                        "\nTotal Price: " + total +
                        "\nTotal Change: " + change +
                        "\nCustomer Username: " + currentUser.getUsername() + "\n");

                // Save product details
                AllproductWriter.println("Product ID: " + order.getProductID() +
                        " \nProduct Name: " + order.getProductName() +
                        "\nQuantity: " + order.getProductStock() +
                        "\nTotal Price: " + order.getProductTotalprice() +
                        "\nCustomer Name: "+currentUser.getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearUserCart(){
        userOrders.clear();
        totalprice = 0;
        totalprices.setText("$"+totalprice);
        UserPayAmount.clear();
    }

    @FXML
    private void displayreceipt(Event e){
        String purchaseDetailsFilename = "purchase-details-" + currentUser.getUsername();

        try (BufferedReader br = new BufferedReader(new FileReader(purchaseDetailsFilename))){
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null){
                content.append(line).append("\n");
            }

            //Create a new Dialog
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Receipt");

            //Set custom content
            TextArea textArea = new TextArea(content.toString());
            textArea.setEditable(false);
            textArea.setWrapText(true);

            //Set the dialog content
            GridPane gridPane = new GridPane();
            gridPane.add(textArea ,0,0);
            dialog.getDialogPane().setContent(gridPane);

            //Add Ok button to the dialog
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

            //set the dialog to bedisplayed above the current screen
            dialog.initOwner(buttonre.getScene().getWindow());
            dialog.showAndWait();
        }catch (IOException a){
            a.printStackTrace();
        }
    }

    @FXML
    public void removeproduct(Event e) throws IOException {
        Product selectedProduct = product_id.getSelectionModel().getSelectedItem();
        String stockText = stockfield.getText();

        if (selectedProduct != null && !stockText.isEmpty()) {
            int stock = Integer.parseInt(stockText);
            if (stock <= 0) {
                displaylable.setText("Invalid Quantity");
                return;
            }

            Order orderToRemove = null;
            for (Order order : userOrders) {
                if (order.getProductID().equals(selectedProduct.getProductID())) {
                    int orderStock = Integer.parseInt(order.getProductStock());
                    if (stock > orderStock) {
                        displaylable.setText("Quantity to remove exceeds the available quantity in user's order");
                        return;
                    }

                    int price = Integer.parseInt(order.getProductPrice().replaceAll("\\$", ""));
                    int orderPrice = Integer.parseInt(order.getProductTotalprice());

                    // Calculate the new total price and update the label
                    totalprice -= price * stock;
                    totalprices.setText("$" + totalprice);

                    // Update the order's quantity and total price
                    order.setProductStock(String.valueOf(orderStock - stock));
                    order.setProductTotalprice(String.valueOf(orderPrice - price * stock));

                    // Update the user's order file
                    updateUserOrderFile();

                    // Update the product stock in the Product.txt file
                    updateProductStock(selectedProduct, -stock);

                    // Remove the order from the userOrders list if the stock becomes zero
                    if (order.getProductStock().equals("0")) {
                        orderToRemove = order;
                    }

                    break;
                }
            }

            // Remove the order from the userOrders list
            if (orderToRemove != null) {
                userOrders.remove(orderToRemove);
            }

            loadData();
            product_id.getSelectionModel().clearSelection();
            stockfield.clear();
        }
    }

    private void updateUserOrderFile() throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_ORDERS_FILE_PATH))) {
            for (Order order : userOrders) {
                writer.println(order.getProductID() + "," + order.getProductName() + "," +
                        order.getProductPrice() + "," + order.getProductStock() + "," +
                        order.getProductTotalprice() + "," + currentUser.getUsername());
            }

            displaylable.setText("Product removed successfully");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
