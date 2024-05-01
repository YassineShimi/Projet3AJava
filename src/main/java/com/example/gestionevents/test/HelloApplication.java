package com.example.gestionevents.test;

import com.example.gestionevents.models.Evenement;
import com.example.gestionevents.services.ServiceEvenement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/gestionevents/Menu.fxml"));
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/gestionevents/FrontTOUT.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

