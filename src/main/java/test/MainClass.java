package test;

import entities.Utilisateur;
import entities.enums.Role;
import services.UtilisateurCrud;
import tools.MyConnection;


public class MainClass {
    public static void main(String[] args) {
        MyConnection mc=MyConnection.getInstance();
        MyConnection mc2=MyConnection.getInstance();
        System.out.println(mc.hashCode()+"-"+mc2.hashCode());
        Utilisateur p=new Utilisateur(14505878,"52971719","Mejri","aya","ayamejri43@gmail.com","", Role.USER);
        UtilisateurCrud pcd=new UtilisateurCrud();
         System.out.println(pcd.afficherEntite());
        pcd.ajouterEntite2(p);
    }
}
