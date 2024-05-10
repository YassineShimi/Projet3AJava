package controllers;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by karpenko on 25.08.2022.
 * Description:
 */
/**
 * Source code Modified by YassineShimi on 06/05/2024,Tunis
 * Description: Mysql Database interactions added , more dynamic functionalities and more
 */
public class NotificationsStage extends Stage {

    private final VBox noteList = new VBox();
    private final ScrollPane scrollPane;
    private AnchorPane rootPane = new AnchorPane();
    TranslateTransition openTransition = new TranslateTransition(new Duration(500), rootPane);
    TranslateTransition closeTransition = new TranslateTransition(new Duration(500), rootPane);
    private final Label clear = new Label("Clear");
    private final Label close = new Label("Fermer");
    private HBox buttonsBox = new HBox(clear, close);
    private Timer timer;

    public NotificationsStage() {
        initStyle(StageStyle.TRANSPARENT);
        initModality(Modality.NONE);
        initStyles();
        Scene scene = new Scene(rootPane, Color.TRANSPARENT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/stylesExpo.css")).toExternalForm());
        setScene(scene);
        BorderPane borderPane = new BorderPane();
        setAnchors(borderPane);
        buttonsBox.setSpacing(30.0);
        buttonsBox.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setBottom(buttonsBox);
        buttonsBox.setAlignment(Pos.CENTER);
        rootPane.getChildren().setAll(borderPane);
        scrollPane = new ScrollPane(noteList);
        scrollPane.getStyleClass().add("notes-scroll-pane");
        scrollPane.setFitToWidth(true);
        scrollPane.hbarPolicyProperty().set(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.vbarPolicyProperty().set(ScrollPane.ScrollBarPolicy.NEVER);
        borderPane.setCenter(scrollPane);
        BorderPane.setAlignment(scrollPane, Pos.BOTTOM_RIGHT);
        noteList.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setTop(slider());
        noteList.prefHeightProperty().bind(scrollPane.heightProperty().subtract(2));
        noteList.prefWidthProperty().bind(scrollPane.widthProperty());
        initTransition();
        close.setOnMouseClicked(e -> hide());
        clear.setOnMouseClicked(e -> clear());
    }

    private Slider slider() {
        Slider slider = new Slider(0.0, 1.0, 0.6);
        slider.valueProperty().addListener((o, oldVal, newVal) -> {
            rootPane.setStyle(String.format(Locale.ENGLISH, "-fx-background-color: rgba(164, 164, 164, %f)", newVal.doubleValue()));
        });
        slider.getStyleClass().add("opacity-slider");
        return slider;
    }

    private void initTransition() {
        openTransition.setToX(0);
        closeTransition.setToX(400);
        closeTransition.setOnFinished(e -> {
            super.hide();
        });
        setOnShowing(e -> {
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            setX(bounds.getMaxX() - 400);
            setY(0);
            setHeight(bounds.getHeight());
            setWidth(800);
            rootPane.setTranslateX(400);
            openTransition.play();
        });
    }

    private void addNotes() {
        timer = new Timer();
        AtomicInteger count = new AtomicInteger(1);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                            controllers.Notification notification = new controllers.Notification("HELLO WORLD " + count.getAndIncrement(), NotificationType.INFO);
                            notification.setOnRemove(() -> noteList.getChildren().remove(notification));
                            noteList.getChildren().add(notification);
                            scrollPane.setVvalue(scrollPane.getVmax());
                        }
                );
            }
        }, 500, 1000);
    }

    private void initStyles() {
        clear.getStyleClass().add("hyperlink-label");
        close.getStyleClass().add("hyperlink-label");
        rootPane.getStyleClass().add("root-pane");
        noteList.getStyleClass().add("note-list");
    }

    private void clear() {
        noteList.getChildren().clear();
    }

    private void setAnchors(BorderPane borderPane) {
        AnchorPane.setLeftAnchor(borderPane, 0.0);
        AnchorPane.setRightAnchor(borderPane, 0.0);
        AnchorPane.setTopAnchor(borderPane, 0.0);
        AnchorPane.setBottomAnchor(borderPane, 0.0);
    }

    @Override
    public void hide() {
        closeTransition.play();
    }

    public void addNote(String text, NotificationType type) {
        controllers.Notification notification = new controllers.Notification(text, type);
        notification.setOnRemove(() -> noteList.getChildren().remove(notification));
        noteList.getChildren().add(notification);
        scrollPane.setVvalue(scrollPane.getVmax());
    }
}
