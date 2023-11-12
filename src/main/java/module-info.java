module com.store.storeproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.store.logininterface to javafx.fxml;
    exports com.store.logininterface;
    exports controller;
    opens controller to javafx.fxml;
}