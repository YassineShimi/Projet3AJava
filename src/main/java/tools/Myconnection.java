/*
 * To change this license header, choose License Headers in Project Properties.
 */
package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Myconnection {
    private String url = "jdbc:mysql://localhost:3306/projetpidev";
    private String login = "root";
    private String password ="";

    Connection cnx;
    private static Myconnection instance;

    public Myconnection(){
        try {
            cnx = DriverManager.getConnection(url, login, password);
            System.out.println("Connected to DB ! ");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Connection getCnx(){
        return cnx ;
    }
    public static Myconnection getInstance() {

        if (instance == null){
            instance = new Myconnection();
        }
        return instance;
    }




}

