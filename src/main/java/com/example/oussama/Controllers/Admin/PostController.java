package com.example.oussama.Controllers.Admin;


import com.example.oussama.HelloApplication;
import com.example.oussama.models.Post;
import com.example.oussama.models.Threads;
import com.example.oussama.services.PostService;
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

public class PostController {
    @FXML
    ListView<Post> listview;
    @FXML
    public void initialize(int id) {

        afficher(id);

    }

    private  final PostService postService=new PostService();
    void afficher(int id ) {
        listview.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Post> call(ListView<Post> param) {
                return new ListCell<>() {
                    private final HBox h1 = new HBox();

                    private final Label nomLael = new Label();
                    private final Label Datedebu = new Label();
                    private final Label datefin = new Label();





                    {

                        nomLael.setPrefWidth(150.0);
                        Datedebu.setPrefWidth(150.0);
                        datefin.setPrefWidth(150.0);
                        h1.setSpacing(10);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }



                    @Override
                    protected void updateItem(Post item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            nomLael.setText( item.getTitle().toString());
                            Datedebu.setText(String.valueOf(item.getUsername_id()));
                            datefin.setText(String.valueOf(item.getCreatedat()));
                            h1.getChildren().setAll(nomLael, Datedebu,datefin);
                            setGraphic(h1);
                        }
                    }
                };
            }
        });
        try {
            listview.getItems().setAll(postService.getByIdUser(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
     @FXML
    void gothread() throws IOException {
    Scene scenefer = listview.getScene();
    Stage stagefer = (Stage) scenefer.getWindow();
    stagefer.close();
    Stage stage = new Stage();
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Admin/Adminthreads.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("");
    stage.setScene(scene);
    stage.show();
}
}
