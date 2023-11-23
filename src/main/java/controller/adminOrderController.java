package controller;

import com.store.logininterface.main;
import javafx.event.Event;

import java.io.IOException;

public class adminOrderController {
    public void onbackbtn(Event e) throws IOException {
        main me = new main();
        me.changeScene("admin-dashboard.fxml");
    }

    public void onlogoutbtn(Event e) throws IOException {
        main me = new main();
        me.changeScene("login-view.fxml");
    }

}
