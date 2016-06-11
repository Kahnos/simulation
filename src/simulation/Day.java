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

    public Day(int at, int dt) {
        this.nextArrivalTime = at;
        this.nextDepartureTime = dt;

    }

    public int getNextArrivalTime() {
        return nextArrivalTime;
    }

    public int getNextDepartureTime() {
        return nextDepartureTime;
    }

    /**
     * This constructor calls simulate() (which runs the simulation) and passes it the configuration.
     * @param dayID contains the ID of the day, for example, day 13 of 300.
     * @param config contains the configuration for the simulation.
     */
    public Day(int dayID, Config config) {
        this.dayID = dayID;
        simulate(config);
    }

    // -------------------- Getters -------------------- //

    public int getDayID() {
        return dayID;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    // -------------------- Setters -------------------- //

    public void setDayID(int dayID) {
        this.dayID = dayID;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = (ArrayList) events.clone();
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = (ArrayList) clients.clone();
    }

    // -------------------- Other functions -------------------- //

    /**
     * Simulates the day, creating and adding the events following a waiting line simulation algorithm.
     * @param config contains the configuration for the simulation.
     */
    public void simulate(Config config){
        
    }

}