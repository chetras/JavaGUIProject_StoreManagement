package controller;

import Model.Order;
import com.store.logininterface.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Adminhomecontroller implements Initializable {

    @FXML
    private Label totalincome;

    @FXML
    private Label numberOfCustomersLabel;

    @FXML
    private BarChart<String, Double> OrderChart;

    @FXML
    private LineChart<String, Double> IncomeChart;



    @FXML
    public void handleadminlogoutbutton(Event e) throws IOException{
        main me = new main();
        me.changeScene("login-view.fxml");
    }

    public void onCustomerbtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("admincustomer.fxml");
    }

    public void onproductbtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("admin-dashboard.fxml");
    }

    public void onOrderbtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("order-admin.fxml");
    }

    public void onHomebtn(Event e) throws IOException{
        main me = new main();
        me.changeScene("adminhome.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            int numberOfCustomers = countCustomers();
            numberOfCustomersLabel.setText(String.valueOf(numberOfCustomers));
            updateTotalIncome();
            updateIncomeChart();
            updateOrderChart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int countCustomers() throws IOException {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("user.txt"))) {
            while (reader.readLine() != null) {
                count++;
            }
        }

        return count;
    }

    private void updateTotalIncome(){
        try (BufferedReader br = new BufferedReader(new FileReader("Allpurchased-details.txt"))){
            String line;
            int Totalincome= 0;

            while ((line = br.readLine()) != null){
                if (line.startsWith("Total Price: ")){
                    Totalincome += Integer.parseInt(line.split(":")[1].trim());
                }
            }
            totalincome.setText("$"+Totalincome);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void updateIncomeChart() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Allpurchased-details.txt"));
            String line;
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName("Total Income");

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Total Price:")) {
                    String totalPrice = line.split(":")[1].trim();
                    double currentPrice = Double.parseDouble(totalPrice);

                    String category = series.getData().size() + "";
                    series.getData().add(new XYChart.Data<>(category, currentPrice));
                }
            }
            IncomeChart.getData().add(series);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void updateOrderChart() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Product.txt"));
            String line;
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName("Product Stocks");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String ProdName = parts[1];
                    String stock = parts[2];
                    // Parse the stock value as a string and convert it to double
                    series.getData().add(new XYChart.Data<>(ProdName, Double.parseDouble(stock)));
                }
            }

            OrderChart.getData().add(series);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*private void loadChartData() throws IOException{
        Map<String, Integer> userData = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("user-order.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = parts[0];
                    int quantity = Integer.parseInt(parts[3]);

                    userData.put(username, userData.getOrDefault(username, 0) + quantity);
                }
            }
        }
        // Populate the bar chart with the data
        ObservableList<XYChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Map.Entry<String, Integer> entry : userData.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChartData.add(series);

        // Set the chart data
        orderchart.setData(barChartData);

        // Set labels for the axes
        xAxis.setLabel("User");
        yAxis.setLabel("Total Quantity Ordered");
    }*/
}
