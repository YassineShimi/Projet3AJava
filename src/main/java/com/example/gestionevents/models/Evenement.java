package com.example.gestionevents.models;

import java.time.LocalDateTime;

public class Evenement {


    private int id;
    private int publicite_id;
    private String image_evenement;
    private String theme_evenement;
    private String type_evenement;
    private LocalDateTime date_debut;
    private LocalDateTime date_fin;
private  int 	nbr_participant;
    public Evenement() {
    }

    public Evenement(int id,int publicite_id ,String image_evenement,String theme_evenement,String type_evenement, LocalDateTime date_debut,LocalDateTime date_fin,int nbr_participant) {
        this.id = id;
        this.publicite_id = publicite_id;
        this.image_evenement = image_evenement;
        this.theme_evenement = theme_evenement;
        this.type_evenement = type_evenement;
        this.date_debut = date_debut;
        this.date_fin=date_fin;
        this.nbr_participant=nbr_participant;


    }
    public Evenement(int publicite_id ,String image_evenement,String theme_evenement,String type_evenement, LocalDateTime date_debut,LocalDateTime date_fin,int nbr_participant) {
        this.publicite_id = publicite_id;
        this.image_evenement = image_evenement;
        this.theme_evenement = theme_evenement;
        this.type_evenement = type_evenement;
        this.date_debut = date_debut;
        this.date_fin=date_fin;
        this.nbr_participant=nbr_participant;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicite_id() {
        return publicite_id;
    }

    public void setPublicite_id(int publicite_id) {
        this.publicite_id = publicite_id;
    }

    public String getImage_evenement() {
        return image_evenement;
    }

    public void setImage_evenement(String image_evenement) {
        this.image_evenement = image_evenement;
    }

    public String getTheme_evenement() {
        return theme_evenement;
    }

    public void setTheme_evenement(String theme_evenement) {
        this.theme_evenement = theme_evenement;
    }

    public String getType_evenement() {
        return type_evenement;
    }

    public void setType_evenement(String type_evenement) {
        this.type_evenement = type_evenement;
    }

    public LocalDateTime getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(LocalDateTime date_debut) {
        this.date_debut = date_debut;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDateTime date_fin) {
        this.date_fin = date_fin;
    }

    public int getNbr_participant() {
        return nbr_participant;
    }

    public void setNbr_participant(int nbr_participant) {
        this.nbr_participant = nbr_participant;
    }



    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", publicite_id=" + publicite_id +
                ", image_evenement='" + image_evenement + '\'' +
                ", theme_evenement='" + theme_evenement + '\'' +
                ", type_evenement='" + type_evenement + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", nbr_participant=" + nbr_participant +
                '}';
    }
}
