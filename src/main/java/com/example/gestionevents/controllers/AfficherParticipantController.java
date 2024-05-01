package com.example.gestionevents.controllers;

import com.example.gestionevents.models.Participant;
import com.example.gestionevents.services.ServiceEvenement;
import com.example.gestionevents.services.ServiceParticipant;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherParticipantController implements Initializable {

    @FXML
    private VBox ParticipantVBox;

    @FXML
    private ScrollPane ScrollPane;

    @FXML
    private DatePicker dateUpdate;

    @FXML
    private TextField descriptionUpdate;

    @FXML
    private ChoiceBox<String> eventIDupdate;

    @FXML
    private Button suppBtn;

    @FXML
    private Button updateBtn;
    @FXML
    private Button BackBtn;


    private ServiceParticipant serviceParticipant = new ServiceParticipant();
    private Participant selectedParticipant;
    private final ServiceEvenement SE = new ServiceEvenement();


    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/Menu.fxml", "Menu");

    }

    @FXML
    void OnClickedSuppParticipant(ActionEvent event) {
        //Participant selectedParticipant = getSelectedParticipant();
        if (selectedParticipant != null) {
            try {
                serviceParticipant.supprimer(selectedParticipant);
                showAlert("Participant supprimé avec succès!");
                refreshParticipantList();
            } catch (SQLException e) {
                showAlert("Erreur lors de la suppression du participant: " + e.getMessage());
            }
        } else {
            showAlert("Veuillez sélectionner un participant à supprimer.");
        }
    }
   @FXML
    void OnClickedUpdateParticipant(ActionEvent event) {

       if (selectedParticipant != null) {
           try {
               // Retrieve the selected event ID based on its name
               int eventId = SE.getEventId(eventIDupdate.getValue());

               // Update the participant's information
               selectedParticipant.setEvenement_id(eventId);
               selectedParticipant.setDescription(descriptionUpdate.getText());
               selectedParticipant.setDate_participation(LocalDateTime.of(dateUpdate.getValue(), LocalDateTime.now().toLocalTime()));

               // Call the service method to update the participant
               serviceParticipant.modifier(selectedParticipant);

               // Show success message
               showAlert("Participant mis à jour avec succès!");

               // Refresh the participant list
               refreshParticipantList();
           } catch (SQLException e) {
               showAlert("Erreur lors de la mise à jour du participant: " + e.getMessage());
           }
       } else {
           showAlert("Veuillez sélectionner un participant à mettre à jour.");
       }

    }



    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceParticipant = new ServiceParticipant();
     /*   List<String> eventIDS = serviceParticipant.listEventNames();
        eventIDupdate.setItems(FXCollections.observableArrayList(eventIDS));
        afficherParticipants();
        refreshParticipantList();*/

        List<String> eventNames = null;
        try {
            eventNames = SE.listEventNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        eventIDupdate.setItems(FXCollections.observableArrayList(eventNames));
        afficherParticipants();
        refreshParticipantList();
    }
    private void refreshParticipantList() {
        ParticipantVBox.getChildren().clear(); // Effacez le contenu existant

        try {
            List<Participant> participants = serviceParticipant.afficher();
            for (Participant participant : participants) {
                // Créez un label pour afficher les données du participant
                Label label = new Label();
                label.setText(
                        "ID d'événement : " + participant.getEvenement_id() +
                                " Description : " + participant.getDescription() +
                                " Date de participation : " + participant.getDate_participation()
                );

                // Ajoutez le label à VBox
                ParticipantVBox.getChildren().add(label);

                // Associez un gestionnaire d'événements de clic à chaque label pour sélectionner le participant
                label.setOnMouseClicked(mouseEvent -> {
                    // Mettez à jour les champs avec les données du participant sélectionné
                    selectedParticipant = participant;
                   // selectedParticipant.setEvenement_id(Integer.parseInt(eventIDupdate.getValue()));
                    String eventIdString = eventIDupdate.getValue();
                    if (eventIdString != null) {
                        selectedParticipant.setEvenement_id(Integer.parseInt(eventIdString));
                    }
                    //eventIDupdate.setValue(participant.getEvenement_id());
                    descriptionUpdate.setText(participant.getDescription());
                    dateUpdate.setValue(participant.getDate_participation().toLocalDate());
                });

            }
        } catch (SQLException e) {
            showAlert("Erreur lors de l'affichage des participants: " + e.getMessage());
        }
    }

    private void afficherParticipants() {
        ParticipantVBox.getChildren().clear(); // Effacez le contenu existant

        try {
            List<Participant> participants = serviceParticipant.afficher();
            for (Participant participant : participants) {
                // Créez un label pour afficher les données du participant
                Label label = new Label();
                label.setText(
                        "ID d'événement : " + participant.getEvenement_id() +
                                " Description : " + participant.getDescription() +
                                " Date de participation : " + participant.getDate_participation()
                );

                // Ajoutez le label à VBox
                ParticipantVBox.getChildren().add(label);

                // Associez un gestionnaire d'événements de clic à chaque label pour sélectionner le participant
                label.setOnMouseClicked(mouseEvent -> {
                    // Mettez à jour les champs avec les données du participant sélectionné

                    selectedParticipant.setEvenement_id(Integer.parseInt(eventIDupdate.getValue()));

                    descriptionUpdate.setText(participant.getDescription());
                    dateUpdate.setValue(participant.getDate_participation().toLocalDate());
                });
            }
        } catch (SQLException e) {
            showAlert("Erreur lors de l'affichage des participants: " + e.getMessage());
        }
    }
}




