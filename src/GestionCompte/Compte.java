package GestionCompte;

/**
 * Created by Nefast on 10/04/2017.
 */
public class Compte {
    private int id;
    private String userName;
    private Client client;

    public Compte(int id, String userName, Client client){
        this.id = id;
        this.userName = userName;
        this.client = client;
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
}
