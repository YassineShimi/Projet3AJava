package com.example.gestionevents.models;

import java.time.LocalDateTime;

public class Participant {


   private int id;
     private int evenement_id;
    private  String description;
    private LocalDateTime date_participation;

    public Participant(){

    }

    Participant(int id, int evenement_id, String description, LocalDateTime date_participation){
        this.id =id;
        this.evenement_id = evenement_id;
        this.description = description;
        this.date_participation = date_participation;
    }

    public Participant(int evenement_id, String description, LocalDateTime date_participation){

        this.evenement_id = evenement_id;
        this.description = description;
        this.date_participation = date_participation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvenement_id() {
        return evenement_id;
    }

    public void setEvenement_id(int evenement_id) {
        this.evenement_id = evenement_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate_participation() {
        return date_participation;
    }

    public void setDate_participation(LocalDateTime date_participation) {
        this.date_participation = date_participation;
    }

    @Override
    public String toString() {
        return "participant{" +
                "id=" + id +
                ", evenement_id=" + evenement_id +
                ", description='" + description + '\'' +
                ", date_participation=" + date_participation +
                '}';
    }
}
