package com.example.oussama.services;

import com.example.oussama.Util.DataBase;
import com.example.oussama.models.Threads;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadServicss implements IService<Threads> {

    private Connection connection;

    public ThreadServicss() {
        connection = DataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Threads thread) throws SQLException {
        String req = "INSERT INTO thread (title, topic, createdat, author, likes) " +
                "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement os = connection.prepareStatement(req);
        os.setString(1, thread.getTitle());
        os.setString(2, thread.getTopic());
        os.setTimestamp(3, Timestamp.valueOf(thread.getCreatedat()));
        os.setString(4, thread.getAuthor());
        os.setInt(5, thread.getLikes());
        os.executeUpdate();
        System.out.println("Thread ajouté avec succès");
    }

    @Override
    public void modifier(Threads thread) throws SQLException {
        String req = "UPDATE thread SET title = ?, topic = ?, createdat = ?, author = ?, likes = ? " +
                "WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setString(1, thread.getTitle());
        os.setString(2, thread.getTopic());
        os.setTimestamp(3, Timestamp.valueOf(thread.getCreatedat()));
        os.setString(4, thread.getAuthor());
        os.setInt(5, thread.getLikes());
        os.setInt(6, thread.getId());
        os.executeUpdate();
        System.out.println("Thread modifié avec succès");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM thread WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        os.executeUpdate();
        System.out.println("Thread supprimé avec succès");
    }

    @Override
    public Threads getOneById(int id) throws SQLException {
        String req = "SELECT * FROM thread WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        if (rs.next()) {
            Threads thread = new Threads();
            thread.setId(rs.getInt("id"));
            thread.setTitle(rs.getString("title"));
            thread.setTopic(rs.getString("topic"));
            thread.setCreatedat(rs.getTimestamp("createdat").toLocalDateTime());
            thread.setAuthor(rs.getString("author"));
            thread.setLikes(rs.getInt("likes"));
            return thread;
        }
        return null;
    }

    @Override
    public List<Threads> getAll() throws SQLException {
        List<Threads> threads = new ArrayList<>();
        String req = "SELECT * FROM thread";
        PreparedStatement os = connection.prepareStatement(req);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Threads thread = new Threads();
            thread.setId(rs.getInt("id"));
            thread.setTitle(rs.getString("title"));
            thread.setTopic(rs.getString("topic"));
            thread.setCreatedat(rs.getTimestamp("createdat").toLocalDateTime());
            thread.setAuthor(rs.getString("author"));
            thread.setLikes(rs.getInt("likes"));
            threads.add(thread);
        }
        return threads;
    }

    @Override
    public List<Threads> getByIdUser(int id) throws SQLException {
        return null;
    }

    // Les autres méthodes de la classe IService ne sont pas implémentées pour cette classe
}
