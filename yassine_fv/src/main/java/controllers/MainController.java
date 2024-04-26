package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import services.ExposeesS;
import services.ProduitsS;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button btnContactUS;

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
    private final ExposeesS SE = new ExposeesS();
    private final ProduitsS SP = new ProduitsS();

    @FXML
    void Contactus(ActionEvent event) {

    }

    @FXML
    void ExitApp(ActionEvent event) {

    }

    @FXML
    void ExportPdfEx(ActionEvent event) {

    }

    @FXML
    void ExportPdfPro(ActionEvent event) {

    }

    @FXML
    void GoToExposees(ActionEvent event) {
        String absolutePath = "/AjouterExposee.fxml";
        SE.changeScreen(event, absolutePath, "afficher exposee");
    }


    @FXML
    void GoToProduits(ActionEvent event) {
        SP.changeScreen(event, "/AjouterProduit.fxml", "afficher Produits");

    }


}