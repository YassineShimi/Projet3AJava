package services;
import entities.Exposees;
import entities.Produits;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.MainClass;
import tools.Myconnection;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitsService implements CrudInterface<Produits>{

        private Connection cnx;
        public ProduitsService() {
            cnx = Myconnection.getInstance().getCnx();
        }
    @Override
    public void ajouter(Produits produits) throws SQLException {
        String req = "INSERT INTO produits (description, prix, ressource, exposees_id) VALUES (?,?,?,?)";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, produits.getDescription());
            pre.setString(2, produits.getPrix());
            pre.setString(3, produits.getRessource());
            pre.setInt(4, produits.getExposees_id());
            pre.executeUpdate();
        }
    }
    public void modifier(Produits produits) throws SQLException {
        String req = "UPDATE produits SET description=?, prix=?, ressource=?, exposees_id=? WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, produits.getDescription());
            pre.setString(2, produits.getPrix());
            pre.setString(3, produits.getRessource());
            pre.setInt(4, produits.getExposees_id());
            pre.setInt(5, produits.getId());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error modifying event: " + e.getMessage());
            throw e;
        }
    }
        @Override
        public void supprimer(Produits produits) throws SQLException {
            String req = "DELETE FROM produits WHERE id=?";
            try (PreparedStatement pre = cnx.prepareStatement(req)) {
                pre.setInt(1, produits.getId());
                pre.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Error deleting produit: " + e.getMessage());
            }

        }
        public List<Integer> listproduitstid() {
            String req = "SELECT id FROM produits";
            List<Integer> list = new ArrayList<>();
            try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {
                while (res.next()) {
                    int id = res.getInt(1); // Use index 1 to get the value of the first (and only) column
                    list.add(id);
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving produits id : " + e.getMessage());
            }
            return list;
        }
    public int getExpoId(String expoName) throws SQLException {
        int expoId = -1;
        String query = "SELECT id FROM exposees WHERE nom_e = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, expoName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    expoId = resultSet.getInt("id");
                }
            }
        }
        return expoId;
    }
    public String getExpoName(int expoId) throws SQLException {
        String expoName = null;
        String query = "SELECT nom_e FROM exposees WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, expoId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    expoName = resultSet.getString("nom_e");
                }
            }
        }
        return expoName;
    }

        @Override
        public List<Produits> afficher() throws SQLException {

            String req = "SELECT produits.*, produits.exposees_id " +
                    "FROM produits " +
                    " INNER JOIN exposees  ON produits.exposees_id = exposees.id ";

            List<Produits> list = new ArrayList<>();
            try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {
                while (res.next()) {
                    Produits produits = new Produits();
                    produits.setId(res.getInt(1));
                    produits.setDescription(res.getString(2));
                    produits.setPrix(res.getString(3));
                    produits.setRessource(res.getString(4));
                    produits.setExposees_id(res.getInt(5));
                    list.add(produits);
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
    public List<Produits> searchByCriteria(String keyword) throws SQLException {
        List<Produits> searchResults = new ArrayList<>();
        String query = "SELECT * FROM produits WHERE description LIKE ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setString(1, "%" + keyword + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Produits produit = new Produits();
                    produit.setId(resultSet.getInt("id"));
                    produit.setDescription(resultSet.getString("description"));
                    produit.setPrix(resultSet.getString("prix"));
                    produit.setRessource(resultSet.getString("ressource"));
                    produit.setExposees_id(resultSet.getInt("exposees_id"));
                    searchResults.add(produit);
                }
            }
        }
        return searchResults;
    }



}
