package simulation;

import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about all events in a simulation day. Setups and runs the simulation for a day.
 * Creates the clients based on a random number for arrival and service times.
 */
public class Day {

    private int dayID = -1;
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();

    public Day() {
    }

    /**
     * This constructor creates the Clients assigning the arrival and service times based on a random number.
     * @param config contains the configuration for the simulation. This constructor uses its TimeDistributions when creating the Clients.
     */
    public Day(Config config) {

    }

    // -------------------- Getters -------------------- //

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    // -------------------- Setters -------------------- //

    public void setEvents(ArrayList<Event> events) {
        this.events = (ArrayList) events.clone();
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = (ArrayList) clients.clone();
    }

    // -------------------- Other functions -------------------- //

    // TODO: 08/06/2016 Simulates the day, creating and adding the events following a waiting line simulation algorithm.
    public Day simulate(){
        return this;
    }

}
