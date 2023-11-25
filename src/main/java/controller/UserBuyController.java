package controller;

import Model.Product;
import com.store.logininterface.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UserBuyController implements Initializable {
    private static final String FILE_PATH = "Product.txt";
    private final ObservableList<Product> list = FXCollections.observableArrayList();
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
        list.clear();
        Scanner input = new Scanner(new File(FILE_PATH));
        while (input.hasNextLine()){
            String[] str = input.nextLine().split(",");
            if (str.length == 5) {
                list.add(new Product(str[0], str[1], str[2], str[3], str[4]));
            } else {
                // Handle the case where the array doesn't have enough elements
                System.err.println("Invalid data format: " );
            }
        }
        productData.setItems(list);
        input.close();
    }

    private ArrayList<Product> readProduct() throws IOException
    {
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
