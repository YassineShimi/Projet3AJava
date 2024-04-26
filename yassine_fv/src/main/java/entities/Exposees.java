package entities;

import java.time.LocalDateTime;

public class Exposees {
    private int id;
    private String nom_e;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String image_exposees;



// Constructeurs
    public Exposees(String nom_e, LocalDateTime dateDebut, LocalDateTime dateFin, String image_exposees) {
        this.id = id;
        this.nom_e = nom_e;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.image_exposees = image_exposees;
    }

    public Exposees() {

    }
// Fin Constructeurs
    // Getters
    public int getId() {
        return id;
    }
    public String getNom_e(){
        return nom_e;
    }
    public LocalDateTime getDateDebut() {
        // Format LocalDateTime to String using a DateTimeFormatter
        return dateDebut;
    }

    public LocalDateTime getDateFin() {

        return dateFin;
    }

    public String getImage_exposees() {
        return image_exposees;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setNom_e(String nom_e){
        this.nom_e = nom_e;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public void setImage_exposees(String image_exposees) {
        this.image_exposees = image_exposees;
    }

    @Override
    public String toString() {
        return "Exposees{" +
                "id=" + id +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", image_exposees='" + image_exposees + '\'' +
                '}';
    }
}
