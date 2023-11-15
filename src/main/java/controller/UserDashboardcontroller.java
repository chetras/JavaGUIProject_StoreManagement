package controller;

import com.store.logininterface.main;
import javafx.event.Event;

import java.io.IOException;

public class UserDashboardcontroller {

        public void handleuserlogoutbutton(Event e) throws IOException {
            main me = new main();
            me.changeScene("login-view.fxml");
        }
}

