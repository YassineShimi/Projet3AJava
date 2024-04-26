package com.example.oussama.services;

import com.example.oussama.Util.DataBase;
import com.example.oussama.models.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostService implements IService<Post> {

    private Connection connection;

    public PostService() {
        connection = DataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Post post) throws SQLException {
        String req = "INSERT INTO post (username_id, title, content, quote, image, rating, video, createdat, visible, threadid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, post.getUsername_id());
        os.setString(2, post.getTitle());
        os.setString(3, post.getContent());
        os.setString(4, post.getQuote());
        os.setString(5, post.getImage());
        os.setInt(6, post.getRating());
        os.setString(7, post.getVideo());
        os.setDate(8, new java.sql.Date(post.getCreatedat().getTime()));
        os.setInt(9, post.getVisible());
        os.setInt(10, post.getThreadid());
        os.executeUpdate();
        System.out.println("Post ajoutée avec succès");
    }

    @Override
    public void modifier(Post post) throws SQLException {
        String req = "UPDATE post SET title = ?, content = ?, quote = ?, image = ?, rating = ?, video = ?, " +
                "createdat = ?, visible = ?, updatedat = ?, threadid = ? WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setString(1, post.getTitle());
        os.setString(2, post.getContent());
        os.setString(3, post.getQuote());
        os.setString(4, post.getImage());
        os.setInt(5, post.getRating());
        os.setString(6, post.getVideo());
        os.setDate(7, new java.sql.Date(post.getCreatedat().getTime()));
        os.setInt(8, post.getVisible());
        os.setDate(9, new java.sql.Date(post.getDateupdatedat().getTime()));
        os.setInt(10, post.getThreadid());
        os.setInt(11, post.getId());
        os.executeUpdate();
        System.out.println("Post modifiée avec succès");
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM post WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        os.executeUpdate();
        System.out.println("Post supprimée avec succès");
    }

    @Override
    public Post getOneById(int id) throws SQLException {
        String req = "SELECT * FROM post WHERE id = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        if (rs.next()) {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setUsername_id(rs.getInt("username_id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setQuote(rs.getString("quote"));
            post.setImage(rs.getString("image"));
            post.setRating(rs.getInt("rating"));
            post.setVideo(rs.getString("video"));
            post.setCreatedat(rs.getDate("createdat"));
            post.setVisible(rs.getInt("visible"));
            post.setDateupdatedat(rs.getDate("updatedat"));
            post.setThreadid(rs.getInt("threadid"));
            return post;
        }
        return null;
    }

    @Override
    public List<Post> getAll() throws SQLException {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM post";
        PreparedStatement os = connection.prepareStatement(req);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setUsername_id(rs.getInt("username_id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setQuote(rs.getString("quote"));
            post.setImage(rs.getString("image"));
            post.setRating(rs.getInt("rating"));
            post.setVideo(rs.getString("video"));
            post.setCreatedat(rs.getDate("createdat"));
            post.setVisible(rs.getInt("visible"));
            post.setDateupdatedat(rs.getDate("updatedat"));
            post.setThreadid(rs.getInt("threadid"));
            posts.add(post);
        }
        return posts;
    }

    @Override
    public List<Post> getByIdUser(int id) throws SQLException {
        List<Post> posts = new ArrayList<>();
        String req = "SELECT * FROM post WHERE threadid = ?";
        PreparedStatement os = connection.prepareStatement(req);
        os.setInt(1, id);
        ResultSet rs = os.executeQuery();
        while (rs.next()) {
            Post post = new Post();
            post.setId(rs.getInt("id"));
            post.setUsername_id(rs.getInt("username_id"));
            post.setTitle(rs.getString("title"));
            post.setContent(rs.getString("content"));
            post.setQuote(rs.getString("quote"));
            post.setImage(rs.getString("image"));
            post.setRating(rs.getInt("rating"));
            post.setVideo(rs.getString("video"));
            post.setCreatedat(rs.getDate("createdat"));
            post.setVisible(rs.getInt("visible"));
            post.setDateupdatedat(rs.getDate("updatedat"));
            post.setThreadid(rs.getInt("threadid"));
            posts.add(post);
        }
        return posts;
    }
}
