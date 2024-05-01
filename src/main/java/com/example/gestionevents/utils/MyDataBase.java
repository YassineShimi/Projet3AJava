package com.example.gestionevents.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class MyDataBase {
    private static MyDataBase instance ;
    private final String URL ="jdbc:mysql://localhost:3306/projet";
    private final String USERNAME="root";
    private final String PASSWORD ="";

    static Connection cnx ;


    private MyDataBase(){

        try {
            cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);

            System.out.println("Connected ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("____not connected____ ");

        }

    }
    public static MyDataBase getInstance(){
        if (instance == null)
            instance = new MyDataBase();

        return instance;
    }
    public static Connection getCnx(){
        return cnx;
    }

}
