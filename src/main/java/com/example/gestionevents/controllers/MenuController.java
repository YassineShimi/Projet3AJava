package com.example.gestionevents.controllers;

import com.example.gestionevents.services.ServiceEvenement;
import com.example.gestionevents.services.ServiceParticipant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {



    @FXML
    private Button AfficherParicipantBtn;

    @FXML
    private Button AfficherPubliciteBtn;

    @FXML
    private Button AfficherEvenementBtnBack;

    @FXML
    private Button AfficherEvenementBtnFront;


    @FXML
    private Button AjouterEvenementBtn;

    @FXML
    private Button AjouterPubliciteBtn;

    @FXML
    private Button ajouterParticipantBtn;
    private final ServiceParticipant SP = new ServiceParticipant();
    private final ServiceEvenement SE = new ServiceEvenement();



    @FXML
    void OnClickedAfficherEventBack(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/AfficherEvenementController.fxml", "afficher Evenement Back ");

    }

    @FXML
    void OnClickedAfficherEventFront(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/AfficherEvenementFront.fxml", "afficher Evenement Front ");

    }

    @FXML
    void OnClickedAfficherParticipant(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/AfficherParticipant.fxml", "afficher participant");

    }

    @FXML
    void OnClickedAfficherPublicite(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/AfficherPublicite.fxml", "afficher publicite");

    }

    @FXML
    void OnClickedAjouterParticipant(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/AjouterParticipant.fxml", "Ajouter participant");

    }

    @FXML
    void OnClickedAjouterPublicite(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/AjouterPublicite.fxml", "Ajouter publicite");

    }

    @FXML
    void OnClickedEvenmentAjouter(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/AjouterEvenement.fxml", "Ajouter Evenement");

    }

}
