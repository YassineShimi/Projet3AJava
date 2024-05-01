package com.example.gestionevents.services;
import com.example.gestionevents.interfaces.IService;
import com.example.gestionevents.models.Participant;
import com.example.gestionevents.utils.MyDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceParticipant implements IService<Participant> {

private Connection cnx;
    public ServiceParticipant() {
        cnx = MyDataBase.getInstance().getCnx();
    }
    @Override
    public void ajouter(Participant participant) throws SQLException {
        String req = "INSERT INTO participant (evenement_id , description, date_participation) VALUES (?, ?,?)";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, participant.getEvenement_id());
            pre.setString(2, participant.getDescription());
            pre.setString(3, String.valueOf(participant.getDate_participation()));
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding event: " + e.getMessage());
        }

    }

    @Override
    public void modifier(Participant participant) throws SQLException {
        String req = "UPDATE participant SET evenement_id=?, description=?, date_participation=? WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, participant.getEvenement_id());
            pre.setString(2, participant.getDescription());
            pre.setString(3, String.valueOf(participant.getDate_participation()));
            pre.setInt(4, participant.getId());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error modifying event: " + e.getMessage());
        }

    }



    @Override
    public void supprimer(Participant participant) throws SQLException {
        String req = "DELETE FROM participant WHERE id=?";
        try (PreparedStatement pre = cnx.prepareStatement(req)) {
            pre.setInt(1, participant.getId());
            pre.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting event: " + e.getMessage());
        }

    }

    public List<Integer> listevenementid() {
        String req = "SELECT id FROM evenement";
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
    public List<Participant> afficher() throws SQLException {
      /*  String req = "SELECT evenement.*, evenement.publicite_id " +
                "FROM evenement " +
                " INNER JOIN publicite  ON evenemnt.publicite_id = publicite.id ";*/
        String req = "SELECT participant.*, participant.evenement_id " +
                "FROM participant " +
                " INNER JOIN evenement  ON participant.evenement_id = evenement.id ";

        List<Participant> list = new ArrayList<>();
        try (Statement ste = cnx.createStatement(); ResultSet res = ste.executeQuery(req)) {
            while (res.next()) {
                Participant participant = new Participant();
                participant.setId(res.getInt(1));
                participant.setEvenement_id(res.getInt(2));
                participant.setDescription(res.getString(3));
                participant.setDate_participation(res.getTimestamp(4).toLocalDateTime());
                list.add(participant);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving events: " + e.getMessage());
        }
        return list;
    }
}
