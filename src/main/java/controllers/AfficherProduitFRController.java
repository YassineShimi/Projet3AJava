package controllers;

import entities.Produits;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import services.ExposeesService;
import services.ProduitsService;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherProduitFRController implements Initializable {



    @FXML
    private ImageView imageView;

    @FXML
    private ImageView imageViewQR;

    @FXML
    private VBox produitsVBox;
    @FXML
    private Button retttt;
    @FXML
    private ScrollPane scrollpane;

    @FXML
    private ScrollPane scrollpanel;

    @FXML
    private Button search;

    @FXML
    private Button search2;

    @FXML
    private TextField searchidnamepr;

    @FXML
    private Button sortdate;

    @FXML
    private Button sortnameascpr;

    @FXML
    private Button sortnamedescpr;

    @FXML
    private Button sortnd;

    @FXML
    private Button sortprix;
    private Produits selectedProduit;
    @FXML
    private Pagination pagination;

    private final ProduitsService SP = new ProduitsService();
    private List<Produits> produitsList;
    private final int itemsPerPage = 4; // Change this value as per your requirement
    private final ExposeesService SE = new ExposeesService();

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
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void afficherProduits() {
        produitsVBox.getChildren().clear(); // Clear existing content

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
                                produitsVBox.getChildren().add(imageView);
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
                    produitsVBox.getChildren().add(label);

                    label.setOnMouseClicked(mouseEvent -> {
                        selectedProduit = produits;
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
    void retprodBtn(ActionEvent event) {
        SE.changeScreen(event, "/frontGExpo.fxml", "Menu");

    }

    @FXML
    void sortnameasc(ActionEvent event) {
        try {
            List<Produits> produitsList = SP.afficher();
            produitsList.sort(Comparator.comparing(Produits::getDescription));
            findProduits(produitsList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    @FXML
    void sortnamedesc(ActionEvent event) {
        try {
            List<Produits> produitsList = SP.afficher();
            produitsList.sort(Comparator.comparing(Produits::getDescription).reversed());
            findProduits(produitsList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    @FXML
    void sortprix(ActionEvent event) {
        try {
            List<Produits> produitsList = SP.afficher();
            produitsList.sort(Comparator.comparing(Produits::getPrix));
            findProduits(produitsList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }
    private void setupSearchBarListener() {
        searchidnamepr.textProperty().addListener((observable, oldValue, newValue) -> {
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
        produitsVBox.getChildren().clear();

        // Loop through the found products
        for (Produits produit : produits) {
            String description = produit.getDescription();
            // Check if the product's description contains the search text
            String searchText = searchidnamepr.getText(); // Get the search text from the text field
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
                produitsVBox.getChildren().addAll(imageView, label);

                // Associate a click event handler to each label for selection of the product
                label.setOnMouseClicked(mouseEvent -> {
                    selectedProduit = produit;
                });
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            produitsList = SP.afficher();
            int pageCount = (int) Math.ceil((double) produitsList.size() / itemsPerPage);

            pagination.setPageCount(pageCount);
            pagination.setPageFactory(this::createPage);

            pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> {
                createPage(newIndex.intValue());
            });

            // Setup search bar listener
            setupSearchBarListener();
        } catch (SQLException e) {
            showAlert("Error retrieving products: " + e.getMessage());
        }
    }


    private VBox createPage(int pageIndex) {
        VBox page = new VBox();
        page.setSpacing(10);

        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, produitsList.size());

        for (int i = startIndex; i < endIndex; i++) {
            Produits produit = produitsList.get(i);
            String imagePath = produit.getRessource();
            ImageView imageView = new ImageView();
            try {
                if (imagePath != null && !imagePath.isEmpty()) {
                    InputStream inputStream = getClass().getResourceAsStream("/images/" + imagePath);
                    if (inputStream != null) {
                        Image image = new Image(inputStream);
                        imageView.setImage(image);
                        imageView.setFitWidth(200); // Adjust if necessary
                        imageView.setFitHeight(200); // Adjust if necessary
                        page.getChildren().add(imageView);
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
                    " Description : " + produit.getDescription() +
                            " Prix : " + produit.getPrix() +
                            " Ressource : " + produit.getRessource()
            );
            page.getChildren().add(label);
            label.setOnMouseClicked(mouseEvent -> {
                selectedProduit = produit;
            });
        }

        produitsVBox.getChildren().clear();
        produitsVBox.getChildren().add(page);

        return page;
    }

}
