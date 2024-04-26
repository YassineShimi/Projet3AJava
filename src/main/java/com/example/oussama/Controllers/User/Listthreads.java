package com.example.oussama.Controllers.User;

import com.example.oussama.HelloApplication;
import com.example.oussama.models.Post;
import com.example.oussama.models.Threads;
import com.example.oussama.services.ThreadServicss;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Listthreads {
    @FXML
    ScrollPane ScrollePan;
    @FXML
    public void initialize() {
        loadPosts();
    }


    private void loadPosts() {
        FlowPane reclamationFlowPane = new FlowPane();
        reclamationFlowPane.setStyle("-fx-pref-width: 950px; " +
                "-fx-pref-height: 547px;");

        List<Threads> threadsList = null;


        try {
            threadsList = threadService.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        for (Threads threads : threadsList) {
            VBox cardContainer = createDonationVBox(threads);
            reclamationFlowPane.getChildren().add(cardContainer);
            FlowPane.setMargin(cardContainer, new Insets(10));
        }
        ScrollePan.setContent(reclamationFlowPane);
    }
    private ThreadServicss threadService=new ThreadServicss();
    private VBox createDonationVBox(Threads threads) {
        VBox cardContainer = new VBox();
        cardContainer.setStyle("-fx-padding: 10px 10px 10px 10px;");
        cardContainer.setStyle(
                "-fx-background-color: #EFFCFF; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-border-color: #9CCBD6; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-width: 1px; ");


        Pane pane = new Pane();
        pane.setLayoutX(403.0);
        pane.setLayoutY(130.0);
        pane.setPrefHeight(300.0);
        pane.setPrefWidth(440.0);



        Label userLabel = new Label();
        userLabel.setLayoutX(43.0);
        userLabel.setLayoutY(15.0);
        userLabel.setPrefHeight(17.0);
        userLabel.setPrefWidth(200.0);
        userLabel.setText("Date :"+threads.getCreatedat());

        Label DateLabel = new Label();
        DateLabel.setLayoutX(43.0);
        DateLabel.setLayoutY(35.0);
        DateLabel.setPrefHeight(17.0);
        DateLabel.setPrefWidth(200.0);
        DateLabel.setText("Type :"+threads.getAuthor());

        Label dateLabel = new Label();
        dateLabel.setLayoutX(43.0);
        dateLabel.setLayoutY(55.0);
        dateLabel.setPrefHeight(17.0);
        dateLabel.setPrefWidth(99.0);
        dateLabel.setText(String.valueOf("severity :"+threads.getTitle()));

        Label contentLabel = new Label();
        contentLabel.setLayoutX(43.0);
        contentLabel.setLayoutY(75.0);
        contentLabel.setPrefHeight(75.0);
        contentLabel.setPrefWidth(538.0);
        contentLabel.setText("Descreption : "+threads.getTopic());

        ImageView editImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/edit.png"))));
        editImageView.setFitHeight(42.0);
        editImageView.setFitWidth(35.0);
        editImageView.setLayoutX(360.0);
        editImageView.setLayoutY(10.0);
        editImageView.setPickOnBounds(true);
        editImageView.setPreserveRatio(true);

        editImageView.setOnMouseClicked(event -> {
            modifieralert(threads);
        });
        /////
        ImageView daitailleImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/projects.png"))));
        daitailleImageView.setFitHeight(42.0);
        daitailleImageView.setFitWidth(35.0);
        daitailleImageView.setLayoutX(401.0);
        daitailleImageView.setLayoutY(10.0);
        daitailleImageView.setPickOnBounds(true);
        daitailleImageView.setPreserveRatio(true);

        daitailleImageView.setOnMouseClicked(event -> {
            daitaillealert(threads);
        });
        /////

        ImageView deleteImageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/delete.png"))));
        deleteImageView.setFitHeight(35.0);
        deleteImageView.setFitWidth(42.0);
        deleteImageView.setLayoutX(319.0);
        deleteImageView.setLayoutY(10.0);
        deleteImageView.setPickOnBounds(true);
        deleteImageView.setPreserveRatio(true);

        deleteImageView.setOnMouseClicked(event -> {
            Alert alertv = new Alert(Alert.AlertType.CONFIRMATION);
            alertv.setTitle("Confirmation de suppression");
            alertv.setHeaderText("Voulez-vous vraiment supprimer cette Treads ?");
            alertv.setContentText("Cette action est irréversible.");

            Optional<ButtonType> result = alertv.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    threadService.supprimer(threads.getId());
                    loadPosts();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        pane.getChildren().addAll(userLabel,
                dateLabel, contentLabel, editImageView, deleteImageView,daitailleImageView);


        cardContainer.getChildren().add(pane);
        return cardContainer;
    }
    void modifieralert(Threads threads){
        Scene scene1 = ScrollePan.getScene();
        Stage stage1 = (Stage) scene1.getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ModifierThreads.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            ModifierThreads controller = loader.getController();
            controller.initialize(threads);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Activiter");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void daitaillealert(Threads threads){
        Scene scene1 = ScrollePan.getScene();
        Stage stage1 = (Stage) scene1.getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Posts.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            UserPoste controller = loader.getController();
            controller.initialize(threads.getId());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Activiter");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void addthread() throws IOException {
        Scene scenefer = ScrollePan.getScene();
        Stage stagefer = (Stage) scenefer.getWindow();
        stagefer.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/User/Addthreads.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("");
        stage.setScene(scene);
        stage.show();
    }
}
