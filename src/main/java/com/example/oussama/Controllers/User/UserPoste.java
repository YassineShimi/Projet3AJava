package com.example.oussama.Controllers.User;


import com.example.oussama.HelloApplication;
import com.example.oussama.models.Post;
import com.example.oussama.services.PostService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UserPoste {
    @FXML
    ScrollPane ScrollePan;
    int choix = 1;

    private final PostService postService = new PostService();

    @FXML
    public void initialize(int choix) {
        this.choix = choix;
        loadPosts(choix);
    }


    private void loadPosts(int choix) {
        FlowPane reclamationFlowPane = new FlowPane();
        reclamationFlowPane.setStyle("-fx-pref-width: 950px; " +
                "-fx-pref-height: 547px;");

        List<Post> postList = null;


        try {
            postList = postService.getByIdUser(choix);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        for (Post post : postList) {
            VBox cardContainer = createPostVBox(post, choix);
            reclamationFlowPane.getChildren().add(cardContainer);
            FlowPane.setMargin(cardContainer, new Insets(10));
        }
        ScrollePan.setContent(reclamationFlowPane);
    }

    private VBox createPostVBox(Post post, int choix) {
        String imagepth = post.getImage();
        String viseopth = post.getVideo();
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
        pane.setPrefHeight(513.0);
        pane.setPrefWidth(631.0);

        MediaView mediaView = new MediaView();
        mediaView.setFitWidth(400.0);
        mediaView.setFitHeight(200.0);
        mediaView.setLayoutX(109.0);
        mediaView.setLayoutY(132.0);
        mediaView.setVisible(false);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(400.0);
        imageView.setFitHeight(200.0);
        imageView.setLayoutX(109.0);
        imageView.setLayoutY(132.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        imageView.setVisible(false);

        Button jouerbtn = new Button();
        jouerbtn.setLayoutX(512.0);
        jouerbtn.setLayoutY(174.0);
        jouerbtn.setMnemonicParsing(false);
        jouerbtn.setPrefHeight(26.0);
        jouerbtn.setPrefWidth(52.0);
        jouerbtn.setText("Play");
        jouerbtn.setVisible(false);

        Button pausebtn = new Button();
        pausebtn.setLayoutX(512.0);
        pausebtn.setLayoutY(219.0);
        pausebtn.setMnemonicParsing(false);
        pausebtn.setPrefHeight(26.0);
        pausebtn.setPrefWidth(52.0);
        pausebtn.setText("Pause");
        pausebtn.setVisible(false);

        Button arreterbtn = new Button();
        arreterbtn.setLayoutX(512.0);
        arreterbtn.setLayoutY(264.0);
        arreterbtn.setMnemonicParsing(false);
        arreterbtn.setPrefHeight(26.0);
        arreterbtn.setPrefWidth(52.0);
        arreterbtn.setText("Areet");
        arreterbtn.setVisible(false);

        Button videoBtn = new Button();
        videoBtn.setLayoutX(14.0);
        videoBtn.setLayoutY(175.0);
        videoBtn.setMnemonicParsing(false);
        videoBtn.setPrefHeight(25.0);
        videoBtn.setPrefWidth(81.0);
        videoBtn.setText("Video");

        Button imageBtn = new Button();
        imageBtn.setLayoutX(14.0);
        imageBtn.setLayoutY(220.0);
        imageBtn.setMnemonicParsing(false);
        imageBtn.setPrefHeight(25.0);
        imageBtn.setPrefWidth(81.0);
        imageBtn.setText("Image");

        Label userLabel = new Label();
        userLabel.setLayoutX(43.0);
        userLabel.setLayoutY(14.0);
        userLabel.setPrefHeight(17.0);
        userLabel.setPrefWidth(99.0);
        userLabel.setText(post.getTitle());

        Label dateLabel = new Label();
        dateLabel.setLayoutX(54.0);
        dateLabel.setLayoutY(31.0);
        dateLabel.setPrefHeight(17.0);
        dateLabel.setPrefWidth(99.0);
        dateLabel.setText(String.valueOf(post.getCreatedat()));

        Label contentLabel = new Label();
        contentLabel.setLayoutX(66.0);
        contentLabel.setLayoutY(57.0);
        contentLabel.setPrefHeight(75.0);
        contentLabel.setPrefWidth(538.0);
        contentLabel.setText(post.getContent());


        ImageView editImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/edit.png")));
        editImageView.setFitHeight(42.0);
        editImageView.setFitWidth(35.0);
        editImageView.setLayoutX(519.0);
        editImageView.setLayoutY(10.0);
        editImageView.setPickOnBounds(true);
        editImageView.setPreserveRatio(true);

        editImageView.setOnMouseClicked(event -> {
            modifierPost(post);
        });

        ImageView deleteImageView = new ImageView(new Image(getClass().getResourceAsStream("/image/delete.png")));
        deleteImageView.setFitHeight(35.0);
        deleteImageView.setFitWidth(42.0);
        deleteImageView.setLayoutX(578.0);
        deleteImageView.setLayoutY(10.0);
        deleteImageView.setPickOnBounds(true);
        deleteImageView.setPreserveRatio(true);

        deleteImageView.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer cette réservation ?");
            alert.setContentText("Cette action est irréversible.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    postService.supprimer(post.getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        if (imagepth != null) {
            File imageFile = new File(imagepth);
            if (imageFile.exists()) {
                String imageUrl = imageFile.toURI().toString();
                imageView.setImage(new Image(imageUrl));
                imageView.setVisible(true);
            } else {
                System.out.println("Fichier d'image introuvable : " + imagepth);
            }
        } else {
            imageBtn.setVisible(false);
        }

        videoBtn.setOnAction(actionEvent -> {
            imageView.setVisible(false);
            mediaView.setVisible(true);
            jouerbtn.setVisible(true);
            pausebtn.setVisible(true);
            arreterbtn.setVisible(true);
            File file1 = new File(viseopth);
            Media video = new Media(file1.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(video);
            mediaView.setMediaPlayer(mediaPlayer);

            jouerbtn.setOnAction(event -> mediaPlayer.play());
            pausebtn.setOnAction(event -> mediaPlayer.pause());
            arreterbtn.setOnAction(event -> mediaPlayer.stop());
        });

        imageBtn.setOnAction(actionEvent -> {
            imageView.setVisible(true);
            mediaView.setVisible(false);
        });


        pane.getChildren().addAll(mediaView, imageView, jouerbtn, pausebtn, arreterbtn, videoBtn, imageBtn, userLabel,
                dateLabel, contentLabel, editImageView, deleteImageView);


        cardContainer.getChildren().add(pane);
        return cardContainer;
    }

    void modifierPost(Post post) {
        Scene scene1 = ScrollePan.getScene();
        Stage stage1 = (Stage) scene1.getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/ModifierPost.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            ModifierPostController controller = loader.getController();
            controller.initialize(post);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Activiter");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText(message);
        alert.showAndWait();
    }



    @FXML
    void goaddpost() {
        Scene scene1 = ScrollePan.getScene();
        Stage stage1 = (Stage) scene1.getWindow();
        stage1.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/AddPosts.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            AddPostController controller = loader.getController();
            controller.initialize(this.choix);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Modifier Activiter");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}