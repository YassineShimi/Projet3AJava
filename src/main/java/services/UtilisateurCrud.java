package services;


import entities.MdpGen;
import entities.MdpHash;
import entities.Utilisateur;
import entities.enums.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UtilisateurCrud implements IUtilisateurCrud<Utilisateur> {
    private List<Utilisateur> registeredUsers;
    Connection cnx2;

    public UtilisateurCrud() {
        cnx2 = MyConnection.getInstance().getCnx();
        registeredUsers = getAllUtilisateurs();
    }

    // Méthode pour charger la liste des utilisateurs enregistrés depuis la base de données
    private List<Utilisateur> loadRegisteredUsers() {
        List<Utilisateur> users = new ArrayList<>();

        return users;
    }

    // Méthode pour obtenir la liste des utilisateurs enregistrés
    public List<Utilisateur> getRegisteredUsers() {
        if (registeredUsers == null) {
            // Charger la liste des utilisateurs enregistrés si elle n'a pas déjà été chargée
            registeredUsers = loadRegisteredUsers();
        }
        return registeredUsers;
    }

    @Override
    public void ajouterEntite(Utilisateur u) {
        String req1 = "INSERT INTO user (email , roles  , password , nom , prenom, tel , cin  ) VALUES ('" + u.getEmail() + "','" + u.getroles() + "','" + u.getpassword() + "','" + u.getNom() + "','" + u.getPrenom() + "','" + u.gettel() + "','" + u.getCin() + "')";
        try {
            Statement st = cnx2.createStatement();
            st.executeUpdate(req1);
            System.out.println("Utilisateur ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Utilisateur> afficherEntite() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String req3 = "SELECT * FROM user";
        try {
            Statement stm = cnx2.createStatement();
            ResultSet rs = stm.executeQuery(req3);
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt(1));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return utilisateurs;
    }
    public Utilisateur afficherUtilisateurByEmail(String email) {
        Utilisateur utilisateur = null;
        String req = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement pst = cnx2.prepareStatement(req)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("id"));
                utilisateur.setCin(rs.getInt("cin"));
                utilisateur.settel(rs.getString("num_tel"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setpassword(rs.getString("mdp"));
                String roleString = rs.getString("role");
                Role role = Role.valueOf(roleString);
                utilisateur.setroles(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }


    public void ajouterEntite2(Utilisateur u) {
        // Hacher le mot de passe avant de l'ajouter à la base de données
        String hashedPassword = MdpHash.hashPassword(u.getpassword());
        u.setpassword(hashedPassword);

        String sql= "INSERT INTO `user` ( `email`, `roles`, `password`,  `nom`, `prenom`, `tel`, `cin`) VALUES (?,?,?,?,?, ? , ?)";

        try {
            PreparedStatement pst = cnx2.prepareStatement(sql);
            pst.setString(1, u.getEmail());
            pst.setString(2, u.getroles().name());
            pst.setString(3, hashedPassword);
            pst.setString(4, u.getNom());
            pst.setString(5, u.getPrenom());
            pst.setString(6, u.gettel());
            pst.setInt(7, u.getCin());
            pst.executeUpdate();
            System.out.println("Ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifierEntite(Utilisateur u) {
        //String hashedPassword = MdpHash.hashPassword(u.getMdp());
       // u.setMdp(hashedPassword);

        Connection cnx2 = MyConnection.instance.getCnx();
        System.out.println(u);
        String req2 = "UPDATE user SET cin=?, tel=?, nom=?, prenom=?, email=?, password=? WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req2);
            pst.setInt(1, u.getCin());
            pst.setString(2, u.gettel());
            pst.setString(3, u.getNom());
            pst.setString(4, u.getPrenom());
            pst.setString(5, u.getEmail());
            pst.setString(6, u.getpassword());
            pst.setInt(7, u.getId());// Assuming id is the primary key
            pst.executeUpdate();
            System.out.println("utilisateur modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void modifierProfil(Utilisateur u) {
        u.setroles(Role.USER);
        System.out.println(u);
        // Assurez-vous d'avoir une connexion à la base de données valide
        Connection cnx2 = MyConnection.instance.getCnx();

        // Requête SQL pour mettre à jour l'utilisateur
        String req2 = "UPDATE user SET cin=?, tel=?, nom=?, prenom=?, email=?, password=?, roles=? WHERE id=?";

        try {
            // Préparer la requête SQL avec les paramètres
            PreparedStatement pst = cnx2.prepareStatement(req2);
            pst.setInt(1, u.getCin());
            pst.setString(2, u.gettel());
            pst.setString(3, u.getNom());
            pst.setString(4, u.getPrenom());
            pst.setString(5, u.getEmail());
            pst.setString(6, u.getpassword());
            pst.setString(7, u.getroles().toString());
            pst.setInt(8, u.getId()); // Supposant que 'id' est la clé primaire

            // Exécuter la requête de mise à jour
            int rowsAffected = pst.executeUpdate();

            // Vérifier si des lignes ont été affectées par la mise à jour
            if (rowsAffected > 0) {
                System.out.println("Utilisateur modifié avec succès.");
            } else {
                System.out.println("Aucune modification effectuée pour l'utilisateur.");
            }
        } catch (SQLException e) {
            // Gérer toute exception SQL
            System.out.println("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
        }
    }



    @Override
    public void supprimerEntite(int id) {
        String req3 = "DELETE FROM user WHERE id=?";
        try {
            PreparedStatement pst = cnx2.prepareStatement(req3);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("utilisateur supprimé");
            // Réinitialiser la séquence d'auto-incrémentation
            String resetSequenceQuery = "ALTER TABLE user AUTO_INCREMENT = 1";
            PreparedStatement resetSequenceStatement = cnx2.prepareStatement(resetSequenceQuery);
            resetSequenceStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier2(Utilisateur e) {


            try {
                String req = "UPDATE user SET cin=?, tel=?, nom=?, prenom=? WHERE id=?";
                PreparedStatement pst = cnx2.prepareStatement(req);
                pst.setInt(1, e.getCin());
                pst.setString(2, e.gettel());
                pst.setString(3, e.getNom());
                pst.setString(4, e.getPrenom());
                pst.setInt(5, e.getId());
                pst.executeUpdate();
                System.out.println("Votre profil est modifié !");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }


    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();

        // Préparer la requête SQL pour récupérer tous les utilisateurs
        String sql = "SELECT * FROM user";
        try (PreparedStatement statement = cnx2.prepareStatement(sql)) {
            // Exécuter la requête et récupérer le résultat
            try (ResultSet resultSet = statement.executeQuery()) {
                // Parcourir le résultat et ajouter chaque utilisateur à la liste
                while (resultSet.next()) {
                    Utilisateur utilisateur = new Utilisateur();
                    utilisateur.setId(resultSet.getInt("id"));
                    utilisateur.setCin(resultSet.getInt("cin"));
                    utilisateur.settel(resultSet.getString("tel"));
                    utilisateur.setNom(resultSet.getString("nom"));
                    utilisateur.setPrenom(resultSet.getString("prenom"));
                    utilisateur.setEmail(resultSet.getString("email"));
                    utilisateur.setpassword(resultSet.getString("password"));
                    String roleString = resultSet.getString("roles");
                    Role role = Role.valueOf(roleString);
                    utilisateur.setroles(role);
                    utilisateurs.add(utilisateur);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return utilisateurs;
    }

    public Utilisateur getUtilisateurById(int id) {
        Utilisateur utilisateur = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement pst = cnx2.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    utilisateur = new Utilisateur();
                    utilisateur.setId(rs.getInt("id"));
                    utilisateur.setCin(rs.getInt("cin"));
                    utilisateur.settel(rs.getString("num_tel"));
                    utilisateur.setNom(rs.getString("nom"));
                    utilisateur.setPrenom(rs.getString("prenom"));
                    utilisateur.setEmail(rs.getString("email"));
                    utilisateur.setpassword(rs.getString("mdp"));

                    // Ajoutez d'autres champs selon vos besoins
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }


    public void registerUser(Utilisateur user) {
        // Add the user to the list of registered users
        registeredUsers.add(user);
    }

    public boolean authenticateUser(String email, String password) {
        // Récupérer l'utilisateur correspondant à l'email spécifié
        Utilisateur user = registeredUsers.stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);

        // Vérifier si l'utilisateur a été trouvé et si le mot de passe correspond
        if (user != null) {
            // Hacher le mot de passe fourni par l'utilisateur
            String hashedPassword = MdpHash.hashPassword(password);

            // Comparer le mot de passe haché avec celui stocké dans la base de données
            return user.getpassword().equals(hashedPassword);
        } else {
            // L'utilisateur n'a pas été trouvé
            return false;
        }
    }

    public boolean utilisateurExisteDeja(String cin, String email) {


        // Vérifier si un utilisateur avec le même CIN ou la même adresse email existe déjà
        String query = "SELECT COUNT(*) FROM user WHERE cin = ? OR email = ?";
        try (PreparedStatement pst = cnx2.prepareStatement(query)) {
            pst.setString(1, cin);
            pst.setString(2, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer les exceptions de manière appropriée
        }

        return false;
    }

    public Utilisateur login(String email, String password) {
        Utilisateur utilisateur = null;
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (PreparedStatement pst = cnx2.prepareStatement(query)) {
            pst.setString(1, email);
            pst.setString(2, password);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    // L'utilisateur est trouvé dans la base de données
                    utilisateur = new Utilisateur();
                    utilisateur.setId(rs.getInt("id"));
                    utilisateur.setCin(rs.getInt("cin"));
                    utilisateur.settel("tel");
                    utilisateur.setNom(rs.getString("nom"));
                    utilisateur.setPrenom(rs.getString("prenom"));
                    utilisateur.setEmail(rs.getString("email"));
                    utilisateur.setpassword(rs.getString("password"));
                    String roleString = rs.getString("roles");
                    Role role = Role.valueOf(roleString);
                    utilisateur.setroles(role);

                    // Afficher un message de succès
                    System.out.println("Connexion réussie !");
                } else {
                    // L'utilisateur n'est pas trouvé dans la base de données
                    System.out.println("Connexion échouée : email ou mot de passe incorrect !");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    public ObservableList<Utilisateur> getUtilisateursByRole(Role role) {
        List<Utilisateur> utilisateursFiltres = new ArrayList<>();
        for (Utilisateur utilisateur : getAllUtilisateurs()) {
            if (utilisateur.getroles().equals(role)) {
                utilisateursFiltres.add(utilisateur);
            }
        }
        return FXCollections.observableArrayList(utilisateursFiltres);
    }
    public Utilisateur getUtilisateurByEmail(String email) {
        // Parcourir la liste des utilisateurs enregistrés pour trouver celui avec l'email correspondant
        for (Utilisateur utilisateur : registeredUsers) {
            if (utilisateur.getEmail().equals(email)) {
                return utilisateur;
            }
        }
        // Retourner null si aucun utilisateur avec l'email correspondant n'est trouvé
        return null;
    }
    public int getUserIdByEmail(String email) {
        int userId = -1; // Default value if user is not found
        String query = "SELECT id FROM user WHERE email = ?";

        try {
            PreparedStatement statement = cnx2.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("id");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }
    // Méthode pour calculer le nombre d'utilisateurs selon leur rôle
    public int countUsersByRole(String role) {
        Connection cnx = MyConnection.instance.getCnx();
        String req = "SELECT COUNT(*) FROM user WHERE roles=?";
        try {
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, role);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public static String genererNouveauMdp() {
        return MdpGen.genererMdp();
    }

}
