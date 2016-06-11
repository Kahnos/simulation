package simulation;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Kahnos - libcorrales.
 * Contains information about all events in a simulation day. Setups and runs the simulation for a day.
 * Creates the clients based on a random number for arrival and service times.
 */
public class Day {

    private int dayID = -1;
    private Config config;
    private ArrayList<Event> events = new ArrayList<>();
    private ArrayList<Client> clients = new ArrayList<>();

    public Day() {
    }

    /**
     * This constructor calls simulate(), which runs the simulation.
     * @param dayID contains the ID of the day, for example, day 13 of 300.
     * @param config contains the configuration for the simulation.
     */
    public Day(int dayID, Config config) {
        this.dayID = dayID;
        this.config = config;
        simulate();
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
     * Creates a new client, assigning the arrival and service times based on random probability.
     * @return the created client.
     */
    private Client createClient(int id){
        double randomNumber = Math.random();
        int arrivalTime = -1;
        int serviceTime = -1;

        for (TimeDistribution AD : config.getArrivalDistribution())
            if ((randomNumber >= AD.getProbabilityMin())
                    && (randomNumber <= AD.getProbabilityMax())) {
                arrivalTime = AD.getTime();
            }

        for (TimeDistribution SD : config.getServiceDistribution())
            if ((randomNumber >= SD.getProbabilityMin())
                    && (randomNumber <= SD.getProbabilityMax())) {
                serviceTime = SD.getTime();
            }

        return new Client(id, arrivalTime, serviceTime);
    }

    /**
     * Simulates the day, creating and adding the events following a waiting line simulation algorithm.
     */
    private void simulate(){
        // Creating the servers based on the configuration.
        ArrayList servers = new ArrayList(config.getServerAmount());
        for (int i = 0; i < config.getServerAmount(); i++) {
            servers.add(new Server(i));
        }

        // Creating the first client.
        clients.add(createClient(1));
        clients.get(0).setRelativeArrivalTime(0);
        clients.get(0).setRealArrivalTime(0);
        clients.get(0).setServedTime(0);
        clients.get(0).calculateDepartureTime();
        clients.get(0).calculateWaitTime();

        // Creating the second client.
        clients.add(createClient(2));
        clients.get(1).setRealArrivalTime(clients.get(1).getRelativeArrivalTime());

        // Creating the first event, the arrival of the first client.
        events.add(new Event(1, "Llegada", clients.get(0), 0, clients.get(1).getRealArrivalTime(), clients.get(0).get ));
    }

}





















