package controller;

import Model.Order;
import com.store.logininterface.main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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
    private Label numberOfCustomersLabel;

    @FXML
    private BarChart<String, Number> orderchart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


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
            //loadChartData();
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
