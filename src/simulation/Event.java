package simulation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private int totalClients = -1;
    private int nextArrivalTime = -1;
    private int nextDepartureTime = -1;

    public Event() {
    }

    public Event(int eventID, String type, Client client, int TM, int nextArrivalTime, int nextDepartureTime,
                 ArrayList<Server> servers, int totalClients) {
        this.eventID = eventID;
        this.type = type;
        this.client = client;
        this.TM = TM;
        this.nextArrivalTime = nextArrivalTime;
        this.nextDepartureTime = nextDepartureTime;
        for (Server server : servers) {
            this.servers.add((Server) deepClone(server));
        }
        this.totalClients = totalClients;
    }

    public Event(int eventID, String type, Client client, int TM, int nextArrivalTime, int nextDepartureTime,
                 ArrayList<Server> servers, ArrayList<Client> waitLine, int totalClients) {
        this.eventID = eventID;
        this.type = type;
        this.client = client;
        this.TM = TM;
        for (Server server : servers) {
            this.servers.add((Server) deepClone(server));
        }
        this.waitLine = (ArrayList) waitLine.clone();
        this.nextArrivalTime = nextArrivalTime;
        this.nextDepartureTime = nextDepartureTime;
        this.totalClients = totalClients;
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

    public int getTotalClients() {
        return totalClients;
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
        this.servers.clear();
        for (Server server : servers) {
            this.servers.add((Server) deepClone(server));
        }
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

    public void setTotalClients(int totalClients) {
        this.totalClients = totalClients;
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

    /**
     * This method makes a "deep clone" of any Java object it is given.
     * Taken from: http://alvinalexander.com/java/java-deep-clone-example-source-code
     */
    public static Object deepClone(Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append(String.format("%5d%15s%10d%5d", eventID, type, client.getId(), TM));

        for (Server server : servers) {
            if (server.isBusy())
                string.append(String.format("%5d ", 1));
            else
                string.append(String.format("%5d ", 0));
        }

        string.append(String.format("%10d%10d%10d%10d\n",waitLine.size(), totalClients, nextArrivalTime, nextDepartureTime));

        return string.toString();
    }

}