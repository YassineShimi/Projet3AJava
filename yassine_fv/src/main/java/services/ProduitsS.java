package services;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Main;
import tools.Myconnection;
import entities.Produits;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitsS implements CrudInterface<Produits>{
        private Connection cnx;

        public ProduitsS() {
            cnx = Myconnection.getInstance().getCnx();
        }


    @Override
    public void create(Produits Produit) {
        String req = "INSERT INTO produits (description,prix) VALUES ( ?, ?, ?)";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, Produit.getDescription());
            pre.setString(2, Produit.getPrix());

            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
        }
    }

    @Override
    public void update(Produits Produit) {
        String req = "UPDATE produits SET description=?, prix=? WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, Produit.getDescription());
            pre.setString(2, Produit.getPrix());

            pre.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void delete(Produits Produit) throws SQLException {
        String req = "DELETE FROM produits WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, Produit.getId());
            pre.executeUpdate();
        }


    }
    @Override
    public Produits getById(int id) {
        return null;
    }

    @Override
    public List<Produits> getAll() {
        return null;
    }

    @Override
    public void ajouter(Produits produits) throws SQLException {

    }

    @Override
    public void modifier(Produits produits) throws SQLException {

    }

    @Override
    public void supprimer(Produits produits) throws SQLException {

    }


    @Override
        public List<Produits> afficher() throws SQLException {

            String req = "SELECT * FROM produits";
            List<Produits> list = new ArrayList<>();
            try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {

                while (res.next()) {
                    Produits produits = new Produits(); // Change here
                    produits.setId(res.getInt(1));
                    produits.setDescription(res.getString(2));
                    produits.setDescription(res.getString(3));
                    list.add(produits);
                }

            } catch (SQLException e) {
                System.err.println("Error retrieving events: " + e.getMessage());
            }
            return list;
        }




    public void changeScreen(ActionEvent event, String s, String dashboard) {


        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(s));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(dashboard);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
