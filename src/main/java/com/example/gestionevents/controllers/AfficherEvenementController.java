package com.example.gestionevents.controllers;

import com.example.gestionevents.models.Evenement;
import com.example.gestionevents.services.ServiceEvenement;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;


import javafx.embed.swing.SwingFXUtils;



import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class AfficherEvenementController implements Initializable {
    @FXML
    private VBox EvenementVBox;

    private ServiceEvenement serviceEvenement;

    @FXML
    private Button deleteBtn;

    @FXML
    private DatePicker dateDebutUpdate;

    @FXML
    private DatePicker dateFinEventUpdate;

    @FXML

    private TextField nbrParticipantUpdate;

    @FXML
    private ChoiceBox<Integer> publiciteIDUpdate;

    @FXML
    private ScrollPane scrollpane;

    @FXML
    private Button selectImageUpdate;

    @FXML
    private TextField themeEventUpdate;

    @FXML
    private TextField typeEventUpdate;

    @FXML
    private Button updateEventBtn;

    @FXML
    private Button StatBtn;

    @FXML
    private PieChart pieChart;
    private Evenement selectedEvenement;
    private final ServiceEvenement SE = new ServiceEvenement();

    @FXML
    void OnClickedStat(ActionEvent event) {
        generateStatistics();

    }

    private void generateStatistics() {
        pieChart.getData().clear(); // Effacez les données précédentes du PieChart

        try {
            List<Evenement> evenements = serviceEvenement.afficher();

            // Calculer le nombre total de participants
            int totalParticipants = 0;
            for (Evenement evenement : evenements) {
                totalParticipants += evenement.getNbr_participant();
            }

            // Calculer la proportion de chaque événement par rapport au nombre total de participants
            for (Evenement evenement : evenements) {
                double proportion = (double) evenement.getNbr_participant() / totalParticipants;
                String label = evenement.getTheme_evenement(); // Utilisez le thème de l'événement comme libellé dans le PieChart
                PieChart.Data slice = new PieChart.Data(label, proportion);
                pieChart.getData().add(slice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception SQL
        }
    }
    @FXML
    void OnClickedBack(ActionEvent event) {
        SE.changeScreen(event, "/com/example/gestionevents/Menu.fxml", "Menu");

    }
    @FXML
    void OnClickedUpdateEvent(ActionEvent event) {
        if (selectedEvenement != null) {
            try {
                // Mettez à jour les champs modifiés de l'événement sélectionné
                selectedEvenement.setPublicite_id(publiciteIDUpdate.getValue());
                selectedEvenement.setTheme_evenement(themeEventUpdate.getText());
                selectedEvenement.setType_evenement(typeEventUpdate.getText());
                selectedEvenement.setDate_debut(dateDebutUpdate.getValue().atStartOfDay());
                selectedEvenement.setDate_fin(dateFinEventUpdate.getValue().atStartOfDay());
                selectedEvenement.setNbr_participant(Integer.parseInt(nbrParticipantUpdate.getText()));

                // Appelez la méthode de service pour mettre à jour l'événement dans la base de données
                serviceEvenement.modifier(selectedEvenement);
               // OnClickedSelectImageUpdate(event);
                // Actualisez l'affichage des événements
                afficherEvenements();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez l'exception de mise à jour
            }
        }
    }

    @FXML
    void OnClickedSupprimer(ActionEvent event) {
        if (selectedEvenement != null) {
            try {
                // Appelez la méthode de service pour supprimer l'événement sélectionné
                serviceEvenement.supprimer(selectedEvenement);

                // Actualisez l'affichage des événements
                afficherEvenements();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérez l'exception de suppression
            }
        }
    }

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        serviceEvenement = new ServiceEvenement();
        // Mettez à jour la liste des publicités dans la ChoiceBox lors de l'initialisation
        List<Integer> publiciteIDs = serviceEvenement.listevenement();
        publiciteIDUpdate.setItems(FXCollections.observableArrayList(publiciteIDs));
        afficherEvenements();

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
               // Sauvegarder l'image dans le dossier Images
               String imageName = selectedFile.getName();
               File destinationFile = new File("com/example/gestionevents/Images/" + imageName);
               ImageIO.write(bufferedImage, "png", destinationFile);

               // Mettre à jour le chemin de l'image dans le modèle d'événement
               selectedEvenement.setImage_evenement(imageName);

               // Afficher l'image mise à jour dans l'interface utilisateur
               Image image = SwingFXUtils.toFXImage(bufferedImage, null);
               selectImageUpdate.setGraphic(new ImageView(image));
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }



    private void afficherEvenements() {
        EvenementVBox.getChildren().clear(); // Effacez le contenu existant

        try {
            List<Evenement> evenements = serviceEvenement.afficher();
            for (Evenement evenement : evenements) {
                String imagePath = evenement.getImage_evenement();

                // Créez un ImageView pour afficher l'image
                ImageView imageView = new ImageView();
                // Chargez l'image à partir du chemin du fichier
                try {
                    // Assurez-vous que le chemin de l'image n'est pas nul ou vide
                    if (imagePath != null && !imagePath.isEmpty()) {
                        // Chargez l'image à partir du classpath
                        InputStream inputStream = getClass().getResourceAsStream("/com/example/gestionevents/Images/" + imagePath);
                        if (inputStream != null) {
                            Image image = new Image(inputStream);
                            imageView.setImage(image);
                            // Définissez la largeur et la hauteur de l'ImageView
                            imageView.setFitWidth(200); // Ajustez si nécessaire
                            imageView.setFitHeight(200); // Ajustez si nécessaire
                            // Ajoutez l'ImageView à VBox
                            EvenementVBox.getChildren().add(imageView);
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
                        "ID de publicité : " + evenement.getPublicite_id() +
                                " Thème : " + evenement.getTheme_evenement() +
                                "  Type : " + evenement.getType_evenement() +
                                " Date de début : " + evenement.getDate_debut() +
                                " Date de fin : " + evenement.getDate_fin() +
                                " Nombre de participants : " + evenement.getNbr_participant() +
                                "");
                // Ajoutez le label à VBox
                EvenementVBox.getChildren().add(label);

                // Associez un gestionnaire d'événements de clic à chaque label pour sélectionner l'événement
                label.setOnMouseClicked(mouseEvent -> {
                    selectedEvenement = evenement;
                    // Mettez à jour les champs avec les données de l'événement sélectionné
                    publiciteIDUpdate.setValue(selectedEvenement.getPublicite_id());
                    themeEventUpdate.setText(selectedEvenement.getTheme_evenement());
                    typeEventUpdate.setText(selectedEvenement.getType_evenement());
                    dateDebutUpdate.setValue(selectedEvenement.getDate_debut().toLocalDate());
                    dateFinEventUpdate.setValue(selectedEvenement.getDate_fin().toLocalDate());
                    nbrParticipantUpdate.setText(String.valueOf(selectedEvenement.getNbr_participant()));
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérez l'exception SQL
        }
    }
}

