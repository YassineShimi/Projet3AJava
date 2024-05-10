package controllers;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import services.ExposeesService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Timer;
import entities.Exposees;
import java.util.TimerTask;
public class NotificationController {
    @FXML Button btn;
    @FXML ImageView notificationsImage;
    @FXML TextField textField;
    @FXML ComboBox<NotificationType> noteType;
    @FXML private Button retour;
    @FXML
    private DatePicker datePicker;
    NotificationsStage notificationsStage = new NotificationsStage();
    private Timer notificationTimer;

    private final ExposeesService SE = new ExposeesService();

    @FXML
    public void initialize() {
        noteType.getItems().addAll(NotificationType.values());
        noteType.valueProperty().setValue(NotificationType.INFO);
        notificationsImage.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY))
                openNotificationsStage();
        });
        textField.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER))
                    btn.fire();
            }
        });
        btn.setOnAction(e -> {
            notificationsStage.addNote(textField.getText(), noteType.getValue());
            textField.clear();
        });
        startNotificationTimer();

    }
    private void openNotificationsStage() {
        if (notificationsStage.isShowing())
            notificationsStage.hide();
        else
            notificationsStage.show();
    }

    private void startNotificationTimer() {
        notificationTimer = new Timer();
        notificationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkForExpoNotifications();
            }
        }, 0, 24 * 60 * 60 * 1000); // Check once every 24 hours (adjust as needed)
    }
    private void checkForExpoNotifications() {
        LocalDate today = LocalDate.now();
        // Query exposés table to find exposés with date_debut equal to today
        try {
            ExposeesService exposeesService = new ExposeesService();
            List<Exposees> exposToday = exposeesService.getExposForDate(today);
            for (Exposees expo : exposToday) {
                // Construct notification message
                String message = "Today's exposition: " + expo.getNom_e();
                // Add notification
                notificationsStage.addNote(message, NotificationType.INFO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
    protected void onHelloButtonClick() {
        Notifications notifications = Notifications.create()
                .title("Download Complete!")
                .text("Saved to /home/downloads")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(e -> {})
                .darkStyle();

        notifications.show();
    }
*/

    @FXML
    void retourMenu(ActionEvent event) {
        SE.changeScreen(event, "/menuExpo.fxml", "Thakafa");

    }
}
