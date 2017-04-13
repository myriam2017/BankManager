package GestionCompte;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Nejoua & Myriam on 29/03/2017.
 */
public class Categorie {

    private int id;
    private String name;

    // For the view

    private SimpleStringProperty idView;
    private SimpleStringProperty nameView;

    public Categorie(int id, String name){
        this.id = id;
        this.name = name;
        this.idView = new SimpleStringProperty(String.valueOf(id));
        this.nameView = new SimpleStringProperty(name);
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
}
