package com.example.gestionevents.controllers;

import com.example.gestionevents.models.Evenement;
import com.example.gestionevents.services.ServiceEvenement;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEvenementFrontController implements Initializable {

    @FXML
    private Button BackBtn;

    @FXML
    private VBox EvenementVBox;


    @FXML
    private Button TriBtn;

    @FXML
    private ScrollPane scrollpane;
    private ServiceEvenement serviceEvenement;

    private Evenement selectedEvenement;
    private final ServiceEvenement SE = new ServiceEvenement();

    @FXML
    private Button ParticiperBtn;
    @FXML
    private TextField searchBar;

    private String tri="ASC";
    private int i = 0;
    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/FrontTOUT.fxml", "Menu");

    }
    private void setupSearchBarListener() {
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterEvents(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void OnClickedBtn(ActionEvent event) {
        if(i % 2 == 0){
            tri = "ASC";
        }else{
            tri = "DESC";

        }
        i++;
        afficherEvenements();
    }
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        afficherEvenements();
        TriBtn.setOnAction(this::OnClickedBtn);
        setupSearchBarListener();
    }

    private void filterEvents(String searchText) throws SQLException {
        EvenementVBox.getChildren().clear(); // Clear the existing content

        List<Evenement> evenements = SE.afficherbyNOM(tri);

        for (Evenement evenement : evenements) {
            String theme = evenement.getTheme_evenement();
            // Check if the event's theme contains the search text
            if (theme.toLowerCase().contains(searchText.toLowerCase())) {
                String imagePath = evenement.getImage_evenement();
                ImageView imageView = new ImageView();
                // Load image from file and set properties
                try {
                    // Load the image
                    InputStream inputStream = getClass().getResourceAsStream("/com/example/gestionevents/Images/" + imagePath);
                    if (inputStream != null) {
                        Image image = new Image(inputStream);
                        imageView.setImage(image);
                        imageView.setFitWidth(200); // Set width
                        imageView.setFitHeight(200); // Set height
                    } else {
                        System.out.println("Image not found: " + imagePath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Create a Label for the event
                Label label = new Label();
                label.setText(
                        "ID de publicité : " + evenement.getPublicite_id() +
                                " Thème : " + evenement.getTheme_evenement() +
                                "  Type : " + evenement.getType_evenement() +
                                " Date de début : " + evenement.getDate_debut() +
                                " Date de fin : " + evenement.getDate_fin() +
                                " Nombre de participants : " + evenement.getNbr_participant() +
                                "");
                // Add ImageView and Label to VBox
                EvenementVBox.getChildren().addAll(imageView, label);
            }
        }
    }


    private void afficherEvenements() {
        EvenementVBox.getChildren().clear(); // Effacez le contenu existant

        List<Evenement> evenements = SE.afficherbyNOM(tri);
        for (Evenement evenement : evenements) {
            String imagePath = evenement.getImage_evenement();

            // Créez un ImageView pour afficher l'image
            ImageView imageView = new ImageView();
            // Chargez l'image à partir du chemin du fichier
            try {
                // Assurez-vous que le chemin de l'image n'est pas nul ou vide
                if (imagePath != null && !imagePath.isEmpty()) {
                    // Chargez l'image à partir du classpath
                    InputStream inputStream = getClass().getResourceAsStream("/com/example/gestionevents/Images/" + imagePath);
                    if (inputStream != null) {
                        Image image = new Image(inputStream);
                        imageView.setImage(image);
                        // Définissez la largeur et la hauteur de l'ImageView
                        imageView.setFitWidth(200); // Ajustez si nécessaire
                        imageView.setFitHeight(200); // Ajustez si nécessaire
                        // Ajoutez l'ImageView à VBox
                        EvenementVBox.getChildren().add(imageView);
                    } else {
                        System.out.println("Échec du chargement de l'image : " + imagePath);
                    }
                } else {
                    System.out.println("Le chemin de l'image est nul ou vide.");
                }
            } catch (Exception e) {
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
                e.printStackTrace();
            }

            // Créez un label pour afficher les données de l'événement
            Label label = new Label();
            label.setText(
                    "ID de publicité : " + evenement.getPublicite_id() +
                            " Thème : " + evenement.getTheme_evenement() +
                            "  Type : " + evenement.getType_evenement() +
                            " Date de début : " + evenement.getDate_debut() +
                            " Date de fin : " + evenement.getDate_fin() +
                            " Nombre de participants : " + evenement.getNbr_participant() +
                            "");
            // Ajoutez le label à VBox
            EvenementVBox.getChildren().add(label);

            // Associez un gestionnaire d'événements de clic à chaque label pour sélectionner l'événement
            label.setOnMouseClicked(mouseEvent -> {
                selectedEvenement = evenement;
                // Mettez à jour les champs avec les données de l'événement sélectionné

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gestionevents/AjouterParticipant.fxml"));
                Parent root;
                try {
                    root = loader.load();
                    AjouterParticipantController ajouterParticipantController = loader.getController();
                    // Pass the selected event to the AjouterParticipantController
                    ajouterParticipantController.setSelectedEventName(selectedEvenement.getTheme_evenement());

                    // ajouterParticipantController.setSelectedEvent(selectedEvenement);
                    // Change screen to AjouterParticipant interface
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
        }
    }


    @FXML
    void OnClickedParticiper(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/AjouterParticipant.fxml", "Ajouter participant");

    }
}



