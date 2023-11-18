package controller;

import Model.Product;
import com.store.logininterface.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AdminDashboardcontroller implements Initializable {
    private static final String FILE_PATH = "Product.txt";
    private final ObservableList<Product> list = FXCollections.observableArrayList();

    @FXML
    private Button btnaddProd;

    @FXML
    private Label text;

    @FXML
    private Button btnsearchProd;

    @FXML
    private Button btnupdateProd;

    @FXML
    private Button btndeleteProd;

    @FXML
    private Button btnshowProd;

    @FXML
    private TableView<Product> productData;

    @FXML
    private TextField txtProdId;

    @FXML
    private TextField txtProdName;

    @FXML
    private TextField txtProdPrice;

    @FXML
    private TextField txtProdCat;

    @FXML
    private TextField txtProdStock;

    @FXML
    private TableColumn<Product, String> ProdID;

    @FXML
    private TableColumn<Product, String> ProdName;

    @FXML
    private TableColumn<Product, String> ProdPrice;

    @FXML
    private TableColumn<Product, String> ProdCat;

    @FXML
    private TableColumn<Product, String> ProdStock;

    public void handleadminlogoutbutton(Event e) throws IOException {
        main me = new main();
        me.changeScene("login-view.fxml");
    }

    private ArrayList<Product> readProduct() throws IOException {
        ArrayList<Product> products = new ArrayList<>();
        File file = new File(FILE_PATH);

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

    private void writeProduct(ArrayList<Product> products) throws IOException{
        FileWriter writer = new FileWriter(FILE_PATH);

        for(Product product : products){
            writer.write(product.getProductID() + "," + product.getProductName() + "," + product.getPrice() + "," + product.getCatagory() + "," + product.getProductStocks());
        }
        writer.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initeCols();
        try{
            loadData();
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void displayTable() throws FileNotFoundException{
        initeCols();
        loadData();
    }

    private void initeCols(){
        ProdID.setCellValueFactory(new PropertyValueFactory<>("ProdId"));
        ProdName.setCellValueFactory(new PropertyValueFactory<>("ProdName"));
        ProdPrice.setCellValueFactory(new PropertyValueFactory<>("ProdPrice"));
        ProdCat.setCellValueFactory(new PropertyValueFactory<>("ProdCategory"));
        ProdStock.setCellValueFactory(new PropertyValueFactory<>("ProdStock"));
    }

    public void loadData() throws FileNotFoundException{
        list.clear();
        Scanner input = new Scanner(new File(FILE_PATH));
        while (input.hasNextLine()){
            String[] str = input.nextLine().split(",");
            list.add(new Product(str[0],str[1],str[2],str[3],str[4]))
        }
        productData.setItems(list);
        input.close();
    }

    @FXML
    protected void addOnClick(){
        Product product = new Product(txtProdId.getText(),txtProdName.getText(),txtProdPrice.getText(),txtProdCat.getText(),txtProdStock.getText());
        addProduct(product);
        txtProdId.setText("");
        txtProdName.setText("");
        txtProdPrice.setText("");
        txtProdCat.setText("");
        txtProdStock.setText("");
    }

    public void addProduct(Product product){
        if(product.getProductID().isEmpty() || product.getProductName().isEmpty() || product.getPrice().isEmpty() || product.getCatagory().isEmpty() || product.getProductStocks().isEmpty()){
            text.setText("Please Enter the Information to Add to Product");
        }
        else{
            try (PrintWriter p = new PrintWriter(new FileOutputStream("Product.txt",true))){
                p.println(product.getProductID()+ product.getProductName() + "," + product.getPrice() + "," + product.getCatagory()+ "," + product.getProductStocks());

            }catch (FileNotFoundException e){
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void removeonClick() throws IOException{
        removeProduct(txtProdId.getText());
        text.setText("");
    }

    public void removeProduct(String id) throws IOException{
        ArrayList<Product> products = readProduct();
        boolean found = false;

        for(int i = 0; i < products.size(); i++){
            Product product  = products.get(i);
            if (product.getProductID().equalsIgnoreCase(id)){
                products.remove(i);
                found = true;
                break;
            }
        }

        if(found){
            writeProduct(products);
            text.setText("Poduct ID: " + id + " has been successfully deleted");
        }
        else if(txtProdId.getText().isEmpty()){
            text.setText("Please Enter the valid ID, you want to remove");
        }
        else{
            text.setText("Product ID: " +id+ " not Found!");
        }
    }



















}
