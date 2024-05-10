package controllers;

import entities.Exposees;
import entities.Produits;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ExposeesService;
import services.ProduitsService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitsControllers implements Initializable {
    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnRet2;

    @FXML
    private Button btnSaveP;

    @FXML
    private TextField txtDescription;

    @FXML
    private ChoiceBox<String> txtIdExpos;

    @FXML
    private TextField txtPrix;
    private final ProduitsService SP = new ProduitsService();

    private final ExposeesService SE = new ExposeesService();
    private Exposees selectedExposee;

    @FXML
    void ret2(ActionEvent event) {
        SE.changeScreen(event, "/menuExpo.fxml", "Menu");

    }


    @FXML
    void saveP(ActionEvent event) {
        try {
            // Récupérer le chemin absolu du fichier sélectionné
            String imagePath = btnBrowse.getText();
            // Assurez-vous que le chemin de l'image n'est pas vide avant d'ajouter l'événement
            if (!imagePath.isEmpty()) {
                File sourceImage = new File(imagePath);
                String imageName = sourceImage.getName();
                // Enregistrer l'image dans le répertoire spécifié
                saveImage(sourceImage, imageName);
                // Récupérer l'ID de l'Exposé sélectionné dans la ChoiceBox
                String exposIdString = txtIdExpos.getValue().toString();
                if (!exposIdString.isEmpty()) {
                    // Get the selected exposé name
                    String exposName = txtIdExpos.getValue();
                    // Get the exposé ID from the database
                    int exposId = SP.getExpoId(exposName);
                    if (exposId != -1) {
                        // Créer une instance de ProduitsService
                        ProduitsService produitsService = new ProduitsService();
                        // Ajouter le produit avec le nom de l'image et l'ID de l'Exposé
                        produitsService.ajouter(new Produits(txtDescription.getText(), txtPrix.getText(), imageName, exposId));
                        // Afficher une alerte de succès
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Produit ajouté");
                        alert.setHeaderText(null);
                        alert.setContentText("Le Produit a été ajouté avec succès!");
                        alert.showAndWait();
                        // Effacer les champs de saisie
                        clearFields();
                    } else {
                        // Afficher une alerte d'erreur si l'ID de l'Exposé n'est pas valide
                        showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "L'ID de l'Exposé n'est pas valide!");
                    }
                } else {
                    // Afficher une alerte d'erreur si aucun Exposé n'est sélectionné
                    showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez sélectionner un Exposé!");
                }
            } else {
                // Afficher une alerte d'erreur si aucune image n'est sélectionnée
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez sélectionner une image!");
            }
        } catch (SQLException e) {
            // Gérer l'exception SQLException
            showAlert(Alert.AlertType.ERROR, "Erreur lors de l'ajout", "Une erreur s'est produite lors de l'ajout du produit: " + e.getMessage());
        } catch (NumberFormatException e) {
            // Gérer NumberFormatException (si l'ID de l'Exposé ne peut pas être converti en entier)
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "La valeur sélectionnée pour l'Exposé n'est pas valide!");
        }
    }

    //#############################CONTROLE SAISIE##########################################
private void showAlert(Alert.AlertType alertType, String title, String content) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}
    private boolean champsSontValides() {
        return txtIdExpos.getValue() != null && !txtDescription.getText().isEmpty()&&  txtPrix.getText().isEmpty() ;
    }
    private void clearFields() {
        txtIdExpos.setValue(null);
        txtDescription.clear();
        txtPrix.clear();
    }

//##########################FIN CONTROLE SAISIE##########################################
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    List<String> expoNames;
    try {
        expoNames = SE.listExpoNames();
        txtIdExpos.setItems(FXCollections.observableArrayList(expoNames));
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Add a listener to the ChoiceBox to print the selected event
    txtIdExpos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            // If a new value is selected, print it in the ChoiceBox
            txtIdExpos.setValue(newValue);
            System.out.println("Selected Exposee: " + newValue);
        }
    });

}
    private void saveImage(File sourceImage, String imageName) {
        File targetDirectory = new File("/images");
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
    void selectImgp(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        // Add filters to only allow certain file types, e.g., images
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        // Show open file dialog
        Stage stage = (Stage) btnBrowse.getScene().getWindow(); // Assuming 'Image' is your button
        File selectedFile = fileChooser.showOpenDialog(stage);
        // Check if a file was selected
        if (selectedFile != null) {
            // Afficher le nom du fichier sélectionné
            System.out.println("Selected Image: " + selectedFile.getName());
            // Set the selected image name to the TextField or store it in a variable as needed
            btnBrowse.setText(selectedFile.getAbsolutePath());
        }

    }

}

