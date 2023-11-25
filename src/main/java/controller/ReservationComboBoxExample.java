package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReservationComboBoxExample extends Application {
    private ComboBox<Order> reservation_id;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        reservation_id = new ComboBox<>();
        VBox root = new VBox(reservation_id);
        Scene scene = new Scene(root, 200, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Reservation ComboBox Example");
        primaryStage.show();

        try {
            ArrayList<Order> elementList = Res_selector();
            writeResId(elementList);
            if (elementList.isEmpty()) {
                System.out.println("No reservations found.");
                return;
            }

            for (Order order : elementList) {
                reservation_id.getItems().add(order);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private ArrayList<Order> Res_selector() throws FileNotFoundException {
        ArrayList<Order> elementList = new ArrayList<>();
        File file = new File("Product.txt");

        if (!file.exists()) {
            return elementList;
        }
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] elements = line.split(",");

            if (elements.length == 5) {
                String resID = elements[0];
                elementList.add(new Order(resID));
            }
        }
        scanner.close();
        return elementList;
    }

    private void writeResId(ArrayList<Order> elements) throws IOException {
        FileWriter writer = new FileWriter("ReservationID.txt");

        for (Order order : elements) {
            writer.write(order.getResId() + "\n");
        }
        writer.close();
    }

    private static class Order {
        private String resId;

        public Order(String resId) {
            this.resId = resId;
        }

        public String getResId() {
            return resId;
        }

        @Override
        public String toString() {
            return resId;
        }
    }
}


