package GestionCompte;

import javafx.beans.property.SimpleStringProperty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nejoua & Myriam on 29/03/2017.
 */
public class Operation {

    private int id;
    private String name;
    private String description;
    private String type;
    private String paymentType;
    private Categorie categorie;
    private SubCategorie subCategorie;
    private Date createdAt;
    private Date updatedAt;
    private float amount;
    private String poste;

    // For the view

    private SimpleStringProperty nameView;
    private SimpleStringProperty typeView;
    private SimpleStringProperty typePayView;
    private SimpleStringProperty categoryView;
    private SimpleStringProperty subCategoryView;
    private SimpleStringProperty creationView;
    private SimpleStringProperty posteView;


    public Operation(int id, String name, String description,String type, String paymentType, Categorie categorie, SubCategorie subCategorie, Date createdAt, Date updatedAt, float amount, String poste){
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.paymentType = paymentType;
        this.categorie = categorie;
        this.subCategorie = subCategorie;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.amount = amount;
        this.poste = poste;
        this.nameView = new SimpleStringProperty(name);
        this.typeView = new SimpleStringProperty(type);
        this.typePayView = new SimpleStringProperty(paymentType);
        this.categoryView = new SimpleStringProperty(categorie.getName());
        this.subCategoryView = new SimpleStringProperty(subCategorie.getName());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        this.creationView = new SimpleStringProperty(df.format(createdAt));
        this.posteView = new SimpleStringProperty(poste);
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public SubCategorie getSubCategorie() {
        return subCategorie;
    }

    public void setSubCategorie(SubCategorie subCategorie) {
        this.subCategorie = subCategorie;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
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

    public String getTypeView() {
        return typeView.get();
    }

    public SimpleStringProperty typeViewProperty() {
        return typeView;
    }

    public void setTypeView(String typeView) {
        this.typeView.set(typeView);
    }

    public String getTypePayView() {
        return typePayView.get();
    }

    public SimpleStringProperty typePayViewProperty() {
        return typePayView;
    }

    public void setTypePayView(String typePayView) {
        this.typePayView.set(typePayView);
    }

    public String getCategoryView() {
        return categoryView.get();
    }

    public SimpleStringProperty categoryViewProperty() {
        return categoryView;
    }

    public void setCategoryView(String categoryView) {
        this.categoryView.set(categoryView);
    }

    public String getSubCategoryView() {
        return subCategoryView.get();
    }

    public SimpleStringProperty subCategoryViewProperty() {
        return subCategoryView;
    }

    public void setSubCategoryView(String subCategoryView) {
        this.subCategoryView.set(subCategoryView);
    }

    public String getCreationView() {
        return creationView.get();
    }

    public SimpleStringProperty creationViewProperty() {
        return creationView;
    }

    public void setCreationView(String creationView) {
        this.creationView.set(creationView);
    }

    public String getPosteView() {
        return posteView.get();
    }

    public SimpleStringProperty posteViewProperty() {
        return posteView;
    }

    public void setPosteView(String posteView) {
        this.posteView.set(posteView);
    }
}
