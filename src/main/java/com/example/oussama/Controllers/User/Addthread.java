package com.example.oussama.Controllers.User;

import com.example.oussama.HelloApplication;
import com.example.oussama.models.Threads;
import com.example.oussama.services.ThreadServicss;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Addthread {
    @FXML
    private TextField TitleF;

    @FXML
    private TextField authorF;

    @FXML
    private Label checkauthor;

    @FXML
    private Label checktitle;

    @FXML
    private Label checktopic;

    @FXML
    private TextArea topicF;
    private ThreadServicss threadService =new ThreadServicss();
    boolean checkInpute(){
        if(TitleF.getText().isEmpty()){
            checktitle.setVisible(true);
            return false;
        }
        if(authorF.getText().isEmpty()){
            checkauthor.setVisible(true);
            return false;
        }
        if(topicF.getText().isEmpty()){
            checktopic.setVisible(true);
            return false;
        }
        return true;
    }
    @FXML
    void ajouter(){
        checkInpute();
        if(checkInpute()){
            Threads threads=new Threads();
            threads.setTitle(TitleF.getText());
            threads.setCreatedat(LocalDateTime.now());
            threads.setAuthor(authorF.getText());
            threads.setTopic(topicF.getText());
            threads.setLikes(0);
            try {
                threadService.ajouter(threads);
                showSuccessAlert("Exposition ajoutée avec succès.");
                Scene scenefer = TitleF.getScene();
                Stage stagefer = (Stage) scenefer.getWindow();
                stagefer.close();
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/User/Listthreads.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setTitle("");
                stage.setScene(scene);
                stage.show();

            } catch (SQLException e) {
                showErrorAlert("Erreur lors de l'ajout de l'exposition : " + e.getMessage());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Succès");
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
