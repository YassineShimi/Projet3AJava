
package com.example.gestionevents.controllers;

import com.example.gestionevents.models.Publicite;
import com.example.gestionevents.services.ServiceEvenement;
import com.example.gestionevents.services.ServicePublicite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherPubliciteController implements Initializable {

    @FXML
    private TextField descPublicite;

    @FXML
    private VBox publiciteVbox;

    @FXML
    private TextField sponsorPublicite;

    @FXML
    private Button suppPublicite;

    @FXML
    private TextField typePublicite;

    @FXML
    private Button updatePubliciteBtn;
    @FXML
    private Button BackBtn;

private  Publicite selectedPublicite;
private  ServicePublicite servicePublicite;
    private final ServiceEvenement SE = new ServiceEvenement();

    @FXML
    void OnClickedback(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/Menu.fxml", "Menu");

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        servicePublicite = new ServicePublicite();
        afficherPublicites();
    }

    private void afficherPublicites() {
        publiciteVbox.getChildren().clear();
       // ServicePublicite service = new ServicePublicite();
        try {
            List<Publicite> publicites = servicePublicite.afficher();
            for (Publicite publicite : publicites) {
                Label label = new Label();
                label.setText(
                        "ID de publicité : " + publicite.getId() +
                                " Description : " + publicite.getDescription() +
                                " Sponsor : " + publicite.getSponsor() +
                                " Type : " + publicite.getType()
                );

                // Ajouter le label à votre VBox
                publiciteVbox.getChildren().add(label);
                label.setOnMouseClicked(mouseEvent -> {
                    // Mettre à jour les champs avec les données de la publicité sélectionnée
                    descPublicite.setText(publicite.getDescription());
                    sponsorPublicite.setText(publicite.getSponsor());
                    typePublicite.setText(publicite.getType());

                    // Mettre à jour l'objet selectedPublicite
                    selectedPublicite = publicite;
                });
                // Associer un gestionnaire d'événements de clic à chaque label pour sélectionner la publicité
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs
        }
    }


    @FXML
    void OnClickedSuppPublicite(ActionEvent event) {
        if (selectedPublicite != null) {
            try {
                servicePublicite.supprimer(selectedPublicite);
                afficherPublicites();
                showAlert("Publicité supprimée avec succès!");
            } catch (SQLException e) {
                showAlert("Erreur lors de la suppression de la publicité: " + e.getMessage());
            }
        } else {
            showAlert("Veuillez sélectionner une publicité à supprimer.");
        }
    }

    @FXML
    void OnCllickedUpdatePublicite(ActionEvent event) {
        if (selectedPublicite != null) {
            try {
                selectedPublicite.setDescription(descPublicite.getText());
                selectedPublicite.setSponsor(sponsorPublicite.getText());
                selectedPublicite.setType(typePublicite.getText());

                servicePublicite.modifier(selectedPublicite);
                afficherPublicites();
                showAlert("Publicité mise à jour avec succès!");
            } catch (SQLException e) {
                showAlert("Erreur lors de la mise à jour de la publicité: " + e.getMessage());
            }
        } else {
            showAlert("Veuillez sélectionner une publicité à mettre à jour.");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


