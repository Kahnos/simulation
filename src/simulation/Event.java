package simulation;

import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about a specific event in a day. Includes type, status of servers, current waiting line, current time and involved client.
 */
public class Event {

    private int eventID = -1;
    private String type = "";
    private Client client = null;
    private int TM = -1;
    private ArrayList<Server> servers = new ArrayList<>();
    private ArrayList<Client> waitLine = new ArrayList<>();
    private int nextArrivalTime = -1;
    private int nextDepartureTime = -1;

    public Event() {
    }

    public Event(int eventID, String type, Client client, int TM, int nextArrivalTime, int nextDepartureTime, ArrayList<Server> servers) {
        this.eventID = eventID;
        this.type = type;
        this.client = client;
        this.TM = TM;
        this.nextArrivalTime = nextArrivalTime;
        this.nextDepartureTime = nextDepartureTime;
        this.servers = (ArrayList) servers.clone();
        ;
    }

    public Event(int eventID, String type, Client client, int TM, int nextArrivalTime, int nextDepartureTime, ArrayList<Server> servers, ArrayList<Client> waitLine) {
        this.eventID = eventID;
        this.type = type;
        this.client = client;
        this.TM = TM;
        this.servers = (ArrayList) servers.clone();
        this.waitLine = (ArrayList) waitLine.clone();
        this.nextArrivalTime = nextArrivalTime;
        this.nextDepartureTime = nextDepartureTime;
    }

    // -------------------- Getters -------------------- //

    public int getEventID() {
        return eventID;
    }

    public int getTM() {
        return TM;
    }

    public String getType() {
        return type;
    }

    public Client getClient() {
        return client;
    }

    public ArrayList<Server> getServers() {
        return servers;
    }

    public ArrayList<Client> getWaitLine() {
        return waitLine;
    }

    public int getNextArrivalTime() {
        return nextArrivalTime;
    }

    public int getNextDepartureTime() {
        return nextDepartureTime;
    }

    // -------------------- Setters -------------------- //

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public void setTM(int TM) {
        this.TM = TM;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setServers(ArrayList<Server> servers) {
        this.servers = (ArrayList) servers.clone();
    }

    public void setWaitLine(ArrayList<Client> waitLine) {
        this.waitLine = (ArrayList) waitLine.clone();
    }

    public void setNextArrivalTime(int nextArrivalTime) {
        this.nextArrivalTime = nextArrivalTime;
    }

    public void setNextDepartureTime(int nextDepartureTime) {
        this.nextDepartureTime = nextDepartureTime;
    }

    // -------------------- Other functions -------------------- //

    /**
     * Sets the busy status of a server.
     * @param busy contains the status to set.
     * @param index in the server ArrayList.
     */
    public void setServer(boolean busy, int index) {
        servers.get(index).setBusy(busy);
    }

    /**
     * Sets the Client of a server.
     * @param client contains the Client to set.
     * @param index in the server ArrayList.
     */
    public void setServer(Client client, int index) {
        servers.get(index).setClient(client);
    }

}