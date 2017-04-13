package GestionCompte;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Nejoua & Myriam on 10/04/2017.
 */
public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private int admin;

    // For the view

    private SimpleStringProperty idView;
    private SimpleStringProperty firstnameView;
    private SimpleStringProperty lastnameView;
    private SimpleStringProperty usernameView;
    private SimpleStringProperty adminView;

    public Client(int id, String firstName, String lastName, String userName, String password, int admin){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.admin = admin;

        this.idView = new SimpleStringProperty(String.valueOf(id));
        this.firstnameView = new SimpleStringProperty(firstName);
        this.lastnameView = new SimpleStringProperty(lastName);
        this.usernameView = new SimpleStringProperty(userName);
        String is_admin = (admin == 1) ? "Oui":"Non";
        this.adminView = new SimpleStringProperty(is_admin);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
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

    public String getFirstnameView() {
        return firstnameView.get();
    }

    public SimpleStringProperty firstnameViewProperty() {
        return firstnameView;
    }

    public void setFirstnameView(String firstnameView) {
        this.firstnameView.set(firstnameView);
    }

    public String getLastnameView() {
        return lastnameView.get();
    }

    public SimpleStringProperty lastnameViewProperty() {
        return lastnameView;
    }

    public void setLastnameView(String lastnameView) {
        this.lastnameView.set(lastnameView);
    }

    public String getUsernameView() {
        return usernameView.get();
    }

    public SimpleStringProperty usernameViewProperty() {
        return usernameView;
    }

    public void setUsernameView(String usernameView) {
        this.usernameView.set(usernameView);
    }

    public String getAdminView() {
        return adminView.get();
    }

    public SimpleStringProperty adminViewProperty() {
        return adminView;
    }

    public void setAdminView(String adminView) {
        this.adminView.set(adminView);
    }
}
