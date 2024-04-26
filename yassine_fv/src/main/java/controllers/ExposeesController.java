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
import services.ExposeesS;
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

public class ExposeesController implements Initializable {

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
    private ExposeesS selectedExposees;


    private final ExposeesS SE = new ExposeesS();

    private Exposees getSelectedExposee;
    ExposeesS exposeesService = new ExposeesS();

    @FXML
    void create(ActionEvent event) throws SQLException {
        if (champsSontValides()) {
            // Récupérer le chemin absolu du fichier sélectionné
            String imagePath = btnImg.getText();

            // Assurez-vous que le chemin de l'image n'est pas vide avant d'ajouter l'événement
            if (!imagePath.isEmpty()) {
                File sourceImage = new File(imagePath);
                String imageName = sourceImage.getName();

                // Enregistrer l'image dans le répertoire spécifié
                saveImage(sourceImage, imageName);

                // Ajouter l'événement avec le nom de l'image seulement
                exposeesService.create(new Exposees(txtNom.getText(),  txtDd.getValue().atStartOfDay(), txtDf.getValue().atStartOfDay(), imageName ));

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

    @FXML
    void delete(ActionEvent event) {
        if (selectedExposee != null) {
            try {
                // Appelez la méthode de service pour supprimer l'événement sélectionné
                exposeesService.delete(selectedExposee);

                // Actualisez l'affichage des événements
                afficherExposees();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez l'exception de suppression
            }
        }
    }

    @FXML
    void recherche(ActionEvent event) {

    }

    @FXML
    void update(ActionEvent event) {
        if (selectedExposee != null) {
            try {
                selectedExposee.setId((int) txtUpdID.getValue());
                selectedExposee.setNom_e(txtNom.getText());
                selectedExposee.setDateDebut(txtDd.getValue().atStartOfDay());
                selectedExposee.setDateFin(txtDf.getValue().atStartOfDay());

                // Call the update method on exposeesService instance
                exposeesService.update(selectedExposee);

                // Call other methods or update UI as needed
                // btnImg(event);
                afficherExposees();
            } catch (Exception e) {
                e.printStackTrace();
                // Handle any other exceptions that might occur
            }
        }
    }

    @FXML
    void retour1(ActionEvent event) {
        SE.changeScreen(event, "/sample.fxml", "Thakafa");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ExposeesVBox = new VBox();

        exposeesService = new ExposeesS();
        // Mettez à jour la liste des publicités dans la ChoiceBox lors de l'initialisation
        List<Integer> Ids = exposeesService.listeexposees1();
        txtUpdID.setItems(FXCollections.observableArrayList(Ids));

      // afficherExposees();
    }
    @FXML
    void btnImg(ActionEvent event) {
        if (selectedExposee != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner une image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("images", "*.png", "*.jpg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(selectedFile);
                    // Sauvegarder l'image dans le dossier Images
                    String imageName = selectedFile.getName();
                    File destinationFile = new File("/images/" + imageName);
                    ImageIO.write(bufferedImage, "png", destinationFile);

                    // Mettre à jour le chemin de l'image dans le modèle d'événement
                    selectedExposee.setImage_exposees(imageName);

                    // Afficher l'image mise à jour dans l'interface utilisateur
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    btnImg.setGraphic(imageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void afficherExposees() {
        ExposeesVBox.getChildren().clear(); // Effacez le contenu existant
        try {
            List<Exposees> exposees = exposeesService.afficher();
            if (exposees != null && !exposees.isEmpty()) {
                for (Exposees exposee : exposees) {
                    String imagePath = exposee.getImage_exposees();

                    // Créez un ImageView pour afficher l'image
                    ImageView imageView = new ImageView();
                    // Chargez l'image à partir du chemin du fichier
                    try {
                        // Assurez-vous que le chemin de l'image n'est pas nul ou vide
                        if (imagePath != null && !imagePath.isEmpty()) {
                            // Chargez l'image à partir du classpath
                            InputStream inputStream = getClass().getResourceAsStream("/images/" + imagePath);
                            if (inputStream != null) {
                                Image image = new Image(inputStream);
                                imageView.setImage(image);
                                // Définissez la largeur et la hauteur de l'ImageView
                                imageView.setFitWidth(200); // Ajustez si nécessaire
                                imageView.setFitHeight(200); // Ajustez si nécessaire
                                // Ajoutez l'ImageView à VBox
                                ExposeesVBox.getChildren().add(imageView);
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
                            "ID de l'exposee : " + exposee.getId() +
                                    " Nom : " + exposee.getNom_e() +
                                    " Date de début : " + exposee.getDateDebut() +
                                    " Date de fin : " + exposee.getDateFin() +"");
                    // Ajoutez le label à VBox
                    ExposeesVBox.getChildren().add(label);

                    // Associez un gestionnaire d'événements de clic à chaque label pour sélectionner l'événement
                    label.setOnMouseClicked(mouseEvent -> {
                        selectedExposee = exposee;
                        // Mettez à jour les champs avec les données de l'événement sélectionné
                        txtUpdID.setValue(selectedExposee.getId());
                        txtNom.setText(selectedExposee.getNom_e());
                        txtDd.setValue(selectedExposee.getDateDebut().toLocalDate());
                        txtDf.setValue(selectedExposee.getDateFin().toLocalDate());
                    });
                }
            } else {
                System.out.println("Aucune exposition trouvée.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception SQL
        }
    }

    @FXML
    void OnClickedSelectImageUpdate(ActionEvent event) {
        if (selectedExposee != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Sélectionner une image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("images", "*.png", "*.jpg", "*.gif")
            );
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(selectedFile);
                    // Sauvegarder l'image dans le dossier Images
                    String imageName = selectedFile.getName();
                    File destinationFile = new File("/images/" + imageName);
                    ImageIO.write(bufferedImage, "png", destinationFile);

                    // Mettre à jour le chemin de l'image dans le modèle d'événement
                    selectedExposee.setImage_exposees(imageName);

                    // Afficher l'image mise à jour dans l'interface utilisateur
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(100);
                    imageView.setFitHeight(100);
                    btnImg.setGraphic(imageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}