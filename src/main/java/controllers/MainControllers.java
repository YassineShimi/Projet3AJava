package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import services.ExposeesService;
import services.ProduitsService;
import javafx.scene.control.Button;

public class MainControllers {

    @FXML
    private Button btnNotif;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnGexposees;

    @FXML
    private Button btnGproduits;

    @FXML
    private Button btnPDFEx;

    @FXML
    private Button btnPDFProd;
    @FXML
    private Button btnFront;

    private final ExposeesService SE = new ExposeesService();
    private final ProduitsService SP = new ProduitsService();

    @FXML
    void Contactus(ActionEvent event) {

    }

    @FXML
    void ExitApp(ActionEvent event) {

    }

    @FXML
    void ExportPdfEx(ActionEvent event) {
        SE.changeScreen(event, "/AfficherExposees.fxml", "afficher Produits");

    }

    @FXML
    void ExportPdfPro(ActionEvent event) {
        SE.changeScreen(event, "/AfficherProduits.fxml", "afficher Produits");

    }

    @FXML
    void GoToExposees(ActionEvent event) {
        String absolutePath = "/AjouterExposees.fxml";
        SE.changeScreen(event, absolutePath, "afficher exposee");
    }

    @FXML
    void GoToProduits(ActionEvent event) {
        SE.changeScreen(event, "/AjouterProduits.fxml", "afficher Produits");

    }
    @FXML
    void notifAction(ActionEvent event) {
        SE.changeScreen(event, "/Notifications.fxml", "sauvegarder notification");

    }
    @FXML
    void goToFront(ActionEvent event) {
        SE.changeScreen(event, "/FrontGExpo.fxml", "Bienvenu A Thakafa!");

    }

}