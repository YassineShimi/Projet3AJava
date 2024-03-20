package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class MyConnection {
    private String URL="jdbc:mysql://localhost:3306/esprit3";
    private String USER="";
    private String PWD="";
    Connection cnx;
 public MyConnection(){
     try {
         cnx= DriverManager.getConnection(URL,USER,PWD);
         System.out.println("Connection enbaled");
     } catch (SQLException e) {
         System.out.println(e.getMessage());
     }
 }
}
