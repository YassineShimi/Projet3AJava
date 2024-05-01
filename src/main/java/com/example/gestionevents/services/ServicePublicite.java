package com.example.gestionevents.services;
import com.example.gestionevents.models.Publicite;
import com.example.gestionevents.interfaces.IService;
import com.example.gestionevents.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePublicite implements IService<Publicite> {
    private Connection cnx;

    public ServicePublicite() {
        cnx = MyDataBase.getInstance().getCnx();
    }


    @Override
    public void ajouter(Publicite publicite) throws SQLException {

        String req = "INSERT INTO publicite (description,type,sponsor) VALUES ( ?, ?, ?)";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, publicite.getDescription());
            pre.setString(2, publicite.getType());
            pre.setString(3, publicite.getSponsor());

            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
        }
    }

    @Override
    public void modifier(Publicite publicite) throws SQLException {
        String req = "UPDATE publicite SET description=?, type=?, sponsor=? WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setString(1, publicite.getDescription());
            pre.setString(2, publicite.getType());
            pre.setString(3, publicite.getSponsor());
            pre.setInt(4, publicite.getId());

            pre.executeUpdate();
        }

    }

    @Override
    public void supprimer(Publicite publicite) throws SQLException {
        String req = "DELETE FROM publicite WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, publicite.getId());
            pre.executeUpdate();
        }


    }


    @Override
    public List<Publicite> afficher() throws SQLException {

        String req = "SELECT * FROM publicite";
        List<Publicite> list = new ArrayList<>();
        try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {

            while (res.next()) {
                Publicite publicite = new Publicite(); // Change here
                publicite.setId(res.getInt(1));
                publicite.setDescription(res.getString(2));
                publicite.setSponsor(res.getString(3));
                publicite.setType(res.getString(4));
                list.add(publicite);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving events: " + e.getMessage());
        }
        return list;
    }


}