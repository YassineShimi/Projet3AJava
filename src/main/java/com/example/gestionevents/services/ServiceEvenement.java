package com.example.gestionevents.services;

import com.example.gestionevents.interfaces.IService;
import com.example.gestionevents.models.Evenement;
import com.example.gestionevents.test.HelloApplication;
import com.example.gestionevents.utils.MyDataBase;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ServiceEvenement implements IService<Evenement> {
    private Connection cnx;

    public ServiceEvenement() {
        cnx = MyDataBase.getInstance().getCnx();
    }


    @Override
    public void ajouter(Evenement evenement) throws SQLException {

        String req = "INSERT INTO evenement (publicite_id,image_evenement,theme_evenement,type_evenement,date_debut,date_fin,nbr_participant) VALUES (?,  ?, ?,?,?,?,?)";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, evenement.getPublicite_id());
            pre.setString(2, evenement.getImage_evenement());
            pre.setString(3, evenement.getTheme_evenement());
            pre.setString(4, evenement.getType_evenement());
            pre.setString(5, String.valueOf(evenement.getDate_debut()));
            pre.setString(6, String.valueOf(evenement.getDate_fin()));
            pre.setInt(7, evenement.getNbr_participant());


            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
        }

    }

    @Override
    public void modifier(Evenement evenement) throws SQLException {
        String req = "UPDATE evenement SET publicite_id=?, image_evenement=?, theme_evenement=?, type_evenement=?, date_debut=?,date_fin=?,nbr_participant=? WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, evenement.getPublicite_id());
            pre.setString(2, evenement.getImage_evenement());
            pre.setString(3, evenement.getTheme_evenement());
            pre.setString(4, evenement.getType_evenement());
            pre.setString(5, String.valueOf(evenement.getDate_debut()));
            pre.setString(6, String.valueOf(evenement.getDate_fin()));
            pre.setInt(7, evenement.getNbr_participant());
            pre.setInt(8, evenement.getId());

            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error modifying event: " + e.getMessage());
        }

    }

    public String getEventNameById(int eventId) throws SQLException {
        String req = "SELECT theme_evenement FROM evenement WHERE id = ?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, eventId);
            try (ResultSet res = pre.executeQuery()) {
                if (res.next()) {
                    return res.getString("nom");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving event name by ID: " + e.getMessage());
        }
        return null;
    }

    public List<String> listEventNames() throws SQLException {
        List<String> eventNames = new ArrayList<>();
        String query = "SELECT theme_evenement FROM evenement";
        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                eventNames.add(resultSet.getString("theme_evenement"));
            }
        }
        return eventNames;
    }

    public int getEventId(String eventName) throws SQLException {
        int eventId = -1;
        String query = "SELECT id FROM evenement WHERE theme_evenement = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, eventName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    eventId = resultSet.getInt("id");
                }
            }
        }
        return eventId;
    }

    public String getEventName(int eventId) throws SQLException {
        String eventName = null;
        String query = "SELECT theme_evenement FROM evenement WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, eventId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    eventName = resultSet.getString("theme_evenement");
                }
            }
        }
        return eventName;
    }

    public List<Integer> listevenement() {
        String req = "SELECT id FROM publicite";
        List<Integer> list = new ArrayList<>();
        try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                int id = res.getInt(1); // Use index 1 to get the value of the first (and only) column
                list.add(id);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving publicite id : " + e.getMessage());
        }
        return list;
    }

    @Override
    public void supprimer(Evenement evenement) throws SQLException {

        String req = "DELETE FROM evenement WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, evenement.getId());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting event: " + e.getMessage());
        }

    }

    @Override
    public List<Evenement> afficher() throws SQLException {
       /* String req = "SELECT evenement.*, evenement.publicite_id " +
                "FROM evenement " +
                " INNER JOIN publicite  ON evenemnt.publicite_id = publicite.id ";*/
        String req = "SELECT evenement.*, evenement.publicite_id " +
                "FROM evenement " +
                " INNER JOIN publicite ON evenement.publicite_id = publicite.id ";

        List<Evenement> list = new ArrayList<>();
        try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                Evenement evenement = new Evenement();
                evenement.setId(res.getInt(1));
                evenement.setPublicite_id(res.getInt(2));
                evenement.setImage_evenement(res.getString(3));
                evenement.setTheme_evenement(res.getString(4));
                evenement.setType_evenement(res.getString(5));
                evenement.setDate_debut(res.getTimestamp(6).toLocalDateTime());
                evenement.setDate_fin(res.getTimestamp(7).toLocalDateTime());
                evenement.setNbr_participant(res.getInt(8));

                list.add(evenement);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving events: " + e.getMessage());
        }
        return list;
    }

    public void changeScreen(ActionEvent event, String s, String dashboard) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(s));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(dashboard);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Evenement> afficherbyNOM(String tri) {
        String req = "SELECT evenement.*, evenement.publicite_id " +
                "FROM evenement " +
                " INNER JOIN publicite ON evenement.publicite_id = publicite.id ";

        List<Evenement> list = new ArrayList<>();
        try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                Evenement evenement = new Evenement();
                evenement.setId(res.getInt(1));
                evenement.setPublicite_id(res.getInt(2));
                evenement.setImage_evenement(res.getString(3));
                evenement.setTheme_evenement(res.getString(4));
                evenement.setType_evenement(res.getString(5));
                evenement.setDate_debut(res.getTimestamp(6).toLocalDateTime());
                evenement.setDate_fin(res.getTimestamp(7).toLocalDateTime());
                evenement.setNbr_participant(res.getInt(8));

                list.add(evenement);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving events: " + e.getMessage());
        }
        if (tri.equals("ASC")) {
            Collections.sort(list, Comparator.comparing(Evenement::getNbr_participant));
        } else {
            Collections.sort(list, Comparator.comparing(Evenement::getNbr_participant).reversed());
        }
        return list;
    }
}
