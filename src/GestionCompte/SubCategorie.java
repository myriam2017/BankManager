package GestionCompte;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Nejoua & Myriam on 29/03/2017.
 */
public class SubCategorie {

    private int id;
    private String name;
    private Categorie categorie;

    // For the view

    private SimpleStringProperty idView;
    private SimpleStringProperty nameView;
    private SimpleStringProperty categorieView;

    public SubCategorie(int id, String name, Categorie categorie){
        this.id = id;
        this.name = name;
        this.categorie = categorie;
        this.idView = new SimpleStringProperty(String.valueOf(id));
        this.nameView = new SimpleStringProperty(name);
        this.categorieView = new SimpleStringProperty(categorie.getName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getIdView() {
        return idView.get();
    }

    public SimpleStringProperty idViewProperty() {
        return idView;
    }

    public void setIdView(String idView) {
        this.idView.set(idView);
    }

    public String getNameView() {
        return nameView.get();
    }

    public SimpleStringProperty nameViewProperty() {
        return nameView;
    }

    public void setNameView(String nameView) {
        this.nameView.set(nameView);
    }

    public String getCategorieView() {
        return categorieView.get();
    }

    public SimpleStringProperty categorieViewProperty() {
        return categorieView;
    }

    public void setCategorieView(String categorieView) {
        this.categorieView.set(categorieView);
    }
}
