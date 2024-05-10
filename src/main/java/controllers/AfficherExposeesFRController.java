package controllers;
import entities.Exposees;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import services.ExposeesService;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherExposeesFRController implements Initializable {

    @FXML
    private VBox ExposeesVBox;

    @FXML
    private ImageView imageView;
    @FXML
    private Button btnret;
    @FXML
    private ScrollPane scrollpane;

    @FXML
    private Button search;

    @FXML
    private Button search2;

    @FXML
    private TextField searchidname;

    @FXML
    private Button sortdate;

    @FXML
    private Button sortnd;
    private Exposees selectedExposee;
    private ExposeesService SE = new ExposeesService();


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
    void rechercheExpo(ActionEvent event) {

    }

    @FXML
    void sortdate(ActionEvent event) {
        try {
            List<Exposees> exposeesList = SE.afficher();
            exposeesList.sort(Comparator.comparing(Exposees::getDateDebut));
            afficherExposeesSS(exposeesList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    @FXML
    void sortnameasc(ActionEvent event) {
        try {
            List<Exposees> expos = SE.afficher();
            expos.sort(Comparator.comparing(Exposees::getNom_e));
            afficherExposeesSS(expos);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    @FXML
    void sortnamedesc(ActionEvent event) {
        try {
            List<Exposees> expos = SE.afficher();
            expos.sort(Comparator.comparing(Exposees::getNom_e).reversed());
            afficherExposeesSS(expos);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }
    private void afficherExposeesSS(List<Exposees> exposeesList) {
        ExposeesVBox.getChildren().clear(); // Clear the existing content
        try {
            if (exposeesList != null && !exposeesList.isEmpty()) {
                for (Exposees exposee : exposeesList) {
                    String imagePath = exposee.getImage_exposees();
                    // Create an ImageView to display the image
                    ImageView imageView = new ImageView();
                    // Load the image from the file path
                    try {
                        if (imagePath != null && !imagePath.isEmpty()) {
                            // Load the image from the classpath
                            InputStream inputStream = getClass().getResourceAsStream("/images/" + imagePath);
                            if (inputStream != null) {
                                Image image = new Image(inputStream);
                                imageView.setImage(image);
                                imageView.setFitWidth(200); // Adjust if necessary
                                imageView.setFitHeight(200); // Adjust if necessary
                                ExposeesVBox.getChildren().add(imageView);
                            } else {
                                System.out.println("Failed to load image: " + imagePath);
                            }
                        } else {
                            System.out.println("Image path is null or empty.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error loading image: " + e.getMessage());
                        e.printStackTrace();
                    }

                    // Create a label to display the exposee data
                    Label label = new Label();
                    label.setText(
                            " Nom : " + exposee.getNom_e() +
                                    " Date de début : " + exposee.getDateDebut() +
                                    " Date de fin : " + exposee.getDateFin() + "");
                    ExposeesVBox.getChildren().add(label);
                    label.setOnMouseClicked(mouseEvent -> {
                        selectedExposee = exposee;

                    });
                }
            } else {
                System.out.println("No exposition found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    label.setOnMouseClicked(mouseEvent -> {
                        selectedExposee = exposee;

                    });
                }
            } else {
                System.out.println("No Exposee found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception SQL
        }
    }
    private void setupSearchBarListener() {
        searchidname.textProperty().addListener((observable, oldValue, newValue) -> {
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
    void ret(ActionEvent event) {
        SE.changeScreen(event, "/frontGExpo.fxml", "Menu");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        afficherExposees();
        setupSearchBarListener();

    }
}
