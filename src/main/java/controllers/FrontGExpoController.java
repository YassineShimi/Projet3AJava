package controllers;

import services.ExposeesService;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class FrontGExpoController implements Initializable{

    @FXML
    private Button BtnExpo;

    @FXML
    private VBox ExposeesVBox;

    @FXML
    private Label Menu;

    @FXML
    private Label MenuBack;

    @FXML
    private AnchorPane anchoraffichageEx;

    @FXML
    private Button btnProduit;

    @FXML
    private Label labelexposee;

    @FXML
    private Label labelproduit;

    @FXML
    private VBox produitsVbox;

    @FXML
    private Button retourFrontBtn;

    @FXML
    private AnchorPane slider;


    private final ExposeesService SE = new ExposeesService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.slider.setTranslateX(-176.0);
        this.Menu.setOnMouseClicked((MouseEvent event) -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(this.slider);
            slide.setToX(0.0);
            slide.play();
            this.slider.setTranslateX(-176.0);
            slide.setOnFinished((e) -> {
                this.Menu.setVisible(false);
                this.MenuBack.setVisible(true);
            });
        });
        this.MenuBack.setOnMouseClicked((MouseEvent event) -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(this.slider);
            slide.setToX(-176.0);
            slide.play();
            this.slider.setTranslateX(0.0);
            slide.setOnFinished((e) -> {
                this.Menu.setVisible(true);
                this.MenuBack.setVisible(false);
            });
        });
    }

    @FXML
    void btnRetFront(ActionEvent event) {
        SE.changeScreen(event, "/menuExpo.fxml", "Retour");
    }

    @FXML
    void consulterExpositions(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherExposeesFR.fxml"));
            AnchorPane exposeesAnchorPane = loader.load();
            anchoraffichageEx.getChildren().setAll(exposeesAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void consulterProduits(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherProduitFR.fxml"));
            AnchorPane produitsAnchorPane = loader.load();
            anchoraffichageEx.getChildren().setAll(produitsAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
