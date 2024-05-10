package controllers;

import entities.Exposees;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ExposeesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ExposeesControllers implements Initializable {

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnImg;

    @FXML
    private Button btnRech;

    @FXML
    private Button btnRet1;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private ScrollPane tabEx;

    @FXML
    private DatePicker txtDd;

    @FXML
    private DatePicker txtDf;

    @FXML
    private ChoiceBox<Exposees> txtId;

    @FXML
    private TextField txtNom;

    @FXML
    private ChoiceBox<Integer> txtUpdID;

    @FXML
    private VBox ExposeesVBox;

    @FXML
    private TextField txtRech1;

    private Exposees selectedExposee;
   // private ExposeesS selectedExposees;


    private final ExposeesService SE = new ExposeesService();

    private Exposees getSelectedExposee;
    ExposeesService exposeesService = new ExposeesService();

    @FXML
    void create(ActionEvent event) throws SQLException {
        if (champsSontValides()) {
            // Récupérer le chemin absolu du fichier sélectionné
            String imagePath = btnImg.getText();

            // Assurez-vous que le chemin de l'image n'est pas vide avant d'ajouter
            if (!imagePath.isEmpty()) {
                File sourceImage = new File(imagePath);
                String imageName = sourceImage.getName();

                // Enregistrer l'image dans le répertoire spécifié
                saveImage(sourceImage, imageName);

                // Ajouter avec le nom de l'image seulement
                SE.ajouter(new Exposees(txtNom.getText(),  txtDd.getValue().atStartOfDay(), txtDf.getValue().atStartOfDay(), imageName ));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exposition Ajoutée");
                alert.setHeaderText(null);
                alert.setContentText("L'exposition a été ajoutée avec succès!");
                alert.showAndWait();
                clearFields();
            } else {
                // Afficher une alerte si aucun fichier image n'a été sélectionné
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Champs Image Obligatoire !!");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Tous Les Champs Sont Obligatoires !");
            alert.showAndWait();
        }
    }
    private void saveImage(File sourceImage, String imageName) {
        File targetDirectory = new File("/images");
        if (!targetDirectory.exists()) {
            targetDirectory.mkdirs();
        }
        File targetFile = new File(targetDirectory, imageName);
        try {
            Files.copy(sourceImage.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Image Enregistrée Avec Success: " + targetFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erreur.");
        }
    }

    @FXML
    void selectImg1(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        // Add filters to only allow certain file types, e.g., images
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        // Show open file dialog
        Stage stage = (Stage) btnImg.getScene().getWindow(); // Assuming 'Image' is your button
        File selectedFile = fileChooser.showOpenDialog(stage);
        // Check if a file was selected
        if (selectedFile != null) {
            // Afficher le nom du fichier sélectionné
            System.out.println("Selected Image: " + selectedFile.getName());
            // Set the selected image name to the TextField or store it in a variable as needed
            btnImg.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void retour1(ActionEvent event){
        SE.changeScreen(event, "/menuExpo.fxml", "Menu");

    }
    // ######################CC################
    private boolean champsSontValides() {
        return !txtNom.getText().isEmpty()  && txtDd.getValue() != null && txtDf.getValue() != null;
    }
    private void clearFields() {

        txtNom.clear();
        txtDd.setValue(null);
        txtDf.setValue(null);
    }
// ##############FIN CC####################
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    List<Integer> list = SE.listexpositions();
    System.out.println(list);
    //.setItems(FXCollections.observableArrayList(list));
} }