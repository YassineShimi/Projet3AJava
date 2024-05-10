package entities;


public class Produits {
    private int id;
    private String description;
    private String prix;
    private String ressource;
    private int exposees_id;

    // Constructors
    public Produits(String description, String prix, String ressource, int exposees_id) {
        this.id = id;
        this.description = description;
        this.prix = prix;
        this.ressource = ressource;
        this.exposees_id = exposees_id;
    }

    public Produits() {

    }
    // End of Constructors

    // Getters
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getPrix() {
        return prix;
    }

    public String getRessource() {
        return ressource;
    }

    public int getExposees_id() {
        return exposees_id;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public void setRessource(String ressource) {
        this.ressource = ressource;
    }

    public void setExposees_id(int exposees_id) {
        this.exposees_id = exposees_id;
    }

    @Override
    public String toString() {
        return "Exposees{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", prix='" + prix + '\'' +
                ", ressource='" + ressource + '\'' +
                ", exposees_id=" + exposees_id +
                '}';
    }
}
