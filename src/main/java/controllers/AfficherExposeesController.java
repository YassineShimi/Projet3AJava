package controllers;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import entities.Exposees;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import services.ExposeesService;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AfficherExposeesController implements Initializable {

    @FXML
    private Button BackBtn;

    @FXML
    private VBox ExposeesVBox;

    @FXML
    private DatePicker dateDebutUpdate;

    @FXML
    private DatePicker dateFinUpdate;

    @FXML
    private ImageView imageView;
    @FXML
    private Button deleteBtn;



    @FXML
    private TextField nomUpdate;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private Button selectImageUpdate;

    @FXML
    private Button updateExpoBtn;
    @FXML
    private Button rechbtn;

    @FXML
    private TextField rechtxt;
    private Exposees selectedExposee;
    private ExposeesService SE = new ExposeesService();
    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event, "/menuExpo.fxml", "Menu");

    }

    @FXML
    void OnClickedSelectImageUpdate(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                String imageName = selectedFile.getName();
                //C:\Users\Yassine\Gestion_Expositions\src\main\resources\images
                File destinationFile = new File("/images/" + imageName);
                ImageIO.write(bufferedImage, "png", destinationFile);
                selectedExposee.setImage_exposees(imageName);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                selectImageUpdate.setGraphic(new ImageView(image));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void generate(ActionEvent event) {
        if (selectedExposee != null) {
            String content = selectedExposee.getNom_e() + " " +
                    selectedExposee.getDateDebut() + " " +
                    selectedExposee.getDateFin();
            String newCon = content.replace(" ", "%20");
            String source = "http://api.qrserver.com/v1/create-qr-code/?data=" + newCon + "!&size=180x180";
            Image image = new Image(source);
            imageView.setImage(image);
        } else {
            // Handle case when no item is selected
            showAlert("No item selected. Please select a product.");
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void OnClickedSupprimer(ActionEvent event) {
        if (selectedExposee != null) {
            try {
                // Appelez la méthode de service pour supprimer l'événement sélectionné
                SE.supprimer(selectedExposee);

                // Actualisez l'affichage des événements
                afficherExposees();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez l'exception de suppression
            }
        }
    }

    @FXML
    void OnClickedUpdateExpo(ActionEvent event) {
                if (selectedExposee != null) {
                    try {
                        selectedExposee.setNom_e(nomUpdate.getText());
                        selectedExposee.setDateDebut(dateDebutUpdate.getValue().atStartOfDay());
                        selectedExposee.setDateFin(dateFinUpdate.getValue().atStartOfDay());

                SE.modifier(selectedExposee);
                afficherExposees();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void setupSearchBarListener() {
        rechtxt.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                findExposees(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    private void findExposees(String searchText) throws SQLException {
        ExposeesVBox.getChildren().clear(); // Clear the existing content

        List<Exposees> exposees = SE.searchByName(searchText);

        for (Exposees exposee : exposees) {
            String nom = exposee.getNom_e();
            // Check if the exposition's name contains the search text
            if (nom.toLowerCase().contains(searchText.toLowerCase())) {
                String imagePath = exposee.getImage_exposees();
                ImageView imageView = new ImageView();
                // Load image from file and set properties
                try {
                    // Load the image
                    InputStream inputStream = getClass().getResourceAsStream("/images/" + imagePath);
                    if (inputStream != null) {
                        Image image = new Image(inputStream);
                        imageView.setImage(image);
                        imageView.setFitWidth(200); // Set width
                        imageView.setFitHeight(200); // Set height
                    } else {
                        System.out.println("Image not found: " + imagePath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Create a Label for the exposition
                Label label = new Label();
                label.setText(
                        // Add exposition details to the label
                        " Nom : " + exposee.getNom_e() +
                                " Date de début : " + exposee.getDateDebut() +
                                " Date de fin : " + exposee.getDateFin() +
                                "");
                // Add ImageView and Label to VBox
                ExposeesVBox.getChildren().addAll(imageView, label);
                label.setOnMouseClicked(mouseEvent -> {
                    selectedExposee = exposee;

                });
            }
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ExposeesService SE = new ExposeesService();
        List<Integer> exposeesIDs = SE.listexpositions();
        afficherExposees();
        setupSearchBarListener();

    }
    private void afficherExposees() {
        ExposeesVBox.getChildren().clear(); // Effacez le contenu existant
        try {
            List<Exposees> exposees = SE.afficher();
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
                           // "ID de l'exposee : " + exposee.getId() +
                                    " Nom : " + exposee.getNom_e() +
                                    " Date de début : " + exposee.getDateDebut() +
                                    " Date de fin : " + exposee.getDateFin() +"");
                    // Ajoutez le label à VBox
                    ExposeesVBox.getChildren().add(label);

                    // Associez un gestionnaire d'événements de clic à chaque label pour sélectionner l'événement
                    label.setOnMouseClicked(mouseEvent -> {
                        selectedExposee = exposee;
                        // Mettez à jour les champs avec les données de l'événement sélectionné
                        nomUpdate.setText(selectedExposee.getNom_e());
                        dateDebutUpdate.setValue(selectedExposee.getDateDebut().toLocalDate());
                        dateFinUpdate.setValue(selectedExposee.getDateFin().toLocalDate());
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

}