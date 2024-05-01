package com.example.gestionevents.controllers;

import com.example.gestionevents.services.ServiceEvenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.example.gestionevents.services.ServicePublicite;
import com.example.gestionevents.models.Publicite;

import java.sql.SQLException;

public class AjouterPubliciteController {


    @FXML
    private Button backBtn;
    @FXML
    private Button AjouterPubliciteBtn;

    @FXML
    private TextField DescPublicite;

    @FXML
    private TextField sponsorPublicite;

    @FXML
    private TextField typePublicite;

    private ServicePublicite servicePublicite;
    private final ServiceEvenement SE = new ServiceEvenement();

    public AjouterPubliciteController() {
        servicePublicite = new ServicePublicite();
    }

    @FXML
    void OnclickedAjouterPublicite(ActionEvent event) {
        String description = DescPublicite.getText().trim();
        String sponsor = sponsorPublicite.getText().trim();
        String type = typePublicite.getText().trim();

        if (description.isEmpty() || sponsor.isEmpty() || type.isEmpty()) {
            showAlert("Veuillez remplir tous les champs.");
            return;
        }

        // Création d'une instance de Publicite avec les données saisies
        Publicite publicite = new Publicite();
        publicite.setDescription(description);
        publicite.setSponsor(sponsor);
        publicite.setType(type);

        // Utilisation du service pour ajouter la publicité
        try {
            servicePublicite.ajouter(publicite);
            showAlert("Publicité ajoutée avec succès.");
            // Réinitialiser les champs après l'ajout
            DescPublicite.clear();
            sponsorPublicite.clear();
            typePublicite.clear();
        } catch (SQLException e) {
            showAlert("Erreur lors de l'ajout de la publicité : " + e.getMessage());
        }
    }


    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/Menu.fxml", "Menu");

    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
