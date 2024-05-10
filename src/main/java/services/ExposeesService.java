package services;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import services.CrudInterface;
import java.time.LocalDate;
import entities.Exposees;
import main.MainClass;
import tools.Myconnection;

public class ExposeesService implements CrudInterface<Exposees> {

        private Connection cnx;

        public ExposeesService() {
            cnx = Myconnection.getInstance().getCnx();
        }



        @Override
        public void ajouter(Exposees exposees) throws SQLException {

            String query = "INSERT INTO exposees (nom_e, date_debut, date_fin, image_exposees) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setString(1, exposees.getNom_e());
                pst.setTimestamp(2, new Timestamp(exposees.getDateDebut().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
                pst.setTimestamp(3, new Timestamp(exposees.getDateFin().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
                pst.setString(4, exposees.getImage_exposees());

                pst.executeUpdate();
                System.out.println("Exposition ajoutée avec succès");
            } catch (SQLException ex) {
                System.err.println("Erreur lors de l'ajout de l'Exposee : " + ex.getMessage());
            }
        }

    @Override
    public void modifier(Exposees exposees) throws SQLException {
        String req = "UPDATE exposees SET nom_e=?, date_debut=?, date_fin=?, image_exposees=? WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(req)) {
            pst.setString(1, exposees.getNom_e());
            pst.setTimestamp(2, new Timestamp(exposees.getDateDebut().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pst.setTimestamp(3, new Timestamp(exposees.getDateFin().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pst.setString(4, exposees.getImage_exposees());
            pst.setInt(5, exposees.getId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Exposition modifié avec succès");
            } else {
                System.out.println("Aucune exposition n'a été modifiée.");
            }
        } catch (SQLException e) {
            System.err.println("Error modifying expo: " + e.getMessage());
        }
    }
    public List<Exposees> searchByName(String keyword) throws SQLException {
        List<Exposees> searchResults = new ArrayList<>();
        String query = "SELECT * FROM exposees WHERE nom_e LIKE ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, "%" + keyword + "%");
            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    Exposees exposees = new Exposees();
                    exposees.setId(resultSet.getInt("id"));
                    exposees.setNom_e(resultSet.getString("nom_e"));
                    exposees.setDateDebut(resultSet.getTimestamp("date_debut").toLocalDateTime());
                    exposees.setDateFin(resultSet.getTimestamp("date_fin").toLocalDateTime());
                    exposees.setImage_exposees(resultSet.getString("image_exposees"));
                    searchResults.add(exposees);
                }
            }
        }
        return searchResults;
    }

    public List<Integer> listexpositions() {
            String req = "SELECT id FROM exposees";
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
        public void supprimer(Exposees exposees) throws SQLException {

            String req = "DELETE FROM exposees WHERE id=?";
            try (PreparedStatement pre = cnx.prepareStatement(req)) {
                pre.setInt(1, exposees.getId());
                pre.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error deleting exposition: " + e.getMessage());
            }

        }

        @Override
        public List<Exposees> afficher() throws SQLException {

            String req = "SELECT * FROM exposees";

            List<Exposees> list = new ArrayList<>();
            try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {
                while (res.next()) {
                    Exposees exposees = new Exposees();
                    exposees.setId(res.getInt(1));
                    exposees.setNom_e(res.getString(2));
                    exposees.setDateDebut(res.getTimestamp(3).toLocalDateTime());
                    exposees.setDateFin(res.getTimestamp(4).toLocalDateTime());
                    exposees.setImage_exposees(res.getString(5));

                    list.add(exposees);
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving events: " + e.getMessage());
            }
            return list;
        }

        public void changeScreen(ActionEvent event, String s, String dashboard) {
            try {
                FXMLLoader loader = new FXMLLoader(MainClass.class.getResource(s));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle(dashboard);
                stage.setScene(new Scene(root));
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    public List<String> listExpoNames() throws SQLException {
        List<String> expoName = new ArrayList<>();
        String query = "SELECT nom_e FROM exposees";
        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                expoName.add(resultSet.getString("nom_e"));
            }
        }
        return expoName;
    }
    public List<Exposees> getExposForDate(LocalDate date) throws SQLException {
        List<Exposees> exposForDate = new ArrayList<>();
        String query = "SELECT * FROM exposees WHERE DATE(date_debut) = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    Exposees expo = new Exposees();
                    expo.setId(resultSet.getInt("id"));
                    expo.setNom_e(resultSet.getString("nom_e"));
                    expo.setDateDebut(resultSet.getTimestamp("date_debut").toLocalDateTime());
                    expo.setDateFin(resultSet.getTimestamp("date_fin").toLocalDateTime());
                    expo.setImage_exposees(resultSet.getString("image_exposees"));
                    exposForDate.add(expo);
                }
            }
        }
        return exposForDate;
    }
    }


