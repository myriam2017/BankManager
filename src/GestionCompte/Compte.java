package GestionCompte;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Nejoua & Myriam on 10/04/2017.
 */
public class Compte {
    private int id;
    private String userName;
    private Client client;

    // For the view

    private SimpleStringProperty idView;
    private SimpleStringProperty usernameView;
    private SimpleStringProperty clientView;

    public Compte(int id, String userName, Client client){
        this.id = id;
        this.userName = userName;
        this.client = client;

        this.idView = new SimpleStringProperty(String.valueOf(id));
        this.usernameView = new SimpleStringProperty(userName);
        this.clientView = new SimpleStringProperty(client.getUserName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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

    public String getUsernameView() {
        return usernameView.get();
    }

    public SimpleStringProperty usernameViewProperty() {
        return usernameView;
    }

    public void setUsernameView(String usernameView) {
        this.usernameView.set(usernameView);
    }

    public String getClientView() {
        return clientView.get();
    }

    public SimpleStringProperty clientViewProperty() {
        return clientView;
    }

    public void setClientView(String clientView) {
        this.clientView.set(clientView);
    }
}
