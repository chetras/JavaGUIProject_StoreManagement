package controller;

import com.store.logininterface.main;
import javafx.event.Event;

import java.io.IOException;

public class registercontroller {

    public void handleloginbutton2(Event e) throws IOException {
        main me = new main();
        me.changeScene("login-view.fxml");
    }
}