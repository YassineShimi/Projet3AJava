package com.example.gestionevents.controllers;

import com.example.gestionevents.services.ServiceEvenement;
import com.example.gestionevents.services.ServiceParticipant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FrontTOUTController {

    @FXML
    private Button AfficherEvenementBtnFront;

    @FXML
    private Button ajouterParticipantBtn;
    private final ServiceParticipant SP = new ServiceParticipant();
    private final ServiceEvenement SE = new ServiceEvenement();
    @FXML
    void OnClickedAfficherEventFront(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/AfficherEvenementFront.fxml", "afficher Evenement Front ");

    }

    @FXML
    void OnClickedAjouterParticipant(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/AjouterParticipant.fxml", "Ajouter participant");

    }

}
