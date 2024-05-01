package com.example.gestionevents.controllers;
import com.example.gestionevents.models.Evenement;
import com.example.gestionevents.services.ServiceEvenement;
import com.example.gestionevents.services.ServicePublicite;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterEvenementController implements Initializable {

    @FXML
    private Button AjouterEventBtn;

    @FXML
    private DatePicker DateDebutEvent;

    @FXML
    private DatePicker DateFinEvent;

    @FXML
    private Button Image;

    @FXML
    private TextField nbrParticipant;

    @FXML
    private ChoiceBox<Integer> publiciteId;

    @FXML
    private TextField themeEvent;

    @FXML
    private TextField typeEvent;
    @FXML
    private Button backBtn;

    private final ServicePublicite SP = new ServicePublicite();
    private final ServiceEvenement SE = new ServiceEvenement();
    @FXML
    void onclickedback(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/Menu.fxml", "Menu");

    }
    @FXML
    void OnClickedAjouterEvent(ActionEvent event) throws SQLException {
        if (champsSontValides()) {
            // Récupérer le chemin absolu du fichier sélectionné
            String imagePath = Image.getText();

            // Assurez-vous que le chemin de l'image n'est pas vide avant d'ajouter l'événement
            if (!imagePath.isEmpty()) {
                File sourceImage = new File(imagePath);
                String imageName = sourceImage.getName();

                // Enregistrer l'image dans le répertoire spécifié
                saveImage(sourceImage, imageName);

                // Ajouter l'événement avec le nom de l'image seulement
                SE.ajouter(new Evenement(publiciteId.getValue(), imageName, themeEvent.getText(), typeEvent.getText(), DateDebutEvent.getValue().atStartOfDay(), DateFinEvent.getValue(

                ).atStartOfDay(), Integer.parseInt(nbrParticipant.getText())));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Événement ajouté");
                alert.setHeaderText(null);
                alert.setContentText("L'événement a été ajouté avec succès!");
                alert.showAndWait();
                clearFields();
            } else {
                // Afficher une alerte si aucun fichier image n'a été sélectionné
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez sélectionner une image!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs!");
            alert.showAndWait();
        }
    }

    private void saveImage(File sourceImage, String imageName) {
        File targetDirectory = new File("src/main/resources/com/example/gestionevents/Images");
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }
        File targetFile = new File(targetDirectory, imageName);
        try {
            Files.copy(sourceImage.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Image saved successfully: " + targetFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save image.");
        }
    }

    @FXML
    void OnClickedSelectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        // Add filters to only allow certain file types, e.g., images
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        // Show open file dialog
        Stage stage = (Stage) Image.getScene().getWindow(); // Assuming 'Image' is your button
        File selectedFile = fileChooser.showOpenDialog(stage);
        // Check if a file was selected
        if (selectedFile != null) {
            // Afficher le nom du fichier sélectionné
            System.out.println("Selected Image: " + selectedFile.getName());
            // Set the selected image name to the TextField or store it in a variable as needed
            Image.setText(selectedFile.getAbsolutePath());
        }
    }

    private boolean champsSontValides() {
        return publiciteId.getValue() != null && !themeEvent.getText().isEmpty() && !typeEvent.getText().isEmpty() && DateDebutEvent.getValue() != null && DateFinEvent.getValue() != null && !nbrParticipant.getText().isEmpty();
    }

    private void clearFields() {
        publiciteId.setValue(null);
        themeEvent.clear();
        typeEvent.clear();
        DateDebutEvent.setValue(null);
        DateFinEvent.setValue(null);
        nbrParticipant.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Integer> list = SE.listevenement();
        System.out.println(list);
        publiciteId.setItems(FXCollections.observableArrayList(list));
    }
}

