package controller;

import com.store.logininterface.main;
import javafx.event.Event;

import java.io.IOException;

public class adminCustomersController {
    public void gobackbutton(Event e) throws IOException {
        main me = new main();
        me.changeScene("admin-dashboard.fxml");
    }
}
