module com.example.gestionevents {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.gestionevents.controllers to javafx.fxml;
    opens com.example.gestionevents to javafx.fxml;
    //exports com.example.gestionevents;
    exports com.example.gestionevents.test;
    opens com.example.gestionevents.test to javafx.fxml;
}
