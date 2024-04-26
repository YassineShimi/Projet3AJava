module com.example.oussama {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;

    opens com.example.oussama.models to javafx.base;
    opens com.example.oussama to javafx.fxml;
    exports com.example.oussama;

    exports com.example.oussama.Controllers.User to javafx.fxml;
    opens com.example.oussama.Controllers.User to javafx.fxml;

    exports com.example.oussama.Controllers.Admin to javafx.fxml;
    opens com.example.oussama.Controllers.Admin  to javafx.fxml;
}