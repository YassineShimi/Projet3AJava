package com.example.gestionevents.controllers;

import com.example.gestionevents.models.Evenement;
import com.example.gestionevents.models.Participant;
import com.example.gestionevents.services.ServiceEvenement;
import com.example.gestionevents.services.ServiceParticipant;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterParticipantController implements Initializable {

    @FXML
    private Button AjouterBtn;

    @FXML
    private ChoiceBox<String> EventID;

    @FXML
    private DatePicker dateParticipation;

    @FXML
    private TextField descParticipant;


    @FXML
    private Button BackBtn;
    private final ServiceParticipant SP = new ServiceParticipant();

    private final ServiceEvenement SE = new ServiceEvenement();
    private Evenement selectedEvent;

    public void setSelectedEvent(Evenement event) {
        this.selectedEvent = event;
    }

    public void setSelectedEventName(String eventName) {
        EventID.setValue(eventName);
    }
    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/FrontTOUT.fxml", "Menu");

    }
    @FXML
    void OnclickedAjouterParticipant(ActionEvent event) {
        try {
            if (champsSontValides()) {
                int eventId = SE.getEventId(EventID.getValue());

                SP.ajouter(new Participant(eventId, descParticipant.getText(), dateParticipation.getValue().atStartOfDay()));
                showAlert(Alert.AlertType.INFORMATION, "Événement ajouté", "L'événement a été ajouté avec succès!");
                clearFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez remplir tous les champs!");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout", "Une erreur s'est produite lors de l'ajout de l'événement: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean champsSontValides() {
        return EventID.getValue() != null && !descParticipant.getText().isEmpty()&&  dateParticipation.getValue() != null ;
    }
    private void clearFields() {
        EventID.setValue(null);
        descParticipant.clear();
        dateParticipation.setValue(null);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> eventNames;
        try {
            eventNames = SE.listEventNames();
            EventID.setItems(FXCollections.observableArrayList(eventNames));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Add a listener to the ChoiceBox to print the selected event
        EventID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // If a new value is selected, print it in the ChoiceBox
                EventID.setValue(newValue);
                System.out.println("Selected Event: " + newValue);
            }
        });

    }}
