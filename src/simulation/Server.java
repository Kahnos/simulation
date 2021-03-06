package simulation;

import java.io.Serializable;

/**
 * Created by Kahnos - libcorrales.
 * Contains the current status and client of a server.
 */
public class Server implements Serializable{

    int id = -1;
    private boolean busy = false;
    private Client client = null;

    public Server() {
    }

    public Server(int id) {
        this.id = id;
    }

    public Server(int id, Client client) {
        this.id = id;
        this.client = client;
    }

    public Server(int id, boolean busy, Client client) {
        this.id = id;
        this.busy = busy;
        this.client = client;
    }

    // -------------------- Getters -------------------- //

    public int getId() {
        return id;
    }

    public boolean isBusy() {
        return busy;
    }

    public Client getClient() {
        return client;
    }

    // -------------------- Setters -------------------- //

    public void setId(int id) {
        this.id = id;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // -------------------- Other functions -------------------- //

    public void setAll(Client client, boolean busy){
        this.client = client;
        this.busy = busy;
    }

    public void clearAll(){
        client = null;
        busy = false;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("ID: ").append(id).append(" - ");
        string.append("Busy: ").append(busy).append(" - ");
        if (client == null)
            string.append("Client: null").append("\n");
        else
            string.append("Client: ").append(client.getId()).append("\n");

        return string.toString();
    }

}