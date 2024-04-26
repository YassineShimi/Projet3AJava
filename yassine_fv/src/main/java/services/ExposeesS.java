package services;
import entities.Exposees;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tools.Myconnection;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
public class ExposeesS implements CrudInterface<Exposees> {

    private Connection cnx;

    public ExposeesS() {
        cnx = Myconnection.getInstance().getCnx();
    }


    public void create(Exposees exposees) {
        String query = "INSERT INTO exposees (nom_e, date_debut, date_fin, image_exposees) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, exposees.getNom_e());
            pst.setTimestamp(2, new Timestamp(exposees.getDateDebut().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pst.setTimestamp(3, new Timestamp(exposees.getDateFin().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pst.setString(4, exposees.getImage_exposees());

            pst.executeUpdate();
            System.out.println("Exposee ajouté avec succès");
        } catch (SQLException ex) {
            System.err.println("Erreur lors de l'ajout de l'Exposee : " + ex.getMessage());
        }
    }


    @Override
    public void update(Exposees exposees) {
        if (exposees != null) {
            String query = "UPDATE exposees SET nom_e=?, date_debut=?, date_fin=?, image_exposees=? WHERE id=?";
            try (PreparedStatement pst = cnx.prepareStatement(query)) {
                pst.setString(1, exposees.getNom_e());
                pst.setTimestamp(2, Timestamp.valueOf(exposees.getDateDebut()));
                pst.setTimestamp(3, Timestamp.valueOf(exposees.getDateFin()));

                pst.setString(4, exposees.getImage_exposees());
                pst.setInt(5, exposees.getId());           // Set ID for WHERE clause

                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Exposee mis à jour avec succès");
                } else {
                    System.out.println("Aucun Exposee trouvé avec l'ID : " + exposees.getId());
                }
            } catch (SQLException ex) {
                System.err.println("Erreur lors de la mise à jour de l'exposee : " + ex.getMessage());
            }
        } else {
            System.err.println("L'objet exposees est null.");
        }
    }


    @Override
    public void delete(Exposees exposees) throws SQLException {

        String req = "DELETE FROM exposees WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, exposees.getId());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting event: " + e.getMessage());
        }

    }
    @Override
    public Exposees getById(int id) {
        String query = "SELECT * FROM exposees WHERE id=?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    Exposees exposees = new Exposees();
                    exposees.setId(rs.getInt("id"));
                    exposees.setImage_exposees(rs.getString("nom_e"));
                    exposees.setDateDebut(LocalDateTime.parse(rs.getString("date_debut")));
                    exposees.setDateFin(LocalDateTime.parse(rs.getString("date_fin")));
                    exposees.setImage_exposees(rs.getString("image_exposees"));

                    return exposees;
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération de l'avis : " + ex.getMessage());
        }
        return null;
    }
    public ObservableList<Integer> listeexposees() {
        ObservableList<Integer> ids = FXCollections.observableArrayList();
        String req = "SELECT id FROM exposees";
        try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                int id = res.getInt(1);
                ids.add(id);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving exposee IDs: " + e.getMessage());
        }
        return ids;
    }
    @Override
    public List<Exposees> getAll()  {
        List<Exposees> exposeesList = new ArrayList<>();
        String query = "SELECT * FROM exposees";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Exposees exposees = new Exposees();
                    exposees.setId(rs.getInt("id"));
                    exposees.setImage_exposees(rs.getString("nom_e"));
                    exposees.setDateDebut(LocalDateTime.parse(rs.getString("date_debut")));
                    exposees.setDateFin(LocalDateTime.parse(rs.getString("date_fin")));
                    exposees.setImage_exposees(rs.getString("image_exposees"));

                    exposeesList.add(exposees);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la récupération des exposees : " + ex.getMessage());
        }
        return exposeesList;
    }

    @Override
    public void ajouter(Exposees exposees) throws SQLException {

    }

    @Override
    public void modifier(Exposees exposees) throws SQLException {

    }

    @Override
    public void supprimer(Exposees exposees) throws SQLException {

    }

    public List<Integer> listeexposees1() {
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
    public List<Exposees> afficher() throws SQLException {

        String req = "SELECT * FROM exposees";

        List<Exposees> list = new ArrayList<>();
        try (Statement ste = cnx.createStatement(); ResultSet rs = ste.executeQuery(req)) {
            while (rs.next()) {
                Exposees exposees = new Exposees();
                exposees.setId(rs.getInt("id"));
                exposees.setImage_exposees(rs.getString("nom_e"));
                exposees.setDateDebut(LocalDateTime.parse(rs.getString("date_debut")));
                exposees.setDateFin(LocalDateTime.parse(rs.getString("date_fin")));
                exposees.setImage_exposees(rs.getString("image_exposees"));


                list.add(exposees);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving events: " + e.getMessage());
        }
        return list;
    }
    public void changeScreen(ActionEvent event, String path, String title) {
        try {
            // Load FXML file using absolute path
            FXMLLoader loader = new FXMLLoader(new File(path).toURI().toURL());
            Parent root = loader.load();

            // Set up the stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}