package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import services.ProduitsS;
import entities.Exposees;
import java.net.URL;
import java.util.ResourceBundle;

public class ProduitsController implements Initializable {

    @FXML
    private Button btnBrowse;

    @FXML
    private Button btnRecherche;

    @FXML
    private Button btnRet2;

    @FXML
    private Button btnSaveP;

    @FXML
    private Button btnSupprimerP;

    @FXML
    private Button btnUpdateP;

    @FXML
    private ScrollPane tabPr;

    @FXML
    private TextField txtDescription;

    @FXML
    private ChoiceBox<?> txtIdExpos;

    @FXML
    private ChoiceBox<?> txtIdP;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrix;

    private final ProduitsS SP = new ProduitsS();

    @FXML
    void deleteP(ActionEvent event) {

    }

    @FXML
    void recherche(ActionEvent event) {

    }

    @FXML
    void saveP(ActionEvent event) {

    }

    @FXML
    void updateP(ActionEvent event) {

    }
    @FXML
    void ret2(ActionEvent event) {
        SP.changeScreen(event, "/sample.fxml", "retour");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
