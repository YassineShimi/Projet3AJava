package controllers;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import entities.Produits;
import javafx.stage.FileChooser;
import services.ProduitsService;
import services.CrudInterface;
import  entities.Exposees;
import services.ExposeesService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherProduitsController implements Initializable {
    @FXML
    private Button BackBtn;

    @FXML
    private TextField descriptionProduit;

    @FXML
    private ChoiceBox<String> exposeeID;

    @FXML
    private TextField prixProduit;
    @FXML
    private ImageView imageView;
    @FXML
    private VBox produitVbox;
    @FXML
    private Button rech;

    @FXML
    private TextField rechprod;

    @FXML
    private Button suppPublicite;

    @FXML
    private Button imagepickBtn;

    @FXML
    private Button updatePubliciteBtn;
    private Produits selectedProduit;
    private final ProduitsService SP = new ProduitsService();
    private final ExposeesService SE = new ExposeesService();


    @FXML
    void OnClickedSuppProduit(ActionEvent event) {
        if (selectedProduit != null) {
            try {
                // Appelez la méthode de service pour supprimer l'événement sélectionné
                SP.supprimer(selectedProduit);

                // Actualisez l'affichage des événements
                afficherProduits();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez l'exception de suppression
            }
        }
    }
    @FXML
    public void generate(){
        if (selectedProduit != null) {
            String content = selectedProduit.getDescription(); // Assuming getDescription() returns the content for QR code generation
            String newCon = content.replace(" ", "%20");
            String source = "http://api.qrserver.com/v1/create-qr-code/?data=" + newCon + "!&size=180x180";
            Image image = new Image(source);
            imageView.setImage(image);
        } else {
            // Handle case when no item is selected
            showAlert("No item selected. Please select a product.");
        }
    }

    @FXML
    void OnClickedback(ActionEvent event) {
        SP.changeScreen(event, "/menuExpo.fxml", "Menu");

    }

    @FXML
    void OnCllickedUpdateProduit(ActionEvent event) {
        if (selectedProduit != null) {
            try {
                // Mettez à jour les champs modifiés de l'événement sélectionné
                selectedProduit.setDescription(descriptionProduit.getText());
                selectedProduit.setPrix(prixProduit.getText());
                int expoId = SP.getExpoId(exposeeID.getValue());
                SP.modifier(selectedProduit);
                afficherProduits();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onClickedSelectImgUpdate(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                // Load the image from the selected file
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                // Save the image to the Images folder
                String imageName = selectedFile.getName();
                File destinationFile = new File("images/" + imageName); // Removed leading "/" to make it relative path
                ImageIO.write(bufferedImage, "png", destinationFile);
                // Update the image path in the event model
                selectedProduit.setRessource(imageName);

                // Display the updated image in the UI
                Image image = new Image(selectedFile.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(100); // Adjust width as needed
                imageView.setFitHeight(100); // Adjust height as needed
                imagepickBtn.setGraphic(imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void refreshProduitsList() {
        produitVbox.getChildren().clear(); // Clear existing content

        try {
            List<Produits> produitsList = SP.afficher();
            for (Produits produits : produitsList) {
                // Create a label to display product data
                Label label = new Label();
                label.setText(
                        " Description : " + produits.getDescription() +
                                " Prix : " + produits.getPrix() +
                                " Ressource : " + produits.getRessource()
                );
                // Add the label to VBox
                produitVbox.getChildren().add(label);

                // Load and display the image
                String imagePath = produits.getRessource();
                if (imagePath != null && !imagePath.isEmpty()) {
                    try {
                        InputStream inputStream = getClass().getResourceAsStream("/images/" + imagePath);
                        if (inputStream != null) {
                            Image image = new Image(inputStream);
                            ImageView imageView = new ImageView(image);
                            imageView.setFitWidth(100); // Adjust width as needed
                            imageView.setFitHeight(100); // Adjust height as needed
                            produitVbox.getChildren().add(imageView);
                        } else {
                            System.out.println("Failed to load image: " + imagePath);
                        }
                    } catch (Exception e) {
                        System.out.println("Error loading image: " + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Image path is null or empty.");
                }

                // Associate a click event handler with each label to select the product
                label.setOnMouseClicked(mouseEvent -> {
                    // Update the fields with the data of the selected product
                    selectedProduit = produits;
                    String expoIdString = exposeeID.getValue();
                    if (expoIdString != null) {
                        selectedProduit.setExposees_id(Integer.parseInt(expoIdString));
                    }
                    descriptionProduit.setText(produits.getDescription());
                    prixProduit.setText(produits.getPrix());
                });
            }
        } catch (SQLException e) {
            showAlert("Erreur lors de l'affichage des produits: " + e.getMessage());
        }
    }

    private void afficherProduits() {
        produitVbox.getChildren().clear(); // Clear existing content

        try {
            List<Produits> produitsList = SP.afficher();
            if (produitsList != null && !produitsList.isEmpty()) {
                for (Produits produits : produitsList) {
                    String imagePath = produits.getRessource();
                    ImageView imageView = new ImageView();
                    try {
                        if (imagePath != null && !imagePath.isEmpty()) {
                            InputStream inputStream = getClass().getResourceAsStream("/images/" + imagePath);
                            if (inputStream != null) {
                                Image image = new Image(inputStream);
                                imageView.setImage(image);
                                imageView.setFitWidth(200); // Adjust if necessary
                                imageView.setFitHeight(200); // Adjust if necessary
                                produitVbox.getChildren().add(imageView);
                            } else {
                                System.out.println("Failed to load image: " + imagePath);
                                // Add a placeholder or default image if necessary
                            }
                        } else {
                            System.out.println("Image path is null or empty.");
                            // Add a placeholder or default image if necessary
                        }
                    } catch (Exception e) {
                        System.out.println("Error loading image: " + e.getMessage());
                        e.printStackTrace();
                        // Add a placeholder or default image if necessary
                    }

                    Label label = new Label();
                    label.setText(
                            " Description : " + produits.getDescription() +
                                    " Prix : " + produits.getPrix() +
                                    " Ressource : " + produits.getRessource()
                    );
                    produitVbox.getChildren().add(label);

                    label.setOnMouseClicked(mouseEvent -> {
                        selectedProduit = produits;
                        String expoIdString = exposeeID.getValue();
                        if (expoIdString != null) {
                            selectedProduit.setExposees_id(Integer.parseInt(expoIdString));
                        }
                        descriptionProduit.setText(produits.getDescription());
                        prixProduit.setText(produits.getPrix());
                    });
                }
            } else {
                System.out.println("No products found.");
            }
        } catch (SQLException e) {
            showAlert("Error displaying products: " + e.getMessage());
        }
    }


    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ProduitsService SP = new ProduitsService();
        List<String> expoName = null;
        try {
            expoName = SE.listExpoNames();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        exposeeID.setItems(FXCollections.observableArrayList(expoName));
        afficherProduits();
        refreshProduitsList();
        setupSearchBarListener();
    }

    private void setupSearchBarListener() {
        rechprod.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                List<Produits> produits = SP.searchByCriteria(newValue);
                findProduits(produits);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void findProduits(List<Produits> produits) {
        // Clear the existing content
        produitVbox.getChildren().clear();

        // Loop through the found products
        for (Produits produit : produits) {
            String description = produit.getDescription();
            // Check if the product's description contains the search text
            String searchText = rechprod.getText(); // Get the search text from the text field
            if (description.toLowerCase().contains(searchText.toLowerCase())) {
                String imagePath = produit.getRessource();
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

                // Create a Label for the product
                Label label = new Label();
                label.setText(
                        // Add product details to the label
                        " Description : " + produit.getDescription() +
                                " Prix : " + produit.getPrix()
                );
                // Add ImageView and Label to VBox
                produitVbox.getChildren().addAll(imageView, label);

                // Associate a click event handler to each label for selection of the product
                label.setOnMouseClicked(mouseEvent -> {
                    selectedProduit = produit;
                });
            }
        }
    }

}