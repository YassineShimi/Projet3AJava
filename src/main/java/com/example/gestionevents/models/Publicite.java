package com.example.gestionevents.models;

public class Publicite {

   private int id;
   private String description;
   private String type;
    private String sponsor;

    public Publicite(){

    }
    Publicite(int id, String description, String type, String sponsor){
        this.id = id;
        this.description = description;
        this.type = type;
        this.sponsor = sponsor;
    }

    Publicite( String description, String type, String sponsor){

        this.description = description;
        this.type = type;
        this.sponsor = sponsor;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public String toString() {
        return "publicite{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", sponsor='" + sponsor + '\'' +
                '}';
    }



}
