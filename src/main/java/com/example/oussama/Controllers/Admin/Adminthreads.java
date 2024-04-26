package com.example.oussama.Controllers.Admin;

import com.example.oussama.models.Threads;
import com.example.oussama.services.ThreadServicss;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;

public class Adminthreads {
    @FXML
    ListView<Threads> listview;
    @FXML
    public void initialize() {

        afficher();

    }

    private  final ThreadServicss threadServicss=new ThreadServicss();
    void afficher() {
        listview.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Threads> call(ListView<Threads> param) {
                return new ListCell<>() {
                    private final HBox h1 = new HBox();

                    private final Label nomLael = new Label();
                    private final Label Datedebu = new Label();
                    private final Label datefin = new Label();



                    private final Button products = new Button("Posts");

                    {

                        nomLael.setPrefWidth(150.0);
                        Datedebu.setPrefWidth(150.0);
                        datefin.setPrefWidth(150.0);
                        h1.setSpacing(10);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }



                    @Override
                    protected void updateItem(Threads item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            nomLael.setText( item.getTitle().toString());
                            Datedebu.setText(String.valueOf(item.getAuthor()));
                            datefin.setText(String.valueOf(item.getCreatedat()));
                            h1.getChildren().setAll(nomLael, Datedebu,datefin,products);
                            products.setOnAction(event -> {
                                Scene scene1 = listview.getScene();
                                Stage stage1 = (Stage) scene1.getWindow();
                                stage1.close();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/Posts.fxml"));
                                Parent root = null;
                                try {
                                    root = loader.load();
                                } catch (IOException ex) {
                                    throw new RuntimeException(ex);
                                }
                                PostController controller = loader.getController();
                                 controller.initialize(getItem().getId());
                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.setTitle("Posts");
                                stage.show();
                            });


                            setGraphic(h1);
                        }
                    }
                };
            }
        });
        try {
            listview.getItems().setAll(threadServicss.getAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
